FROM registry.fit2cloud.com/fit2cloud2/fabric8-java-alpine-openjdk8-jre

RUN mkdir -p /opt/apps

ADD target/fit2cloud2.0-ticket-center-2.0.0.jar /opt/apps

ENV JAVA_APP_JAR=/opt/apps/fit2cloud2.0-ticket-center-2.0.0.jar

ENV AB_OFF=true

ENV JAVA_OPTIONS=-Dfile.encoding=utf-8

HEALTHCHECK --interval=15s --timeout=5s --retries=20 --start-period=30s CMD curl -f 127.0.0.1:6607

CMD ["/deployments/run-java.sh"]
