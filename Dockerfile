# Step 1: Use a Java Runtime
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory
WORKDIR /app

# Step 3: Copy the JAR file (Make sure you've run 'mvn package' first!)
COPY target/*.jar app.jar

# Step 4: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]