# Microservicio DevOps - Backend Spring Boot

## Descripción
Microservicio REST desarrollado en Spring Boot con Java 21, que gestiona 
Categorías y Productos con conexión a base de datos MySQL. Este repositorio 
implementa un pipeline CI/CD completo con GitHub Actions.

## Tecnologías
- Java 21 + Spring Boot
- MySQL 8.0
- Docker + Docker Compose
- GitHub Actions (CI/CD)
- Snyk (análisis de seguridad)
- Dependabot (actualización de dependencias)
- JUnit 5 + Mockito (pruebas unitarias)

## Pipeline CI/CD
El pipeline se ejecuta automáticamente en cada push a `develop` y en cada 
Pull Request hacia `main`. Tiene 3 etapas en cadena:

### Etapa 1 - Build y Tests
- Checkout del código
- Configuración de Java 21
- Compilación con Maven
- Ejecución de pruebas unitarias con JUnit 5 y Mockito

### Etapa 2 - Seguridad (depende de Etapa 1)
- Escaneo de dependencias con Snyk
- Si encuentra vulnerabilidades críticas, el pipeline se bloquea
- Complementado con Dependabot para actualizaciones semanales automáticas

### Etapa 3 - Deploy (depende de Etapa 2)
- Construcción de imagen Docker con multi-stage build
- Despliegue automático con Docker Compose

## Trazabilidad
Cada commit en `develop` dispara el pipeline completo. GitHub Actions registra 
cada etapa con logs detallados, garantizando visibilidad total desde el 
desarrollo hasta el despliegue.

## Orquestación de Contenedores
Docker Compose orquesta dos servicios:
- `app`: el microservicio Spring Boot
- `db`: base de datos MySQL 8.0 con healthcheck

## Evidencia Docker Compose

### Contenedores iniciando
![Docker Compose inicio](evidencias%20parcial%202/1.jpg)

### Conexión a MySQL exitosa
![HikariPool conectado](evidencias%20parcial%202/2.jpg)

### Aplicación corriendo
![App iniciada](evidencias%20parcial%202/3.jpg)

## Estrategia de Ramificación (GitFlow)
| Rama | Descripción |
|------|-------------|
| `main` | Código estable en producción |
| `develop` | Integración de nuevas funcionalidades |
| `feature/<nombre>` | Desarrollo de nuevas características |
| `hotfix/<nombre>` | Correcciones urgentes sobre main |

## Herramientas de IA utilizadas
- **Claude (Anthropic):** Apoyo en la estructura del README, configuración del 
pipeline CI/CD, Dockerfile y docker-compose. Todas las decisiones técnicas 
fueron revisadas y validadas por el equipo.

## Integrante
Jean Carlos Andrés Flores Cifuentes

## Conclusión final personal
Mi conclusión final es que aprendí a manejar mejor las ramas, me familiaricé 
con git push y git commit, y aprendí más sobre GitHub Actions para automatizar 
la integración continua. Con esta evaluación además incorporé contenedores con 
Docker, pruebas unitarias con JUnit y análisis de seguridad con Snyk.
