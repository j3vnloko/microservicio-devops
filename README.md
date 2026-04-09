# Microservicio DevOps - Backend Spring Boot

## Descripción
Microservicio REST desarrollado en Spring Boot con Java 21, que gestiona 
Categorías y Productos con conexión a base de datos MySQL. Este repositorio 
forma parte del pipeline DevOps construido en la asignatura Ingeniería DevOps (DOY0101).

## Estrategia de Ramificación
Se utilizó **GitFlow** como estrategia de ramificación. Esta decisión se tomó 
porque el proyecto tiene ciclos de desarrollo definidos, donde se requiere 
mantener una rama estable de producción (main) separada del desarrollo continuo 
(develop). GitFlow permite trabajar features de forma aislada, corregir errores 
urgentes mediante hotfixes sin interrumpir el flujo de desarrollo, y mantener 
una trazabilidad clara de todos los cambios realizados en el código.

## Estructura de Ramas
| Rama | Descripción |
|------|-------------|
| `main` | Código estable en producción |
| `develop` | Integración de nuevas funcionalidades |
| `feature/<nombre>` | Desarrollo de nuevas características |
| `hotfix/<nombre>` | Correcciones urgentes sobre main |

## Convenciones de Commits
Se utiliza el estándar **Conventional Commits**:

| Prefijo | Uso |
|---------|-----|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de error |
| `docs` | Cambios en documentación |
| `ci` | Cambios en pipeline CI/CD |
| `refactor` | Mejora de código sin cambio funcional |
| `chore` | Tareas de mantenimiento |

Ejemplos:
- `feat: agrega endpoint para listar productos`
- `fix: corrige parámetros de conexión a base de datos`
- `ci: agrega workflow de GitHub Actions`

## Flujo de Merge
1. Crear rama desde `develop` (o desde `main` en caso de hotfix)
2. Desarrollar el cambio y hacer commits con mensajes descriptivos
3. Hacer push de la rama al repositorio remoto
4. Abrir Pull Request hacia la rama destino
5. Revisión del código por parte del compañero de equipo
6. Merge tras aprobación

## Naming de Ramas
- Features: `feature/nombre-descriptivo-en-kebab-case`
- Hotfixes: `hotfix/descripcion-corta-del-error`
- Ejemplos:
  - `feature/agregar-endpoint-producto`
  - `feature/validacion-categoria`
  - `hotfix/corregir-conexion-bd`

## GitHub Actions - CI/CD
Se configuró un pipeline de integración continua con **GitHub Actions** que 
se ejecuta automáticamente en cada push a `develop` y en cada Pull Request 
hacia `main`. El pipeline realiza los siguientes pasos:

1. Checkout del código fuente
2. Configuración del entorno Java 21
3. Compilación del proyecto con Maven

Esto garantiza que cada cambio integrado compile correctamente antes de llegar 
a producción, cumpliendo con los estándares básicos de CI/CD.

## Pull Requests realizados
| PR | Tipo | Descripción | Rama origen | Rama destino |
|----|------|-------------|-------------|--------------|
| #1 | feature | Agrega endpoint para listar productos | feature/agregar-endpoint-producto | develop |
| #2 | feature | Agrega validación en entidad Categoria | feature/validacion-categoria | develop |
| #3 | hotfix | Corrige parámetros de conexión a base de datos | hotfix/corregir-conexion-bd | main |

## Herramientas de IA utilizadas
- **Claude (Anthropic):** Apoyo en la estructura del README, configuración del 
workflow de GitHub Actions y orientación general sobre buenas prácticas de GitFlow.
Todo el código, decisiones técnicas y justificaciones fueron revisadas y 
validadas por el equipo.

## Integrante 
Jean Carlos Andrés Flores Cifuentes
