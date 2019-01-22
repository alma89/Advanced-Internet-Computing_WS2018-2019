TUWien
Docker-based Service Composition Project

Institution: TU Vienna

Academic year: 2017/2018

Subject: Advanced Internet Computing

Programming language: Java

Team work - group of 5 people

# Introduction 
The composition performs a sentiment analysis for tweets. Sentiment analysis is, broadly, the process of finding out
(typically automatically) what the general feeling ("sentiment") of one or more Web communities
(for instance, the blogosphere or the Twitter community) about a company or product is. This
sort of analysis has become an increasingly relevant marketing tool in recent years. In short, a
service composition is a sequence of services, together forming a complex application. 

Essentially, the service composition should expect a list of terms as input, e.g. <Microsoft,
Apple, Audi>. Then, for each of those terms, a set of tweets should be loaded from the Twitter
Service. These tweets are forwarded to an Analysis Service for sentiment processing. Once the
individual values for each term are available, those terms and sentiment values are passed to the
Report Service, which generates a PDF report. Each of these services needs to be deployed in a
Docker Container. The modelling and execution/enactment of the process should be done using
a BPMN 2.0 engine like Camunda2 or jBPM3.

# Starting the services
1. Run `docker-compose up` (use `--build` to be sure to rebuild all dockerfiles again)
2. To stop use `docker-compose down`
* Services should be available under `192.168.99.100` when using docker toolbox

# Contributions
Each group member has contributed in equal parts to the project. 
Since we had a few group meetings, we have worked together on services and discussed issues together.
We have also tested and corrected errors together during our group meetings.
The main contributions of each group member:
1. Nourizadeh Barabi Manouchehr: Twitter Service, UI, Docker
2. Gruzdev Ievgenii: Analysis Service, Docker
3. Alma Causevic: Report Service, Docker
4. Eitenberger Christoph: Failover Service, BPMN, Docker

Nevertheless we can say that every group member worked and was involved also on every other part, due to a number of group meetings.
