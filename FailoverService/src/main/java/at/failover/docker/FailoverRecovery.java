package at.failover.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class FailoverRecovery {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final static int DEFAULT_PORT = 8080;
    private final static int TIMEOUT_PING_MS = 500;
    private final static int TIMEOUT_RESTARTED_SERVICE_S = 120;
    private final static int INTERVAL_CHECK_RESTARTED_SERVICES_S = 2;
    private final static int WAIT_BEFORE_CHECKING_RESTARTED_SUCCESSFULLY_S = 20;

    private DockerClient docker;
    private List<String> serviceNames;

    public FailoverRecovery(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public void checkOnRunningServices() {
        if (docker == null) {
            try {
                docker = DefaultDockerClient.fromEnv().build();
            } catch (DockerCertificateException e) {
                e.printStackTrace();
                LOG.error("Failed to connect to docker API", e);
                return;
            }
        }

        LOG.info("Checking availability of services");

        List<Container> containers = null;
        List<String> restartedServices = new ArrayList<>();

        for (String service : serviceNames) {
            if (!serviceAvailable(service, true)) {
                if (containers == null) {
                    try {
                        containers = docker.listContainers(DockerClient.ListContainersParam.allContainers());
                    } catch (DockerException | InterruptedException e) {
                        LOG.error("Failed to obtain list of all containers", e);
                        return;
                    }
                }

                final Optional<Container> container = containers.stream().filter(
                        (c) -> c.names().get(0).contains(service)).findFirst();

                if (container.isPresent()) {
                    try {
                        docker.restartContainer(container.get().names().get(0));
                        LOG.info("Container {} with name {} restarted",
                                service, container.get().names().get(0));
                        restartedServices.add(service);
                    } catch (DockerException | InterruptedException e) {
                        LOG.error("Failed to restart container {}", service, e);
                    }
                } else {
                    LOG.error("Container with name {} not found", service);
                }
            }
        }

        if (restartedServices.size() > 0) {
            LOG.trace("Waiting {} seconds before checking on restarted services", WAIT_BEFORE_CHECKING_RESTARTED_SUCCESSFULLY_S);

            sleepSeconds(WAIT_BEFORE_CHECKING_RESTARTED_SUCCESSFULLY_S);

            final long currentTime = System.currentTimeMillis();

            while (((currentTime - System.currentTimeMillis()) / 1000) < TIMEOUT_RESTARTED_SERVICE_S) {
                for (String service : restartedServices) {
                    if (serviceAvailable(service, false)) {
                        restartedServices.remove(service);
                    } else {
                        sleepSeconds(INTERVAL_CHECK_RESTARTED_SERVICES_S);
                    }
                }
            }
        }

        if(restartedServices.size() > 0) {
            LOG.error("Services {} unsuccessfully restarted", restartedServices);
        } else {
            LOG.info("All services successfully restarted");
        }
    }

    private boolean serviceAvailable(String serviceURL, boolean levelWarning) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(serviceURL, DEFAULT_PORT), TIMEOUT_PING_MS);
        } catch (IOException e) {
            if (levelWarning) {
                LOG.warn("Service {} unreachable", serviceURL, e);
            } else {
                LOG.trace("Service {} unreachable", serviceURL, e);
            }
            return false;
        }

        return true;
    }


    private void sleepSeconds(int secondsToSleep) {
        try {
            TimeUnit.SECONDS.sleep(secondsToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
