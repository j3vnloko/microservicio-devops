Microservicio DevOps — EP3: Observabilidad y Cumplimiento Normativo

Microservicio REST desarrollado en Spring Boot con Java 21, que gestiona Categorías y Productos con conexión a base de datos MySQL. Esta entrega extiende el pipeline CI/CD previo incorporando observabilidad, métricas, análisis de calidad y despliegue en Kubernetes.


Tecnologías utilizadas


Java 21 + Spring Boot 3.5.6
MySQL 8.0
Docker + Docker Compose
GitHub Actions (CI/CD — 3 etapas en cadena)
Prometheus + Grafana (monitoreo y métricas)
SonarCloud (análisis de calidad de código)
Snyk (análisis de seguridad de dependencias)
Kubernetes con Kind (despliegue orquestado)
JaCoCo (cobertura de pruebas)
JUnit 5 + Mockito (pruebas unitarias)



Estructura del proyecto

microservicio-devops/
├── .github/workflows/
│   └── ci.yml                        ← Pipeline CI/CD EP3 (3 etapas)
├── docs/                             ← Evidencias visuales
├── k8s/
│   └── deployment.yaml               ← Despliegue en Kubernetes
├── monitoring/
│   ├── prometheus.yml                ← Configuración de scraping
│   └── grafana/
│       └── provisioning/
│           └── datasources/
│               └── prometheus.yml    ← Fuente de datos Grafana
├── src/
├── docker-compose.yml                ← App + MySQL + Prometheus + Grafana
├── sonar-project.properties          ← Configuración SonarCloud
└── pom.xml                           ← Dependencias + JaCoCo + Sonar


Pipeline CI/CD — 3 Etapas en Cadena (IE6)

El pipeline se activa automáticamente en cada push a develop y en Pull Requests hacia main. Las etapas corren en secuencia: si una falla, las siguientes no se ejecutan.

Mostrar imagen

Etapa 1 — Build y Tests


Checkout del código
Configuración de Java 21 (Temurin)
Compilación y ejecución de pruebas con Maven
Generación de reporte de cobertura con JaCoCo
Subida del reporte como artefacto


Etapa 2 — Seguridad y Calidad


Análisis de calidad con SonarCloud
Escaneo de dependencias con Snyk (--severity-threshold=high)
Si SonarCloud detecta una falla crítica de calidad, el pipeline se detiene (IE6)


Etapa 3 — Deploy Kubernetes


Creación de clúster local con Kind
Build de imagen Docker multi-stage
Carga de la imagen al clúster Kind
Despliegue con kubectl apply
Verificación del rollout y estado de pods



IE1 — Monitoreo con Prometheus

Se agregó la dependencia micrometer-registry-prometheus al pom.xml y se habilitó el endpoint /actuator/prometheus en application.properties:

propertiesmanagement.endpoints.web.exposure.include=health,info,prometheus,metrics
management.endpoint.prometheus.enabled=true

Prometheus scrapea las métricas de la aplicación cada 15 segundos, configurado en monitoring/prometheus.yml:

yamlscrape_configs:
  - job_name: 'microservicio-devops'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['app:8080']

Las métricas disponibles incluyen uso de CPU, memoria de la JVM, tiempos de respuesta HTTP, número de requests y disponibilidad del servicio.


IE2 — Despliegue en Kubernetes (Kind)

El microservicio se despliega en un clúster Kubernetes simulado con Kind dentro de GitHub Actions. El archivo k8s/deployment.yaml define:


2 réplicas del microservicio
Health checks con /actuator/health (readiness y liveness probes)
Anotaciones Prometheus para scraping automático de métricas
Servicio de tipo ClusterIP


El despliegue se realiza de forma automatizada en cada ejecución del pipeline, garantizando trazabilidad completa desde el código hasta el entorno orquestado.


IE3 — Dashboard con Grafana

Grafana se levanta junto con Prometheus a través de docker-compose up. Acceso local en http://localhost:3000 (usuario: admin, contraseña: admin).

La fuente de datos Prometheus se configura automáticamente mediante provisioning en monitoring/grafana/provisioning/datasources/prometheus.yml.

