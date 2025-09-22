# 72151
Taller Springboot para Uniminuto Programacion web

---

## 1. ENTIDADES (Entity)

### 1.1 Medicamento.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/entity/Medicamento.java`

```java
@Entity
@Table(name = "medicamento")
public class Medicamento implements Serializable
```

**Descripción:** Entidad JPA que representa un medicamento en el sistema de clínica.

**Campos principales:**
- `id`: Identificador único del medicamento (auto-generado)
- `nombre`: Nombre del medicamento
- `descripcion`: Descripción detallada del medicamento
- `presentacion`: Presentación del medicamento (tabletas, jarabe, etc.)
- `fechaCompra`: Fecha de compra del medicamento
- `fechaVence`: Fecha de vencimiento del medicamento
- `fechaCreacionRegistro`: Timestamp de creación del registro
- `fechaModificacionRegistro`: Timestamp de última modificación

**Observaciones para uso:**
- Utiliza Lombok `@Data` para generar automáticamente getters, setters, equals, hashCode y toString
- Implementa `Serializable` para permitir serialización
- Los campos de fecha utilizan tipos `LocalDate` y `LocalDateTime` de Java 8+
- Campos obligatorios: nombre, descripcion, presentacion, fechaCompra, fechaVence

### 1.2 Receta.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/entity/Receta.java`

```java
@Entity
@Table(name = "receta")
public class Receta implements Serializable
```

**Descripción:** Entidad JPA que representa una receta médica asociada a una cita y un medicamento.

**Campos principales:**
- `id`: Identificador único de la receta (auto-generado)
- `cita`: Relación ManyToOne con la entidad Cita
- `medicamento`: Relación ManyToOne con la entidad Medicamento
- `dosis`: Dosis del medicamento prescrito
- `indicaciones`: Indicaciones de uso del medicamento
- `fechaCreacionRegistro`: Timestamp de creación del registro

**Observaciones para uso:**
- Establece relaciones con las entidades Cita y Medicamento
- Utiliza `@JoinColumn` para definir las claves foráneas
- Campos obligatorios: cita, medicamento, dosis, indicaciones

---

## 2. INTERFACES API

### 2.1 ClinicaApi.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/api/ClinicaApi.java`

```java
@CrossOrigin(origins = "*")
@RequestMapping("/clinica")
public interface ClinicaApi
```

#### Método: testService()

```java
/**
 * Servicio de prueba para verificar el funcionamiento de la API de clínica
 * @return ResponseEntity con el resultado del test
 * @author lmora
 */
@RequestMapping(value = "/test", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity testService();
```

**Observaciones para uso:**
- Endpoint de prueba accesible en `GET /clinica/test`
- Permite verificar que el servicio esté funcionando correctamente
- Configurado para CORS con origen "*"

### 2.2 MedicamentoApi.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/api/MedicamentoApi.java`

```java
@CrossOrigin(origins = "*")
@RequestMapping("/medicamento")
public interface MedicamentoApi
```

#### Método: listarMedicamentos()

```java
/**
 * Lista todos los medicamentos disponibles en el sistema
 * @return ResponseEntity<List<Medicamento>> Lista de medicamentos
 */
@RequestMapping(value = "/listar", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity<List<Medicamento>> listarMedicamentos();
```

#### Método: listarMedNombre()

```java
/**
 * Busca un medicamento específico por su nombre
 * @param nombre Nombre del medicamento a buscar
 * @return ResponseEntity<Medicamento> Medicamento encontrado
 * @throws BadRequestException Si no se encuentra el medicamento
 */
@RequestMapping(value = "/nombre", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity<Medicamento> listarMedNombre(@RequestParam String nombre) throws BadRequestException;
```

#### Método: guardarMedicamento()

```java
/**
 * Guarda un nuevo medicamento en el sistema
 * @param medicamentoRq Datos del medicamento a guardar
 * @return ResponseEntity<RespuestaRs> Respuesta con el resultado de la operación
 * @throws BadRequestException Si los datos de entrada son inválidos
 */
@RequestMapping(value = "/guardar", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
ResponseEntity<RespuestaRs> guardarMedicamento(@RequestBody MedicamentoRq medicamentoRq) throws BadRequestException;
```

#### Método: actualizarMedicamento()

