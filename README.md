# 🏥 Sistema de Gestión Clínica - Backend

Sistema REST para la gestión de información de **pacientes**, **usuarios**, **médicos** y **especializaciones** en una clínica médica.

## 🚀 Tecnologías

- **Java 21** con Spring Boot 2.7.18
- **Spring Data JPA** (Hibernate) para persistencia
- **MySQL 8.0** como base de datos
- **Spring Security** para autenticación y autorización
- **Lombok** para reducir código boilerplate
- **Maven** como gestor de dependencias
- **Docker Compose** para contenedorización de la base de datos

## 📋 Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- Docker y Docker Compose
- MySQL 8.0 (opcional, se puede usar Docker)

## 🛠️ Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd 75321-backend
```

### 2. Configurar la base de datos con Docker
```bash
docker-compose up -d
```

Esto iniciará MySQL en el puerto `3307` con las siguientes credenciales:
- **Host**: localhost:3307
- **Base de datos**: clinica_db
- **Usuario**: admin
- **Contraseña**: password1

### 3. Configurar la aplicación
El archivo `src/main/resources/application.properties` contiene la configuración de conexión a la base de datos.

### 4. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8000/clinica/v1**

## 🏗️ Arquitectura del Proyecto

```
com.uniminuto.clinica/
├── api/                    # Interfaces de endpoints REST
│   ├── UsuarioApi.java
│   ├── PacienteApi.java
│   ├── MedicoApi.java
│   └── ClinicaApi.java
├── apicontroller/          # Implementaciones de controladores REST
│   ├── UsuarioApiController.java
│   ├── PacienteApiController.java
│   ├── MedicoApiController.java
│   └── ClinicaApiController.java
├── entity/                 # Entidades JPA
│   ├── Usuario.java
│   ├── Paciente.java
│   ├── Medico.java
│   └── Especializacion.java
├── model/                  # DTOs para transferencia de datos
│   ├── UsuarioRs.java
│   └── RespuestaRs.java
├── repository/             # Repositorios Spring Data JPA
│   ├── UsuarioRepository.java
│   ├── PacienteRepository.java
│   ├── MedicoRepository.java
│   └── EspecializacionRepository.java
├── service/                # Interfaces de servicios
│   ├── UsuarioService.java
│   ├── PacienteService.java
│   ├── MedicoService.java
│   ├── ClinicaService.java
│   └── CitaService.java
├── service/impl/           # Implementaciones de servicios
│   ├── UsuarioServiceImpl.java
│   ├── PacienteServiceImpl.java
│   ├── MedicoServiceImpl.java
│   └── ClinicaServiceImpl.java
├── security/               # Configuración de seguridad
│   └── SecurityConfig.java
├── utils/                  # Utilidades
│   └── DateFormatterService.java
├── exception/              # Manejo de excepciones
│   └── EspecializacionNotFoundException.java
└── ClinicaApplication.java # Clase principal de Spring Boot
```

## 📊 Modelo de Datos

### Entidades Principales

#### Usuario
- **id**: Identificador único (BIGINT UNSIGNED)
- **username**: Nombre de usuario único
- **password_hash**: Hash de la contraseña
- **rol**: Rol del usuario en el sistema
- **fecha_creacion**: Fecha de creación del usuario
- **activo**: Estado del usuario (activo/inactivo)

#### Paciente
- **id**: Identificador único
- **usuario_id**: FK hacia Usuario (opcional)
- **tipo_documento**: Tipo de documento de identidad
- **numero_documento**: Número de documento único
- **nombres**: Nombres del paciente
- **apellidos**: Apellidos del paciente
- **fecha_nacimiento**: Fecha de nacimiento
- **genero**: Género (M/F)
- **telefono**: Número de teléfono
- **direccion**: Dirección del paciente

#### Médico
- **id**: Identificador único
- **tipo_documento**: Tipo de documento
- **numero_documento**: Número de documento
- **nombres**: Nombres del médico
- **apellidos**: Apellidos del médico
- **telefono**: Número de teléfono
- **registro_profesional**: Número de registro profesional
- **especializacion_id**: FK hacia Especialización

## 🔗 Endpoints Disponibles

### Usuarios
- **GET** `/usuarios/buscar-por-documento/{numeroDocumento}`
  - Busca un usuario por el número de documento del paciente asociado
  - Retorna: `UsuarioRs` con información del usuario

### Pacientes
- Endpoints para gestión de pacientes (implementados en `PacienteApi`)

### Médicos
- Endpoints para gestión de médicos (implementados en `MedicoApi`)

### Clínica
- Endpoints generales de la clínica (implementados en `ClinicaApi`)

## 🔒 Seguridad

El sistema incluye configuración de Spring Security con:
- **CORS** habilitado para múltiples orígenes
- **CSRF** deshabilitado para pruebas con Postman
- **Autenticación** configurada (actualmente permitiendo todas las rutas)

### Orígenes permitidos:
- `http://localhost:4200` (Angular)
- `http://localhost:8080`
- `http://127.0.0.1:8080`
- `http://127.0.0.1:4200`
- `http://10.0.5.50:8080`
- `http://10.0.5.50:4200`

## 🗄️ Base de Datos

### Configuración de la BD
```sql
-- Alinear tipos de datos
ALTER TABLE paciente
  MODIFY COLUMN usuario_id BIGINT UNSIGNED NULL;

-- Crear FK para integridad referencial
ALTER TABLE paciente
  ADD CONSTRAINT fk_paciente_usuario
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
  ON UPDATE CASCADE
  ON DELETE SET NULL;
```

### Índices
- **paciente**: PRIMARY (id), UNIQUE (numero_documento), INDEX (usuario_id)
- **usuario**: PRIMARY (id), UNIQUE (username)

## 🧪 Ejemplo de Uso
## mysql -uroot -p -h 127.0.0.1 -P 3306
## git branch -d rama_eliminar


### Buscar usuario por documento de paciente
```bash
curl -X GET "http://localhost:8000/clinica/v1/usuarios/buscar-por-documento/1002003001"
```

**Respuesta esperada:**
```json
{
  "id": 1,
  "username": "usuario123",
  "rol": "PACIENTE",
  "fechaCreacion": "2024-01-15T10:30:00",
  "activo": true
}
```

## 🚀 Desarrollo

### Compilar el proyecto
```bash
mvn clean compile
```

### Ejecutar tests
```bash
mvn test
```

### Generar JAR ejecutable
```bash
mvn clean package
java -jar target/clinica-0.0.1-SNAPSHOT.jar
```

## 📝 Características Implementadas

- ✅ **Arquitectura por capas** (API, Controller, Service, Repository)
- ✅ **DTOs** para transferencia segura de datos
- ✅ **Relaciones JPA** entre entidades
- ✅ **Spring Security** configurado
- ✅ **CORS** habilitado
- ✅ **Docker Compose** para base de datos
- ✅ **Lombok** para reducir código boilerplate
- ✅ **Manejo de excepciones** personalizado
- ✅ **Búsqueda de usuario por documento de paciente**

## 🔧 Configuración Adicional

### Variables de entorno
Puedes configurar las siguientes variables en `application.properties`:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `server.port`
- `server.servlet.context-path`


**Desarrollado para Uniminuto - Programación Web** 🎓