Métricas clave disponibles en el dashboard:


Uso de CPU y memoria de la JVM
Tiempo de respuesta de los endpoints HTTP
Número de requests por segundo
Estado de salud del microservicio (UP / DOWN)
Tasa de errores HTTP (4xx y 5xx)



IE4 — Integración en el Pipeline CI/CD

Cada herramienta cumple un rol específico dentro del ciclo de vida del software:

HerramientaEtapaAporte a decisiones técnicasJaCoCoEtapa 1Mide cobertura de pruebas; permite detectar código sin testearSonarCloudEtapa 2Identifica bugs, vulnerabilidades y deuda técnica antes del deploySnykEtapa 2Detecta vulnerabilidades en dependencias de tercerosPrometheusRuntimeExpone métricas en tiempo real del microservicio en ejecuciónGrafanaRuntimeVisualiza tendencias de CPU, memoria y errores para toma de decisionesKind + kubectlEtapa 3Valida que la imagen Docker despliega correctamente en Kubernetes


IE5 — Políticas de Cumplimiento

SonarCloud

El análisis de calidad se ejecuta automáticamente en cada push a develop.

MétricaResultadoSecurity RatingAReliability RatingAMaintainability RatingA

Mostrar imagen

Mostrar imagen

Branch Protection — protect-main

Se configuró un Ruleset activo sobre la rama main con las siguientes reglas:


Requiere Pull Request antes de hacer merge
Requiere que los 3 status checks pasen (Etapa 1, Etapa 2, Etapa 3)
Bloquea force pushes
Restringe eliminación de la rama


Mostrar imagen


IE6 — Detención ante Fallas Críticas

El pipeline implementa los siguientes mecanismos de detención automática:


SonarCloud: si el Quality Gate falla, Maven retorna exit code 1 y detiene el pipeline antes del deploy.
Snyk con --severity-threshold=high: si detecta una vulnerabilidad alta o crítica en dependencias, bloquea la Etapa 2.
Cadena needs: cada etapa depende de la anterior, por lo que una falla en cualquier punto detiene todo el flujo.



Estrategia de Ramificación (GitFlow)

RamaDescripciónmainCódigo estable en producción (protegida por ruleset)developIntegración de nuevas funcionalidades (activa el pipeline)feature/<nombre>Desarrollo de nuevas característicashotfix/<nombre>Correcciones urgentes sobre main


Cómo ejecutar localmente

bash# Levantar la aplicación con monitoreo completo
docker compose up -d

# Accesos
# App:        http://localhost:8080
# Prometheus: http://localhost:9090
# Grafana:    http://localhost:3000  (admin/admin)
# Métricas:   http://localhost:8080/actuator/prometheus


Herramientas de IA utilizadas

Claude (Anthropic): Apoyo en la configuración del pipeline CI/CD, Dockerfile, archivos de Kubernetes, configuración de Prometheus/Grafana y estructura del README. Todas las decisiones técnicas fueron revisadas y validadas por el estudiante.

Todo uso de IA fue citado según las indicaciones del curso: https://bibliotecas.duoc.cl/ia


Conclusión personal

Durante el desarrollo de esta evaluación, lo que más me costó fue depurar los errores del pipeline en GitHub Actions. Cada corrección traía un nuevo error distinto, lo que me obligó a leer los logs con detención y entender qué estaba fallando en cada etapa. Ese proceso, aunque frustrante, fue donde más aprendí.

En cuanto a herramientas nuevas, SonarCloud me pareció muy útil porque permite ver la calidad del código de forma visual y objetiva, identificando vulnerabilidades o problemas que a simple vista no se notan. Entendí que integrar este tipo de análisis en el pipeline no es opcional en un entorno profesional real.

Lo que más valoro de esta evaluación es haber comprendido para qué sirve realmente un pipeline que se detiene ante fallas: no es solo una configuración técnica, sino una forma de proteger el entorno productivo de código defectuoso o inseguro. En el mundo laboral, automatizar estos controles permite que los equipos trabajen con mayor confianza y velocidad.


Integrante

Jean Carlos Andrés Flores Cifuentes

Repositorio: https://github.com/j3vnloko/microservicio-devops