```java
/**
 * Actualiza un medicamento existente en el sistema
 * @param medicamentoRq Datos del medicamento a actualizar (debe incluir ID)
 * @return ResponseEntity<RespuestaRs> Respuesta con el resultado de la operación
 * @throws BadRequestException Si los datos de entrada son inválidos o el medicamento no existe
 */
@RequestMapping(value = "/actualizar", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
ResponseEntity<RespuestaRs> actualizarMedicamento(@RequestBody MedicamentoRq medicamentoRq) throws BadRequestException;
```

**Observaciones para uso:**
- Todos los endpoints manejan contenido JSON
- Configurado para CORS con origen "*"
- Utiliza `MedicamentoRq` como DTO para recibir datos
- Valida datos de entrada antes de procesarlos

### 2.3 RecetaApi.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/api/RecetaApi.java`

```java
@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public interface RecetaApi
```

#### Método: listarTodasLasRecetas()

```java
/**
 * Lista todas las recetas registradas en el sistema
 * @return ResponseEntity<List<Receta>> Lista completa de recetas
 * @author AORUS
 */
@RequestMapping(value = "/all", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity<List<Receta>> listarTodasLasRecetas();
```

#### Método: listarRecetasPorCita()

```java
/**
 * Lista todas las recetas asociadas a una cita específica
 * @param citaId ID de la cita para filtrar las recetas
 * @return ResponseEntity<List<Receta>> Lista de recetas de la cita especificada
 */
@RequestMapping(value = "/by-cita", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity<List<Receta>> listarRecetasPorCita(@RequestParam Integer citaId);
```

#### Método: listarRecetasPorMedicamento()

```java
/**
 * Lista todas las recetas que incluyen un medicamento específico
 * @param medicamentoId ID del medicamento para filtrar las recetas
 * @return ResponseEntity<List<Receta>> Lista de recetas con el medicamento especificado
 */
@RequestMapping(value = "/by-medicamento", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.GET)
ResponseEntity<List<Receta>> listarRecetasPorMedicamento(@RequestParam Integer medicamentoId);
```

#### Método: guardarReceta()

```java
/**
 * Guarda una nueva receta en el sistema
 * @param receta Datos de la receta a guardar (RecetaRq DTO)
 * @return ResponseEntity<RespuestaRs> Respuesta con el resultado de la operación
 * @throws BadRequestException Si los datos de entrada son inválidos o las entidades relacionadas no existen
 */
@RequestMapping(value = "/guardar", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
ResponseEntity<RespuestaRs> guardarReceta(@RequestBody RecetaRq receta) throws BadRequestException;
```

**Observaciones para uso:**
- Proporciona endpoints para gestión completa de recetas
- Permite filtrar recetas por cita o medicamento
- Valida existencia de citas y medicamentos antes de crear recetas
- Configurado para CORS con origen "*"

---

## 3. SERVICIOS (Service Interfaces)

### 3.1 ClinicaService.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/ClinicaService.java`

#### Método: test()

```java
/**
 * Método de prueba para verificar el funcionamiento del servicio de clínica
 * @return RespuestaRs Objeto de respuesta con estado y mensaje
 * @author lmora
 */
RespuestaRs test();
```

### 3.2 MedicamentoService.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/MedicamentoService.java`

#### Método: listarMedicamentos()

```java
/**
 * Obtiene todos los medicamentos registrados en el sistema
 * @return List<Medicamento> Lista de medicamentos ordenada por fecha de compra
 */
List<Medicamento> listarMedicamentos();
```

#### Método: listarMedNombre()

```java
/**
 * Busca un medicamento específico por su nombre exacto
 * @param nombre Nombre del medicamento a buscar
 * @return Medicamento Entidad del medicamento encontrado
 * @throws BadRequestException Si no existe el medicamento con el nombre especificado
 */
Medicamento listarMedNombre(String nombre) throws BadRequestException;
```

#### Método: guardarMedicamento()

```java
/**
 * Guarda un nuevo medicamento en el sistema después de validar los datos
 * @param medicamentoRq DTO con los datos del medicamento a guardar
 * @return RespuestaRs Respuesta con estado de la operación
 * @throws BadRequestException Si faltan campos obligatorios o ya existe un medicamento con el mismo nombre
 */
RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException;
```

#### Método: actualizarMedicamento()

```java
/**
 * Actualiza un medicamento existente con nuevos datos
 * @param medicamentoRq DTO con los datos actualizados del medicamento (debe incluir ID)
 * @return RespuestaRs Respuesta con estado de la operación
 * @throws BadRequestException Si el ID es nulo, el medicamento no existe, o hay conflictos de nombre
 */
RespuestaRs actualizarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException;
```

