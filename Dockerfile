# ===============================
# Etapa base para desarrollo
# ===============================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Instala Maven dentro del contenedor (solo para desarrollo)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiamos los archivos necesarios
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente (aunque luego se montará como volumen)
COPY src ./src

# Exponer el puerto donde corre Spring Boot
EXPOSE 8080

# Comando principal de desarrollo
# Ejecuta Spring Boot directamente con Maven
CMD ["mvn", "spring-boot:run"]


