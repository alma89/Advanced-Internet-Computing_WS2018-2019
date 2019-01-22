package at.failover.docker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DockerCheckTask {
    private FailoverRecovery failoverRecovery;

    private final static List<String> services = Arrays.asList(
            "tweetscollector",
            "sentimentanalysis",
            "reportservice");

    @Scheduled(initialDelay=45000, fixedDelay = 10000)
    public void reportCurrentTime() {
        if(failoverRecovery == null) {
            setFailoverRecovery();
        }

        failoverRecovery.checkOnRunningServices();
    }

    private void setFailoverRecovery() {
        failoverRecovery = new FailoverRecovery(services);
    }
}
