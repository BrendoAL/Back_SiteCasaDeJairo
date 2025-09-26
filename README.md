# 🏠 Casa de Jairo - API Backend

> API REST para gerenciamento das atividades da ONG Casa de Jairo

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Render](https://img.shields.io/badge/Deploy-Render-46E3B7.svg)](https://render.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📖 Sobre o Projeto

Este é o backend do projeto da ONG *Casa de Jairo*, uma organização sem fins lucrativos que promove ações sociais voltadas ao cuidado e desenvolvimento de crianças em vulnerabilidade social. 


### 🎯 Objetivos

- Centralizar informações dos beneficiários e voluntários
- Facilitar o gerenciamento de doações e recursos
- Organizar eventos e atividades da ONG
- Integrar com sistema web da organização

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17** 
- **Spring Boot 3.2** 
- **Spring Security** 
- **Spring Data JPA** 
- **MySQL 8.0**
- **Maven** 
- **JUnit 5** 
- **JWT** 

### Ferramentas
- **Postman** - Testes e documentação de API
- **Kubernetes** - Orquestração de containers

### Infraestrutura
- **Fly.io** - Deploy e hospedagem
- **GitHub Actions** - CI/CD
- **Docker** - Containerização

## 🚀 Funcionalidades
- **Voluntários**: Cadastro e gerenciamento de voluntários.
- **Empresas**: Cadastro e gerenciamento de empresas.
- **Doações**: Facilidade para contribuição 
- **Eventos**: Organização de eventos da ONG
- **Atividades**: Programas e projetos em andamento

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+
- Git
- Docker (opcional)

## 🔧 Instalação e Configuração

### 1. Clone o repositório
```bash
git clone https://github.com/BrendoAL/Back_SiteCasaDeJairo.git
cd Back_SiteCasaDeJairo
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` ou configure as seguintes variáveis:

```bash
# Database
DATABASE_URL=jdbc:mysql://localhost:3306/casa_de_jairo
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

# Fly.io (produção)
FLY_APP_NAME=api-casa-de-jairo
```

### 3. Configure o banco de dados
```sql
CREATE DATABASE casa_de_jairo;
CREATE USER 'casa_user'@'localhost' IDENTIFIED BY 'sua_senha';
GRANT ALL PRIVILEGES ON casa_de_jairo.* TO 'casa_user'@'localhost';
FLUSH PRIVILEGES;
```

### 4. Execute a aplicação
```bash
# Desenvolvimento
./mvnw spring-boot:run

# Ou compile e execute
./mvnw clean package
java -jar target/casa-de-jairo-api-1.0.0.jar
```

🚀 Deploy
Fly.io (Produção)

Instale o CLI do Fly.io: curl -L https://fly.io/install.sh | sh

Faça login: fly auth login

Inicialize a aplicação: fly launch

Configure variáveis de ambiente: fly secrets set DATABASE_URL=... DB_USERNAME=... DB_PASSWORD=...

Faça deploy: fly deploy

Acesse: https://api-casa-de-jairo.fly.dev

### Docker (Alternativo)

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/casa-de-jairo-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
docker build -t casa-de-jairo-api .
docker run -p 8080:8080 casa-de-jairo-api
```

### Kubernetes

Para deploy em Kubernetes, use os arquivos de configuração:

```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: casa-de-jairo-api
spec:
  replicas: 3
  selector:
    matchLabels:
      app: casa-de-jairo-api
  template:
    metadata:
      labels:
        app: casa-de-jairo-api
    spec:
      containers:
      - name: api
        image: casa-de-jairo-api:latest
        ports:
        - containerPort: 8080
        env:
        - name: DATABASE_URL
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: url
---
apiVersion: v1
kind: Service
metadata:
  name: casa-de-jairo-service
spec:
  selector:
    app: casa-de-jairo-api
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
```

```bash
kubectl apply -f deployment.yaml
kubectl get pods
kubectl get services
```

## 🤝 Contribuição

Estamos sempre abertos a sugestões e contribuições!

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👨‍💻 Autores

**Brendo** - [GitHub](https://github.com/BrendoAL)

  
**Rafael** - [GitHub](https://github.com/rafael2297)

## 🙏 Agradecimentos

- Casa de Jairo pela oportunidade de contribuir
- Colaboradores do projeto

## 📞 Contato

- **ONG Casa de Jairo**: https://casadejairo.online/
- **Email**: contato.casadejairo@gmail.com
- **Fone**: (47) 99181-0946

---

<div align="center">
  <p>Feito com ❤️ para a Casa de Jairo</p>
  <p>Ajudando a transformar vidas através da tecnologia</p>
</div>

---
