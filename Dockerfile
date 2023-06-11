FROM eclipse-temurin:17

# Copying the artifact
COPY ["./build/libs/flashcards_bootjar.jar","/opt/app/app.jar"]

# Database configuration 
COPY ["./.env","/.env"] 

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/app.jar"]