# App Cursos Springboot Backend

### Requerimientos Previos :fa-cog:

- Lenguaje Java 17
- [Netbeans IDE 14](https://netbeans.apache.org/download/index.html)
- [Docker 20.10.23](https://www.docker.com/products/docker-desktop/)
- [Git 2.36.1](https://git-scm.com/downloads/)
- [Springboot 2.6.2](https://plugins.netbeans.apache.org/catalogue/?id=4)
- [MariaDB](https://mariadb.org/download/)
- PostgreSQL 
- [PgAdmin 4](https://www.pgadmin.org/download/)
- [DBeaver Community 22.3.5](https://dbeaver.io/download/)
- [Postman](https://www.postman.com/downloads/)
- Docker-Compose 2.15.1
- [Visual Studio Code 1.75.1](https://code.visualstudio.com/download)

------------

### Introducción al Proyecto :fa-globe:

El presente proyecto de desarrollo, se enfoca en gestionar los cursos proporcionados por una institución educativa a los diferentes alumnos inscritos a dichos cursos. 

Los alumnos inscritos en los cursos, deveran responder mediante un proceso de preguntas-respuestas una serie de examenes que pertenecen a las diferentes asignaturas o materias.

------------

### Arquitectura :fa-cubes:

La arquitectura propuesta para el proyecto de desarrollo engloba un conjunto de microservicios construidos en el framework backend de Sringboot versión 2.6.14 con el lenguaje de programación de Java.

La arquitectura del proyecto se divide en dos secciones principales, una de ellas llamada **infraestructura** y la segunda denominada **dominio del negocio** La primera de ellas engloba diferentes servicios proporcionados por el mismo entorno de trabajo, los cuales se describen a continuación:

- **Eureka Server**: Es un servidor de nombres que permite registrar los microservicios que son clientes de Eureka y que están registrados a dicho servicio. El servidor de Eureka gestiona los microservicios mediante su identificador, ademas de almacenar información adicional del servicio como la IPv4, dominio, número de puerto y los metadatos propios del servicio. Actualmente Spring permite hacer uso de este servicio mediante la tecnologia Spring Cloud Eureka.
- **Admin Server**: Es un servicio proporcionado por Spring el cual se encarga de administrar y monitorear la ejecución de manera concurrente de los microservicios que forman parte de un proyecto de desarrollo. De esta forma, el servicio de administración mediante reportes graficos muestra información sobre las metricas de ejecución para que el administrador tenga en conocimiento la ejecución de cada uno de los servicios que componen el sistema.
- **Gateway API Server**: Es un servidor de enrutamiento dinámico, que para fines practicos permite el acceso a un recurso determinado de los microservicios, ademas de manejar las diferentes exepciones de error administración del balanceo de cargas, modificar las cabeceras de las solicitudes HTTP, interceptar el acceso a una ruta o servicio particular, entre otros. Actualmente Spring permite hacer uso de este servicio mediante las tecnologias Zuul Netflix y Spring Cloud Gateway.

La segunda sección engloba los diferentes servicios del proyecto que definen el modelo de negocio, los cuales se describen a continuación:

- **Microservicio alumnos**: Este servicio se enfoca en el almacenamiento de la información de los alumnos inscritos a los diferentes cursos expedidos por la institución educativa. Cabe recalcar que el SGBD es PostgreSQL.
- **Microservicio cursos**: Servicio enfocado en almacenar información acerca de los distintos cursos que ofrece la institución educativa. Es importante señalar que en los cursos pueden estar inscritos varios alumnos y de igual forma se pueden aplicar varios examenes. Cabe recalcar que el SGBD es MariaDB.
- **Microservicio examenes**: Este servicio gestiona la información de los examenes aplicados a los alumnos inscritos en los diferentes cursos. Los examenes pertenecen a una asignatura o materia especifica. Cabe recalcar que el SGBD es MariaDB.
- **Microservicio respuestas**: Por utlimo, este microservicio se encarga de almacenar la información de las respuestas dadas por los alumnos durante la aplicación de los examenes en base a las preguntas de cada prueba de conocimientos. Cabe recalcar que el SGBD es PostgreSQL.

A continuación se muestra una imagen con una arquitectura similar al presente proyecto de desarrollo, en donde se cuenta con un unico acceso mediante un Spring Gateway, Spring Security y los microservicios propios de la lógica del negocio.

![](https://raw.githubusercontent.com/CarlosOteroGitHub/SpringBoot_Java_CoursesProject/master/doc/images/8.%20Arquitectura%20de%20Acceso%20y%20Seguridad.PNG)

------------

### Modelo de Datos :fa-database:

En esta sección, se pretende proporcionar el contexto del modelo de base de datos relacional aplicado de manera general al sistema de información, para de esta manera, entender cada uno de los datos almacenados por las entidades y las relaciones que comparten cada una de los tablas. 

Es importante mencionar que la arquitectura del proyecto de desarrollo esta basado en microservicios, ___por lo que no todas las entidades estan dentro de un mismo repositorio___. 

![](https://raw.githubusercontent.com/CarlosOteroGitHub/SpringBoot_Java_CoursesProject/master/doc/modelo%20BD/data_base.png)

------------

### Dependencias :fa-align-center:

El backend de este proyecto de desarrollo como se menciono anteriormente, esta construido con la tecnología de Springboot del cual se consideraron varias dependencias genericas para la correcta construcción de cada uno de los microservicios, las cuales se enlistan en el siguiente apartado:

+ Springboot Devtools.
+ Springboot Starter.
	+ Web.
	+ Actuator.
	+ Validation.
	+ Json.
	+ Thymeleaf.
	+ Security.
	+ Data JPA.
	+ Webflux.
+ Spring Cloud Starter. 
	+ Bootstrap.
	+ Netflix-eureka-client.
	+ Netflix-eureka-server.
	+ Loadbalancer.
	+ Config.
+ Springboot-admin-server.
+ Loombok.
+ Springdoc Openapi.
+ IO Swagger.
+ Postgresql.
+ Mysql-connector-java.
+ JUnit.
+ Mockito.

------------

### Ejecución del Proyecto :fa-play: