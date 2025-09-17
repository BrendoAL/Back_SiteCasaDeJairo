# Build da aplicação com Maven e JDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências primeiro (otimização de cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Build do JAR
RUN mvn clean package -DskipTests -B

# Roda a aplicação com JRE mais leve
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Cria usuário não-root por segurança
RUN addgroup -g 1001 -S appgroup && \
    adduser -S appuser -u 1001 -G appgroup

# Copia o JAR do build
COPY --from=build /app/target/APICasaDeJairo-0.0.1-SNAPSHOT.jar app.jar

# Muda para o usuário não-root
RUN chown -R appuser:appgroup /app
USER appuser

# Configurações JVM otimizadas para containers
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"

# Porta dinâmica do Render
EXPOSE $PORT

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:$PORT/actuator/health || exit 1

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]