FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# add directly the war
ADD *.war /app.war

EXPOSE 8081
CMD echo "The application will start in ${APPLICATION_SLEEP}s..." && \
    sleep ${APPLICATION_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war