### 3.3 RecetaService.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/RecetaService.java`

#### Método: listarTodasLasRecetas()

```java
/**
 * Obtiene todas las recetas registradas en el sistema
 * @return List<Receta> Lista completa de recetas o lista vacía si no hay registros
 * @author AORUS
 */
List<Receta> listarTodasLasRecetas();
```

#### Método: listarRecetasPorCita()

```java
/**
 * Obtiene todas las recetas asociadas a una cita específica
 * @param citaId ID de la cita para filtrar las recetas
 * @return List<Receta> Lista de recetas de la cita o lista vacía si no hay registros
 */
List<Receta> listarRecetasPorCita(Integer citaId);
```

#### Método: listarRecetasPorMedicamento()

```java
/**
 * Obtiene todas las recetas que incluyen un medicamento específico
 * @param medicamentoId ID del medicamento para filtrar las recetas
 * @return List<Receta> Lista de recetas con el medicamento especificado
 */
List<Receta> listarRecetasPorMedicamento(Integer medicamentoId);
```

#### Método: guardarReceta()

```java
/**
 * Guarda una nueva receta en el sistema después de validar datos y dependencias
 * @param recetaNueva DTO con los datos de la nueva receta
 * @return RespuestaRs Respuesta con estado de la operación
 * @throws BadRequestException Si faltan campos obligatorios o las entidades relacionadas no existen
 */
RespuestaRs guardarReceta(RecetaRq recetaNueva) throws BadRequestException;
```

---

## 4. IMPLEMENTACIONES DE SERVICIOS (Service Implementations)

### 4.1 ClinicaServiceImpl.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/impl/ClinicaServiceImpl.java`

#### Método: test()

```java
/**
 * Implementación del método de prueba del servicio de clínica
 * @return RespuestaRs Objeto con mensaje de confirmación y status 200
 * @author lmora
 */
@Override
public RespuestaRs test() {
    RespuestaRs rta = new RespuestaRs();
    rta.setMensaje("El servicio de clinica esta funcionando MELO"); 
    rta.setStatus(200);
    return rta;
}
```

**Observaciones para uso:**
- Método simple para verificar conectividad del servicio
- Retorna mensaje personalizado "MELO" para identificar la respuesta

### 4.2 MedicamentoServiceImpl.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/impl/MedicamentoServiceImpl.java`

#### Método: listarMedicamentos()

```java
/**
 * Lista todos los medicamentos ordenados por fecha de compra
 * @return List<Medicamento> Lista ordenada cronológicamente por fecha de compra
 */
@Override
public List<Medicamento> listarMedicamentos() {
    List<Medicamento> lista = medicamentoRepository.findAll();
    lista.sort(Comparator.comparing(Medicamento::getFechaCompra));
    return lista;
}
```

#### Método: listarMedNombre()

```java
/**
 * Busca un medicamento por nombre exacto
 * @param nombre Nombre del medicamento a buscar
 * @return Medicamento Entidad encontrada
 * @throws BadRequestException con mensaje "No existe el medicamento" si no se encuentra
 */
@Override
public Medicamento listarMedNombre(String nombre) throws BadRequestException {
    Optional<Medicamento> optMedicamento = this.medicamentoRepository.findByNombre(nombre);
    if (!optMedicamento.isPresent()) {
        throw new BadRequestException("No existe el medicamento");
    }
    return optMedicamento.get();
}
```

#### Método: guardarMedicamento()

```java
/**
 * Guarda un nuevo medicamento después de validaciones completas
 * Proceso: 1) Validar campos, 2) Verificar nombre único, 3) Crear entidad, 4) Guardar, 5) Responder
 * @param medicamentoRq DTO con datos del medicamento
 * @return RespuestaRs Confirmación de guardado exitoso
 * @throws BadRequestException Si faltan campos o existe nombre duplicado
 */
@Override
public RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
    // Validaciones y proceso de guardado...
}
```

#### Método: validarCampos()

```java
/**
 * Valida que todos los campos obligatorios estén presentes y no vacíos
 * @param medicamentoRq DTO a validar
 * @throws BadRequestException Con mensaje específico del campo faltante
 */
private void validarCampos(MedicamentoRq medicamentoRq) throws BadRequestException {
    // Validaciones de nombre, descripción, presentación, fechas...
}
```

