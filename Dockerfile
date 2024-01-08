FROM eclipse-temurin:17.0.5_8-jre-focal

VOLUME /tmp

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

# Build and run service from terminal
# Step 1 - build the image
# docker build -t marketing-customer .
# Step 2 - start the container
# docker run --name marketing-customer --rm -p8080:8080 -e "SPRING_PROFILES_ACTIVE=default" marketing-customer
# Step 3 - access the service
# http://localhost:8080/openapi/swagger-ui.html
# Step 4 - stop the service and container
# docker stop marketing-customer
# Step 5 - delete the image
# docker image rm marketing-customer