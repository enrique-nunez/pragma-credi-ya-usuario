# Dockerfile para microservicio de Autenticación (Usuario)
# Etapa 1: Construcción
FROM gradle:8.14.3-jdk21 AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY gradle.properties .
COPY main.gradle .
COPY settings.gradle .
COPY build.gradle .
COPY gradlew .
COPY gradle/ gradle/

# Copiar el código fuente
COPY applications/ applications/
COPY domain/ domain/
COPY infrastructure/ infrastructure/

# Construir solo el jar de la aplicación, saltando validaciones
RUN ./gradlew :app-service:bootJar --no-daemon -x validateStructure

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk-alpine

# Instalar herramientas útiles para debugging
RUN apk add --no-cache curl wget

# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR construido desde la etapa anterior
COPY --from=builder /app/applications/app-service/build/libs/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8081

# Variables de entorno por defecto (se pueden sobrescribir en docker-compose)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=docker

# Crear usuario no-root para seguridad
RUN adduser -D -s /bin/sh appuser
USER appuser

# Punto de entrada de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
