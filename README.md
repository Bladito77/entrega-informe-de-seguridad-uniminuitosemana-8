"# Vollmed API - Verificaci¢n de Seguridad" 
Entrega – Proyecto Integrador (Seguridad de Software)

Vollmed API – Spring Boot / Maven / MySQL / SonarQube

Este repositorio contiene la implementación, pruebas y análisis de seguridad de una API REST (Vollmed) desarrollada con Spring Boot 3, siguiendo la plantilla y la rúbrica del curso.
Se documenta el proceso de verificación (SAST, DAST, SCA/IAST), resultados obtenidos con SonarQube, y evidencias para la entrega.

1) Objetivo del proyecto

Construir y probar una API de gestión de turnos/consultas médicas (Vollmed).

Aplicar un proceso de desarrollo de código seguro:

Políticas básicas de codificación segura (validaciones, DTOs, JWT).

Pruebas automáticas (JUnit + Spring Boot Test).

Análisis estático (SAST) con SonarQube.

Análisis de composición (SCA) de dependencias.

Evidenciar el flujo de trabajo reproducible (Docker + Maven).

2) Stack y requisitos

Java 17+ (el proyecto compila con JDK 17; en nuestra sesión se usó Java 24 y también funcionó).

Maven 3.9+ (mvn/mvnw)

MySQL 5.7/8.0 (con XAMPP o Docker)

Docker Desktop (para SonarQube)

Git

3) Estructura principal
api/
├─ src/
│  ├─ main/
│  │  ├─ java/med/voll/api/...
│  │  └─ resources/
│  │     ├─ application.properties        # perfil dev
│  │     └─ db/migration/V*.sql           # migraciones Flyway
│  └─ test/
│     └─ java/med/voll/api/...
├─ pom.xml
└─ docs/
   ├─ informe/
   │  └─ Informe_Proyecto_Seguridad.docx  # (colocar aquí el informe)
   └─ evidencias/
      ├─ sonar-dashboard.png
      ├─ pruebas-pantallazos.png
      └─ ...


Nota: coloca tu informe final y pantallazos dentro de docs/ (ya está referenciado en el informe).

4) Configuración de base de datos

Editar src/main/resources/application.properties con tus credenciales locales:

spring.application.name=api
spring.datasource.url=jdbc:mysql://localhost/vollmed_app
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.error.include-stacktrace=never

# JWT (ejemplo de secreto):
api.security.token.secret=${JWT_SECRET:ALope2024}


La BD y las tablas se crean/migran automáticamente con Flyway al iniciar las pruebas o la app (scripts en db/migration).

5) Cómo ejecutar
5.1. Compilar y ejecutar pruebas
# desde la carpeta api/
mvnw clean test

5.2. Ejecutar la aplicación (perfil por defecto)
mvnw spring-boot:run


La API quedará disponible (por defecto) en: http://localhost:8080/

Endpoints de ejemplo (según el código base de Alura):

POST /login (JWT)

POST /medicos, GET /medicos

POST /consultas (agenda/cancelación)

Revisa los controllers en med.voll.api.controller.*.

6) Análisis de seguridad con SonarQube (SAST/SCA)
6.1. Levantar SonarQube en Docker
docker run -d --name sonarqube -p 9000:9000 sonarqube:community


Ingresa a http://localhost:9000
, crea el proyecto local y un token (lo guardas temporalmente).

6.2. Ejecutar el análisis con Maven

👉 Reemplaza TU_TOKEN por tu token y Vollmed si usaste otro key/nombre.

mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=Vollmed \
  -Dsonar.projectName=Vollmed-api \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=TU_TOKEN

<img width="1864" height="869" alt="image" src="https://github.com/user-attachments/assets/951e9b69-e44f-4fd5-8de7-dbef64ff5b89" />

El build corre pruebas y genera artefactos.

SonarQube recibe el reporte y calcula:

SAST (bugs, code smells, vulnerabilidades)

SCA (dependencias/problemas conocidos)

Coverage (si configuras JaCoCo)

Revisa el dashboard del proyecto en SonarQube.

En nuestra ejecución final obtuvimos BUILD SUCCESS y el análisis quedó disponible en el panel. Guarda pantallas en docs/evidencias/.

7) Mapeo con la rúbrica (¿Qué se probó y por qué?)

SAST (Análisis Estático): SonarQube analiza el código fuente sin ejecutarlo. Detecta patrones de riesgo (SQL Injection, NPEs, malas prácticas, exposición de secretos, etc.).

SCA (Software Composition Analysis): SonarQube/Maven inspeccionan las dependencias del pom.xml (versiones, CVEs y licencias).

Pruebas de integración/funcionales: mvnw test ejecuta tests en src/test. Se valida lógica de negocio (agenda/cancelación) y comportamiento de endpoints.

IAST/DAST (enunciado): Este repo concentra principalmente SAST/SCA y pruebas unitarias/integración. DAST/IAST pueden simularse con Postman/Newman o Zaproxy aparte; en la entrega se documenta cómo se integrarían en CI.

8) Evidencias para la entrega

Guarda en docs/evidencias/:

Pantallas del dashboard de SonarQube (overview, issues, security hotspots, quality gate).

Salida de consola con BUILD SUCCESS y envío exitoso a Sonar.

Capturas de pruebas (JUnit verde).

Opcional: Capturas de endpoints probados (Postman).

Estos archivos se referencian desde el informe .docx.

9) Flujo Git (para nuevas entregas)
# Ver estado y cambios
git status

# Añadir cambios
git add .

# Commit semántico
git commit -m "docs: agregar informe y evidencias de seguridad"

# Enviar a GitHub
git push

10) Preguntas orientadoras (resumen corto)

¿Cómo implementar pruebas de seguridad en el desarrollo?
Integrando SAST/SCA en el pipeline (SonarQube + Maven), tests unitarios/integración desde el inicio, validaciones y autenticación/JWT.

¿Por qué usar herramientas automatizadas?
Aumentan cobertura, consistencia, reducen tiempos y evitan errores humanos. Permiten feedback temprano y mejores métricas (Quality Gate).

La exposición ampliada está en el informe (docs/informe/Informe_Proyecto_Seguridad.docx).

11) Resultados (resumen)

Compilación y pruebas: OK (BUILD SUCCESS).

SonarQube:

Proyecto analizado correctamente (SAST/SCA).

Informe disponible en el dashboard local.

Código y evidencias versionadas en GitHub.

12) Licencia / Autores

Uso académico – UNIMINUTO – Proyecto integrador de Seguridad de Software.

Autoría: Bladimir (Bladito77)

Base educativa: material del curso (Alura + rúbrica del docente).

13) Apéndice – Troubleshooting

“Plugin not found in any repository”: verifica conexión a internet y repo.maven.apache.org en settings.xml.

MySQL 5.5 warning en logs de Hibernate: si puedes, usa MySQL 8 para eliminar el aviso (no bloquea el build).

SonarQube no arranca: confirma Docker corriendo/WSL habilitado.

Credenciales/puertos: revisa application.properties.

¿Dudas o mejoras?

Abre un Issue o un Pull Request en este repo. ¡Gracias! 🙌

Edwin Bladimir Torres
edwintorresapp@gmail.com
