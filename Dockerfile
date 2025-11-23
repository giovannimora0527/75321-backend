# ============================================
# Etapa 1: Construcción con Maven
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# ============================================
# Etapa 2: Imagen final para ejecutar
# ============================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]