#### Método: convertirAMedicamento()

```java
/**
 * Convierte un DTO de request a entidad Medicamento
 * @param medicamentoRq DTO de entrada
 * @return Medicamento Entidad preparada para guardar con timestamp actual
 */
private Medicamento convertirAMedicamento(MedicamentoRq medicamentoRq) {
    // Mapeo de campos y asignación de timestamp de creación
}
```

#### Método: actualizarMedicamento()

```java
/**
 * Actualiza un medicamento existente con validaciones de integridad
 * Proceso: 1) Validar ID, 2) Verificar existencia, 3) Validar nombre único, 4) Actualizar, 5) Guardar
 * @param medicamentoRq DTO con datos actualizados (debe incluir ID)
 * @return RespuestaRs Confirmación de actualización exitosa
 * @throws BadRequestException Si no existe el registro o hay conflictos de nombre
 */
@Override
public RespuestaRs actualizarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
    // Proceso completo de actualización con validaciones...
}
```

**Observaciones para uso:**
- Implementación robusta con validaciones completas
- Manejo de campos opcionales en actualización (null-safe)
- Ordenamiento automático por fecha de compra en listados
- Timestamps automáticos de creación y modificación

### 4.3 RecetaServiceImpl.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/service/impl/RecetaServiceImpl.java`

#### Método: listarTodasLasRecetas()

```java
/**
 * Obtiene todas las recetas del sistema
 * @return List<Receta> Lista de recetas o Collections.EMPTY_LIST si no hay datos
 * @author AORUS
 */
@Override
public List<Receta> listarTodasLasRecetas() {
    List<Receta> lista = this.recetaRepository.findAll();
    return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
}
```

#### Método: listarRecetasPorCita()

```java
/**
 * Filtra recetas por ID de cita específica
 * @param citaId ID de la cita para filtrar
 * @return List<Receta> Lista filtrada o Collections.EMPTY_LIST
 */
@Override
public List<Receta> listarRecetasPorCita(Integer citaId) {
    List<Receta> lista = this.recetaRepository.findByCitaId(citaId);
    return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
}
```

#### Método: listarRecetasPorMedicamento()

```java
/**
 * Filtra recetas por medicamento - PENDIENTE DE IMPLEMENTACIÓN
 * @param medicamentoId ID del medicamento para filtrar
 * @return List<Receta> Lista vacía (implementación pendiente)
 */
@Override
public List<Receta> listarRecetasPorMedicamento(Integer medicamentoId) {
    return List.of(); // TODO: Implementar consulta por medicamento
}
```

#### Método: guardarReceta()

```java
/**
 * Guarda una nueva receta después de validar dependencias
 * Proceso: 1) Validar campos, 2) Verificar cita existe, 3) Verificar medicamento existe, 4) Crear y guardar
 * @param recetaNueva DTO con datos de la receta
 * @return RespuestaRs Confirmación de guardado exitoso
 * @throws BadRequestException Si faltan campos o las entidades relacionadas no existen
 */
@Override
public RespuestaRs guardarReceta(RecetaRq recetaNueva) throws BadRequestException {
    validarCamposObligatorios(recetaNueva);
    
    // Validar existencia de cita
    Optional<Cita> citaOpt = this.citaRepository.findById(recetaNueva.getCitaId());
    if (!citaOpt.isPresent()) {
        throw new BadRequestException("La cita no existe");
    }
    
    // Validar existencia de medicamento
    Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaNueva.getMedicamentoId());
    if (!optMedicamento.isPresent()) {
        throw new BadRequestException("El medicamento no existe");
    }
    
    // Crear y guardar nueva receta...
}
```

#### Método: validarCamposObligatorios()

```java
/**
 * Valida que todos los campos obligatorios de una receta estén presentes
 * @param recetaNuevo DTO a validar
 * @throws BadRequestException Con mensaje específico del campo faltante
 */
private void validarCamposObligatorios(RecetaRq recetaNuevo) throws BadRequestException {
    // Validaciones de citaId, medicamentoId, dosis e indicaciones
}
```

**Observaciones para uso:**
- Validación completa de integridad referencial con Cita y Medicamento
- Retorno de listas vacías en lugar de null para evitar NullPointerException
- Método `listarRecetasPorMedicamento()` requiere implementación
- Timestamps automáticos de creación

---

## 5. CONTROLADORES API (API Controllers)

