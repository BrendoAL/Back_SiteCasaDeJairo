# Build da aplicação com Maven e JDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Build do JAR
RUN mvn clean package -DskipTests -B

# Roda a aplicação com JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o JAR do build
COPY --from=build /app/target/APICasaDeJairo-0.0.1-SNAPSHOT.jar app.jar

# Copia script de inicialização
COPY start.sh ./start.sh
RUN chmod +x start.sh

# Configurações JVM otimizadas
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

# Porta dinâmica do Render
EXPOSE $PORT

# Comando de inicialização
CMD ["./start.sh"]