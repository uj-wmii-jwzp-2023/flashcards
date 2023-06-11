FROM eclipse-temurin:17
RUN ["mkdir","/opt/app"]
COPY ["./build/libs/flashcards_bootjar.jar","/opt/app/app.jar"]
COPY ["./.env","/.env"] #database settings
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/app.jar"]