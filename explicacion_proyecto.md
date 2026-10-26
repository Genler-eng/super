# API REST - Sistema de Gestión de Supermercado 🛒

Backend desarrollado en **Spring Boot** y **PostgreSQL** para la gestión integral de un supermercado. El proyecto implementa una arquitectura limpia basada en capas, uso de DTOs, validaciones robustas, control transaccional de inventario y un manejo global de excepciones.

---

## 1. Descripción General
Un supermercado de barrio necesita un sistema backend que le permita administrar sus productos, categorías, proveedores, clientes y ventas. Este proyecto desarrolla una API REST con Spring Boot que expone operaciones CRUD para cada entidad y aplica reglas de negocio sencillas, como el control de stock y el cálculo del total de una venta. El sistema no incluye autenticación ni manejo de usuarios: todos los endpoints son de acceso libre. Las pruebas se realizan exclusivamente con Postman.

## 2. Objetivos de Aprendizaje
* Crear un proyecto Spring Boot con arquitectura por capas (`Controller`, `Service`, `Repository`, `Entity`).
* Mapear entidades con JPA/Hibernate y definir relaciones entre ellas.
* Implementar operaciones CRUD completas con Spring Data JPA.
* Aplicar lógica de negocio básica en la capa de servicio.
* Manejar errores y validaciones devolviendo códigos HTTP adecuados.
* Probar la API con una colección de Postman.

## 3. Requisitos Técnicos
| Aspecto | Detalle |
| :--- | :--- |
| **Lenguaje / Versión** | Java 17 o superior |
| **Framework** | Spring Boot 3.x |
| **Dependencias** | Spring Web, Spring Data JPA, Validation, Lombok, Driver de PostgreSQL |
| **Base de datos** | PostgreSQL |
| **Gestor de dependencias** | Maven |
| **Pruebas** | Postman (colección exportada en formato JSON) |
| **Seguridad** | Sin autenticación ni autorización (acceso libre) |

---

## 🏛️ Arquitectura del Proyecto
El sistema está construido bajo el patrón de diseño **Controller - Service - Repository**:
* **Capa Controller**: Expone los endpoints REST y gestiona las peticiones HTTP (`@RestController`).
* **Capa Service**: Concentra la lógica de negocio, validaciones de integridad y control transaccional (`@Transactional`).
* **Capa Repository**: Interfaces de Spring Data JPA encargadas de la comunicación directa con PostgreSQL y consultas derivadas avanzadas.

## 📦 Entidades y Estructura de Datos
1. **Categoría**: Agrupa los productos por departamento comercial (`id`, `nombre`, `descripcion`).
2. **Proveedor**: Administra los distribuidores de mercancía (`id`, `nombre`, `telefono`, `correo`).
3. **Cliente**: Gestiona los compradores registrados (`id`, `nombre`, `documento`, `telefono`).
4. **Producto**: Artículos disponibles para la venta (`id`, `nombre`, `codigoBarras`, `precio` con alta precisión mediante `BigDecimal`, `stock`, y relaciones con Categoría y Proveedor).
5. **Venta y DetalleVenta**: Registro transaccional que procesa compras, valida existencias, calcula subtotales y descuenta automáticamente el stock del inventario.

## 🔌 Endpoints Principales de la API

### 📂 Categorías (`/api/categorias`)
* `GET /api/categorias`: Obtener lista de todas las categorías
* `GET /api/categorias/{id}`: Buscar categoría por ID
* `GET /api/categorias/nombres`: Listado proyectado de nombres de categorías
* `POST /api/categorias`: Registrar una nueva categoría
* `PUT /api/categorias/{id}`: Actualizar una categoría existente
* `DELETE /api/categorias/{id}`: Eliminar una categoría

### 🚚 Proveedores (`/api/proveedores`)
* `GET /api/proveedores`: Listar todos los proveedores
* `GET /api/proveedores/{id}`: Buscar proveedor por ID
* `GET /api/proveedores/buscar?criterio=...`: Búsqueda flexible por coincidencia en nombre o teléfono
* `POST /api/proveedores`: Crear un proveedor
* `PUT /api/proveedores/{id}`: Actualizar proveedor
* `DELETE /api/proveedores/{id}`: Eliminar proveedor

### 🛍️ Productos (`/api/productos`)
* `GET /api/productos`: Listar inventario de productos
* `GET /api/productos/{id}`: Buscar producto por ID
* `GET /api/productos/buscar?nombre=...`: Buscar productos por nombre
* `GET /api/productos/stock-bajo?limite=...`: Filtrar productos con stock crítico
* `POST /api/productos`: Registrar producto con validación de relaciones
* `PUT /api/productos/{id}`: Actualizar producto
* `DELETE /api/productos/{id}`: Eliminar producto

### 👤 Clientes (`/api/clientes`)
* `GET /api/clientes`: Listar clientes
* `GET /api/clientes/buscar?nombre=...`: Buscar clientes por nombre
* `POST /api/clientes`: Registrar cliente

### 💰 Ventas (`/api/ventas`)
* `GET /api/ventas`: Obtener historial completo de ventas
* `GET /api/ventas/{id}`: Consultar detalle de una venta específica por ID
* `POST /api/ventas`: Registrar una nueva venta (procesa DTOs, valida stock, descuenta inventario y calcula totales de forma transaccional)

## 🛡️ Manejo Global de Excepciones
El sistema implementa un controlador de avisos global (`GlobalExceptionHandler`) para estandarizar las respuestas de error en formato JSON:
* **`ResourceNotFoundException` (`404 NOT FOUND`)**: Se dispara automáticamente cuando un recurso buscado por ID no existe en la base de datos.
* **`IllegalArgumentException` (`400 BAD REQUEST`)**: Atrapa reglas de negocio, como nombres vacíos o intentos de venta con stock insuficiente.
* **`MethodArgumentNotValidException` (`400 BAD REQUEST`)**: Maneja los fallos de validación de campos anotados con `@Valid` en las peticiones.

## ⚙️ Instrucciones de Configuración y Ejecución

Sigue estos pasos para poner en marcha el proyecto en tu entorno local:

### 1. Prerrequisitos
Asegúrate de tener instalado en tu equipo:
* **Java JDK 17** o superior.
* **Maven** (o utilizar el wrapper incluido `mvnw`).
* **PostgreSQL** y **pgAdmin 4** (o cualquier cliente de bases de datos).

### 2. Configurar la Base de Datos
1. Abre pgAdmin o tu cliente de PostgreSQL preferido y crea una nueva base de datos (por ejemplo, llamada `supermercado_db`).
2. Dirígete a la ruta del proyecto: `src/main/resources/application.properties`.
3. Configura tus credenciales de conexión a PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/supermercado_db
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### 3. Compilar y Ejecutar el Proyecto
1. Abre una terminal en la raíz del proyecto (donde se encuentra el archivo `pom.xml`).
2. Ejecuta el siguiente comando para compilar las dependencias y empaquetar la aplicación con Maven:
   ```bash
   mvn clean install
   ```
3. Ejecuta la aplicación con Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
   *(También puedes abrir el proyecto directamente en tu IDE favorito como IntelliJ IDEA o Eclipse y ejecutar la clase principal `MercadoApplication.java`).*

### 4. Probar la API
* La aplicación se iniciará por defecto en el puerto `8080` (`http://localhost:8080`).
* Importa la colección de **Postman** exportada en formato JSON (ubicada en los archivos del proyecto) para probar todos los endpoints CRUD, las consultas de búsqueda y el registro transaccional de ventas.