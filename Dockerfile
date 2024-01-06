FROM eclipse-temurin:17.0.5_8-jre-focal

VOLUME /tmp

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

# Build and run service from terminal
# Step 1
# docker build -t marketing-customer .
# Step 2
# docker run --name marketing-customer --rm -p8080:8080 -e "SPRING_PROFILES_ACTIVE=default" marketing-customer