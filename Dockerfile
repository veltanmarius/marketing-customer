FROM eclipse-temurin:17.0.5_8-jre-focal

VOLUME /tmp

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]