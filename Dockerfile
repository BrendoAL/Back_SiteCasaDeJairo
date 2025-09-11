# 1️⃣ Imagem base com Java 17
FROM eclipse-temurin:23-jdk

# 2️⃣ Definir diretório de trabalho
WORKDIR /app

# 3️⃣ Copiar o arquivo JAR construído
COPY target/APICasaDeJairo-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Expor porta que a API vai rodar
EXPOSE 8088

# 5️⃣ Rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
