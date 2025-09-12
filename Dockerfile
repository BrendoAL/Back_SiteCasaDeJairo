# Build da aplicação com Maven e JDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Build do JAR
RUN mvn clean package -DskipTests

# Segunda fase: rodar a aplicação com JDK leve
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/APICasaDeJairo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "app.jar"]
