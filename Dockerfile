# Etapa 1: Construcción de la aplicación con Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (esto se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jre-alpine

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/clinica-0.0.1-SNAPSHOT.jar app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto de la aplicación
EXPOSE 8000

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
