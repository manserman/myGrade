# ===================== STAGE 1 : BUILD =====================
FROM maven:3.8.6-eclipse-temurin-17 AS build

# 1. Définition du répertoire de travail
WORKDIR /app

# 2. Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# 3. Copier le code source et compiler le projet
COPY src/ src/
RUN mvn clean package -DskipTests

# ===================== STAGE 2 : RUNTIME =====================
FROM eclipse-temurin:17-jre-jammy

# 4. Répertoire de travail
WORKDIR /app

# 5. Copier le JAR compilé depuis l’étape 'build'

COPY --from=build /app/target/*.jar ./school.jar

# 6. Exposer le port 8080
EXPOSE 8080

# 7. Lancer l'application
ENTRYPOINT ["java", "-jar", "school.jar"]
