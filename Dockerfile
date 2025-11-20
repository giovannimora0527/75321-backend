# ETAPA 1: Construcción (Build)
# Usamos la imagen oficial de Maven con Java 21 (Temurin)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos dependencias y código
COPY pom.xml .
COPY src ./src

# Empaquetamos (saltando tests para velocidad)
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Run)
# CAMBIO AQUÍ: Usamos eclipse-temurin:21-jre que es la versión ligera y estable
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/target/clinica-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8000
EXPOSE 8000

# Comando de inicio
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]