### 5.1 ClinicaApiController.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/apicontroller/ClinicaApiController.java`

#### Método: testService()

```java
/**
 * Endpoint REST para probar el servicio de clínica
 * @return ResponseEntity<RespuestaRs> Respuesta HTTP con el resultado del test
 * @author lmora
 */
@Override
public ResponseEntity<RespuestaRs> testService() { 
    return ResponseEntity.ok(this.clinicaService.test());
}
```

**Observaciones para uso:**
- Controlador REST simple con inyección de dependencias
- Endpoint accesible en `GET /clinica/test`

### 5.2 MedicamentoApiController.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/apicontroller/MedicamentoApiController.java`

#### Método: listarMedicamentos()

```java
/**
 * Endpoint REST para obtener todos los medicamentos
 * @return ResponseEntity<List<Medicamento>> Lista de medicamentos con status HTTP 200
 */
@Override
public ResponseEntity<List<Medicamento>> listarMedicamentos() {
    return ResponseEntity.ok(medicamentoService.listarMedicamentos());
}
```

#### Método: listarMedNombre()

```java
/**
 * Endpoint REST para buscar medicamento por nombre
 * @param nombre Parámetro de consulta con el nombre del medicamento
 * @return ResponseEntity<Medicamento> Medicamento encontrado
 * @throws BadRequestException Si no se encuentra el medicamento
 */
@Override
public ResponseEntity<Medicamento> listarMedNombre(String nombre) throws BadRequestException {
    return ResponseEntity.ok(this.medicamentoService.listarMedNombre(nombre));
}
```

#### Método: guardarMedicamento()

```java
/**
 * Endpoint REST para crear un nuevo medicamento
 * @param medicamentoRq Cuerpo de la petición con datos del medicamento
 * @return ResponseEntity<RespuestaRs> Resultado de la operación de guardado
 * @throws BadRequestException Si los datos son inválidos
 */
@Override
public ResponseEntity<RespuestaRs> guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
    return ResponseEntity.ok(medicamentoService.guardarMedicamento(medicamentoRq));
}
```

#### Método: actualizarMedicamento()

```java
/**
 * Endpoint REST para actualizar un medicamento existente
 * @param medicamentoRq Cuerpo de la petición con datos actualizados (debe incluir ID)
 * @return ResponseEntity<RespuestaRs> Resultado de la operación de actualización
 * @throws BadRequestException Si los datos son inválidos o el medicamento no existe
 */
@Override
public ResponseEntity<RespuestaRs> actualizarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
    return ResponseEntity.ok(this.medicamentoService.actualizarMedicamento(medicamentoRq));
}
```

### 5.3 RecetaApiController.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/apicontroller/RecetaApiController.java`

#### Método: listarTodasLasRecetas()

```java
/**
 * Endpoint REST para obtener todas las recetas del sistema
 * @return ResponseEntity<List<Receta>> Lista completa de recetas con status HTTP 200
 * @author AORUS
 */
@Override
public ResponseEntity<List<Receta>> listarTodasLasRecetas() {
    return ResponseEntity.ok(this.recetaService.listarTodasLasRecetas());
}
```

#### Método: listarRecetasPorCita()

```java
/**
 * Endpoint REST para obtener recetas filtradas por cita
 * @param citaId Parámetro de consulta con el ID de la cita
 * @return ResponseEntity<List<Receta>> Lista de recetas de la cita especificada
 */
@Override
public ResponseEntity<List<Receta>> listarRecetasPorCita(Integer citaId) {
    return ResponseEntity.ok(this.recetaService.listarRecetasPorCita(citaId));
}
```

#### Método: listarRecetasPorMedicamento()

```java
/**
 * Endpoint REST para obtener recetas filtradas por medicamento
 * @param medicamentoId Parámetro de consulta con el ID del medicamento
 * @return ResponseEntity<List<Receta>> Lista de recetas con el medicamento especificado
 */
@Override
public ResponseEntity<List<Receta>> listarRecetasPorMedicamento(Integer medicamentoId) {
    return ResponseEntity.ok(this.recetaService.listarRecetasPorMedicamento(medicamentoId));
}
```

#### Método: guardarReceta()

```java
/**
 * Endpoint REST para crear una nueva receta
 * @param receta Cuerpo de la petición con datos de la receta
 * @return ResponseEntity<RespuestaRs> Resultado de la operación de guardado
 * @throws BadRequestException Si los datos son inválidos o las entidades relacionadas no existen
 */
@Override
public ResponseEntity<RespuestaRs> guardarReceta(RecetaRq receta) throws BadRequestException {
    return ResponseEntity.ok(this.recetaService.guardarReceta(receta));
}
```

