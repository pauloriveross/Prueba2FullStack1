<div align="center">

# 🚘 Automotora

**Plataforma de microservicios para gestión automotriz**

![Java](https://img.shields.io/badge/Java-21-%23ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-%236DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-%236DB33F?logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9.9-%23C71A36?logo=apachemaven&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-Discovery-%23DB4437?logo=netflix&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-%23000000?logo=jsonwebtokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-%2385EA2D?logo=swagger&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-%232496ED?logo=docker&logoColor=white)

</div>

---

## 📖 Sobre el Proyecto

Sistema backend para Automotoras que busquen modernizar sus sistemas, construido con **13 microservicios** en **Spring Boot 4.0.6** y **Spring Cloud 2025.0.1**. Utiliza **Eureka** para descubrimiento de servicios, **Spring Cloud Gateway** como API Gateway, **JWT** para autenticación, y base de datos **MySQL** (cada microservicio con su propia BD).

Proyecto evaluativo número 3 para la asignatura de **FullStack 1** — Duoc UC.

---

## 📦 Microservicios

| # | Servicio | Puerto | BD | Descripción |
|---|----------|--------|----|-------------|
| 1 | **eureka-server** | `8869` | — | Service Registry (Netflix Eureka) |
| 2 | **gateway-service** | `8080` | — | API Gateway (Spring Cloud Gateway) |
| 3 | **auth-service** | `8086` | `db_auth_service` | Autenticación y JWT |
| 4 | **cliente** | `8082` | `db_cliente` | CRUD clientes (Flyway + DataFaker) |
| 5 | **vehiculo** | `8089` | `db_vehiculo` | CRUD vehículos |
| 6 | **vendedor** | `8081` | `db_vendedor` | CRUD vendedores |
| 7 | **mecanicos** | `8084` | `db_mecanicos` | CRUD mecánicos |
| 8 | **ventas-Update** | `8083` | `db_ventas` | Gestión de ventas |
| 9 | **mantenciones** | `8085` | `db_mantencion` | Gestión de mantenciones |
| 10 | **testDrive** | `8092` | `db_testDrive` | Gestión de test drives |
| 11 | **seguro** | `8090` | `db_seguro` | Seguros vehiculares |
| 12 | **corredorSeguro** | `8091` | `dv_corredor` | Corredores de seguros |
| 13 | **personalaseo** | `8088` | `db_personal` | Personal de limpieza |

---

## 🛠️ Requisitos

- **Java 21**
- **Maven 3.9+**
- **Laragon** (opcional, para scripts SQL MySQL)
- **Docker Desktop** (opcional)
- **Postman** (opcional)

---

## 🚀 Ejecución Local

### 1. Clonar e iniciar Eureka
**Los servicios se pueden iniciar de manera manual en el IDE o en terminal**

```bash
git clone <repo-url>
cd Prueba2FullStack1

mvn -pl eureka-server spring-boot:run
```

### 2. Iniciar Gateway

```bash
mvn -pl gateway-service spring-boot:run
```

### 3. Iniciar Auth

```bash
mvn -pl auth-service spring-boot:run
```

### 4. Iniciar servicios

```bash
mvn -pl cliente spring-boot:run
mvn -pl vehiculo spring-boot:run
mvn -pl vendedor spring-boot:run
mvn -pl mecanicos spring-boot:run
mvn -pl mantenciones spring-boot:run
mvn -pl ventas-Update spring-boot:run
mvn -pl seguro spring-boot:run
mvn -pl corredorSeguro spring-boot:run
mvn -pl testDrive spring-boot:run
mvn -pl personalaseo spring-boot:run
```

> **Tip:** Compila todo primero con `mvn clean package -DskipTests` y ejecuta los JARs.

### 5. Acceder

```
Gateway:      http://localhost:8080
Eureka:       http://localhost:8869
Swagger:      http://localhost:<puerto>/swagger-ui.html
```

---

## 🐳 Docker

```bash
# Con Eureka
docker-compose up --build

# Sin Eureka (desarrollo local)
docker-compose -f docker-compose.render-local.yml up --build
```

---

## ☁️ Render

```bash
# Build y deploy automático vía render.yaml
# 12 servicios en plan gratuito
# Dockerfiles en render/docker/
```

---

## 🔐 Autenticación

**Login:**

```bash
POST http://localhost:8080/auth/login
Content-Type: application/json

{ "username": "admin", "password": "admin1234" }
```

Respuesta:

```json
{ "token": "eyJ...", "username": "admin", "rol": "Admin" }
```

**Endpoints protegidos:** Ventas, Mantenciones, Seguros y TestDrive requieren `Authorization: Bearer <token>`.

**Usuarios por defecto:**

| Username | Password | Rol |
|---|---|---|
| `admin` | `admin1234` | Admin |
| `Joako` | `1234` | Admin |
| `Emanuel` | `1234` | Admin |
| `Paulo` | `1234` | Admin |
| `Benjamin` | `1234` | Admin |

---

## 🔀 Gateway

Todas las rutas pasan por `http://localhost:8080`:

| Ruta | Destino |
|---|---|
| `/auth/**` | Auth Service |
| `/api/v1/clientes/**` | Cliente |
| `/api/v1/vehiculos/**` | Vehículo |
| `/api/v1/vendedores/**` | Vendedor |
| `/api/v1/mecanicos/**` | Mecánicos |
| `/api/v1/ventas/**` | Ventas |
| `/api/v1/mantenciones/**` | Mantenciones |
| `/api/v1/seguros/**` | Seguros |
| `/api/v1/corredores/**` | Corredores |
| `/api/v1/testdrives/**` | Test Drive |
| `/api/v1/personalaseo/**` | Personal Aseo |

---

## 📁 Estructura

```
Prueba2FullStack1/
├── auth-service/           # JWT auth
├── cliente/                # CRUD + Flyway + DataFaker
├── corredorSeguro/         # CRUD corredores
├── eureka-server/          # Service registry
├── gateway-service/        # API Gateway
├── mantenciones/           # Gestión mantenciones
├── mecanicos/              # CRUD mecánicos
├── personalaseo/           # CRUD personal aseo
├── seguro/                 # Gestión seguros
├── testDrive/              # Gestión test drives
├── vehiculo/               # CRUD vehículos
├── vendedor/               # CRUD vendedores
├── ventas-Update/          # Gestión ventas
├── ScriptsSql/             # Scripts SQL (MySQL)
├── render/docker/          # Dockerfiles para Render
├── docker-compose.yml
├── docker-compose.render-local.yml
├── pom.xml
├── render.yaml
└── README.md
```

---

## 👥 Equipo

| Integrantes |
|---|
| Emanuel Barra |
| Joaquin Fuenzalida |
| Paulo Riveros |
| Benjamin Vargas |

---

<div align="center">

**Duoc UC — FullStack 1 — 2025**

</div>