**Observaciones para uso:**
- Todos los controladores implementan las interfaces API correspondientes
- Utilizan inyección de dependencias con `@Autowired`
- Retornan `ResponseEntity.ok()` para respuestas HTTP 200 exitosas
- Las excepciones `BadRequestException` son manejadas automáticamente por Spring

---

## 6. REPOSITORIOS (Repository Interfaces)

### 6.1 MedicamentoRepository.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/repository/MedicamentoRepository.java`

```java
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer>
```

#### Método: findByNombre()

```java
/**
 * Busca un medicamento por su nombre exacto
 * @param nombre Nombre del medicamento a buscar
 * @return Optional<Medicamento> Contenedor que puede contener el medicamento si existe
 */
Optional<Medicamento> findByNombre(String nombre);
```

**Observaciones para uso:**
- Extiende `JpaRepository` proporcionando operaciones CRUD básicas
- Método customizado para búsqueda por nombre
- Utiliza `Optional` para manejo seguro de valores nulos

### 6.2 RecetaRepository.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/repository/RecetaRepository.java`

```java
public interface RecetaRepository extends JpaRepository<Receta, Integer>
```

#### Método: findByCitaId()

```java
/**
 * Busca todas las recetas asociadas a una cita específica
 * @param citaId ID de la cita para filtrar las recetas
 * @return List<Receta> Lista de recetas de la cita (puede estar vacía)
 * @author AORUS
 */
List<Receta> findByCitaId(Integer citaId);
```

#### Método: findByMedicamentoId()

```java
/**
 * Busca todas las recetas que incluyen un medicamento específico
 * @param medicamentoId ID del medicamento para filtrar las recetas
 * @return List<Receta> Lista de recetas con el medicamento (puede estar vacía)
 */
List<Receta> findByMedicamentoId(Integer medicamentoId);
```

**Observaciones para uso:**
- Extiende `JpaRepository` con operaciones CRUD automáticas
- Métodos de consulta derivados por Spring Data JPA
- Consultas basadas en relaciones con entidades Cita y Medicamento

---

## 7. CLASE PRINCIPAL

### 7.1 ClinicaApplication.java

**Ubicación:** `src/main/java/com/uniminuto/clinica/ClinicaApplication.java`

#### Método: main()

```java
/**
 * Método principal de la aplicación Spring Boot
 * @param args Argumentos de línea de comandos
 */
public static void main(String[] args) {
    SpringApplication.run(ClinicaApplication.class, args);
}
```

**Observaciones para uso:**
- Clase principal que inicia la aplicación Spring Boot
- Anotada con `@SpringBootApplication` para configuración automática
- Punto de entrada estándar de aplicaciones Spring Boot

---

## 8. RECOMENDACIONES GENERALES DE USO

### Validaciones Importantes:
1. **Medicamentos**: Siempre validar nombres únicos antes de guardar/actualizar
2. **Recetas**: Verificar existencia de citas y medicamentos antes de crear
3. **Fechas**: Usar tipos `LocalDate` y `LocalDateTime` consistentemente
4. **Campos obligatorios**: Validar todos los campos requeridos en las operaciones de escritura

### Manejo de Errores:
- Todas las operaciones críticas lanzan `BadRequestException` con mensajes descriptivos  
- Usar `Optional` para manejo seguro de consultas que pueden no retornar resultados
- Retornar listas vacías en lugar de `null` para evitar `NullPointerException`

### Arquitectura:
- Separación clara entre capas: API → Service → Repository → Entity
- Uso de DTOs (`*Rq`) para recibir datos de entrada
- Respuestas estandarizadas con `RespuestaRs`
- Configuración CORS habilitada para desarrollo frontend

### Elementos Pendientes:
- Implementación completa de `listarRecetasPorMedicamento()` en `RecetaServiceImpl`
- Considerar agregar paginación en listados grandes
- Implementar logs para auditoría de operaciones
- Agregar validaciones de fechas de vencimiento de medicamentos

---

**Fecha de generación:** 21 de Septiembre, 2025  
**Rama analizada:** 961415_JonattanSanchez  
**Archivos documentados:** 15 archivos Java relacionados con clínica, receta y medicamento
