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

Los alumnos inscritos en los cursos, deberán responder mediante un proceso de preguntas-respuestas una serie de exámenes que pertenecen a las diferentes asignaturas o materias.

------------

### Arquitectura :fa-cubes:

La arquitectura propuesta para el proyecto de desarrollo engloba un conjunto de microservicios construidos en el framework backend de Sringboot versión 2.6.14 con el lenguaje de programación de Java.

La arquitectura del proyecto se divide en dos secciones principales, una de ellas llamada **infraestructura** y la segunda denominada **dominio del negocio** La primera de ellas engloba diferentes servicios proporcionados por el mismo entorno de trabajo, los cuales se describen a continuación:

- **Eureka Server**: Es un servidor de nombres que permite registrar los microservicios que son clientes de Eureka y que están registrados a dicho servicio. El servidor de Eureka gestiona los microservicios mediante su identificador, además de almacenar información adicional del servicio como la IPv4, dominio, número de puerto y los metadatos propios del servicio. Actualmente Spring permite hacer uso de este servicio mediante la tecnología Spring Cloud Eureka.
- **Admin Server**: Es un servicio proporcionado por Spring el cual se encarga de administrar y monitorear la ejecución de manera concurrente de los microservicios que forman parte de un proyecto de desarrollo. De esta forma, el servicio de administración mediante reportes gráficos muestra información sobre las métricas de ejecución para que el administrador tenga en conocimiento la ejecución de cada uno de los servicios que componen el sistema.
- **Gateway API Server**: Es un servidor de enrutamiento dinámico, que para fines prácticos permite el acceso a un recurso determinado de los microservicios, además de manejar las diferentes excepciones de error administración del balanceo de cargas, modificar las cabeceras de las solicitudes HTTP, interceptar el acceso a una ruta o servicio particular, entre otros. Actualmente Spring permite hacer uso de este servicio mediante las tecnologías Zuul Netflix y Spring Cloud Gateway.
- **Keycloack Server**: Es un software de código abierto que proporciona servicios de autenticación y autorización para aplicaciones y servicios web. Se utiliza como una solución de gestión de identidades y accesos, permitiendo a los usuarios autenticarse a través de múltiples fuentes, como proveedores de identidades externos (por ejemplo, Google, Facebook, Twitter), bases de datos de usuarios internos, sistemas de autenticación de dos factores, entre otros. Además de que proporciona características de gestión de sesiones y autorización basada en roles y permisos.

La segunda sección engloba los diferentes servicios del proyecto que definen el modelo de negocio, los cuales se describen a continuación:

- **Microservicio alumnos**: Este servicio se enfoca en el almacenamiento de la información de los alumnos inscritos a los diferentes cursos expedidos por la institución educativa. Cabe recalcar que el SGBD es PostgreSQL.
- **Microservicio cursos**: Servicio enfocado en almacenar información acerca de los distintos cursos que ofrece la institución educativa. Es importante señalar que en los cursos pueden estar inscritos varios alumnos y de igual forma se pueden aplicar varios exámenes. Cabe recalcar que el SGBD es MariaDB.
- **Microservicio exámenes**: Este servicio gestiona la información de los exámenes aplicados a los alumnos inscritos en los diferentes cursos. Los exámenes pertenecen a una asignatura o materia especifica. Cabe recalcar que el SGBD es MariaDB.
- **Microservicio respuestas**: Por último, este microservicio se encarga de almacenar la información de las respuestas dadas por los alumnos durante la aplicación de los exámenes con base en las preguntas de cada prueba de conocimientos. Cabe recalcar que el SGBD es PostgreSQL.

A continuación se muestra una imagen con una arquitectura similar al presente proyecto de desarrollo, en donde se cuenta con un único acceso mediante un Spring Gateway, Spring Security y los microservicios propios de la lógica del negocio.

![](https://raw.githubusercontent.com/CarlosOteroGitHub/SpringBoot_Java_CoursesProject/master/doc/images/8.%20Arquitectura%20de%20Acceso%20y%20Seguridad.PNG)

------------

### Modelo de Datos :fa-database:

En esta sección, se pretende proporcionar el contexto del modelo de base de datos relacional aplicado de manera general al sistema de información, para de esta manera, entender cada uno de los datos almacenados por las entidades y las relaciones que comparten cada una de los tablas. 

Es importante mencionar que la arquitectura del proyecto de desarrollo está basado en microservicios, ___por lo que no todas las entidades están dentro de un mismo repositorio___. 

![](https://raw.githubusercontent.com/CarlosOteroGitHub/SpringBoot_Java_CoursesProject/master/doc/modelo%20BD/data_base.png)

------------

### Dependencias :fa-align-center:

El backend de este proyecto de desarrollo como se mencionó anteriormente, está construido con la tecnología de Springboot del cual se consideraron varias dependencias genéricas para la correcta construcción de cada uno de los microservicios, las cuales se enlistan en el siguiente apartado:

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

### Estructura de los Microservicios.

#### Métodos auxiliares.

Son aquellas plantillas que definen funcionalidades que sirven como apoyo para el correcto desarrollo de los módulos dentro del proyecto. De esta manera, se pueden definir procedimientos para levantar excepciones de error HTTP sobre los recursos (400, 404, 500, etc) o de igual forma validaciones al momento de exponer los datos en la respuesta de los recursos o endpoints por ejemplo.

#### Modelos.

Los plantillas o clases definen la estructura de los modelos, como por ejemplo el nombre, atributos y relaciones con otras entidades o tablas a nivel de base de datos. Springboot utiliza la librería **Hibernate**  como ORM para poder mapear los modelos y poder crearlos directamente en los respectivos SGBD con los índices establecidos por el desarrollador. 

A continuación se muestra un ejemplo de un modelo, en donde se define el nombre de la entidad y los campos que lo componen.

```
@Entity
@Table(name = "asignaturas")
public class AsignaturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    private String nombre;
```

#### Repositorios.

Es el componente encargado de permitir el acceso a los datos almacenados por los modelos que integran los servicios, de esta manera si se procesa una solicitud HTTP en donde se requiera consultar, guardar, modificar o eliminar información, este componente tendrá la obligación de realizar los cambios directamente sobre los registros.

A continuación se muestra el ejemplo de un repositorio, en donde se define la referencia del modelo a procesar y el acceso a los recursos de manera individual con un tipo de dato Long.

```
import com.backend.exams.models.AsignaturaModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AsignaturaRepository extends PagingAndSortingRepository<AsignaturaModel, Long>{

}
```

#### Servicios.

Es el componente encargado de definir la lógica de negocio dentro de un servicio determinado, esto se logra incorporando funcionalidades mediante código en los métodos que proporciona el servicio.  Es importante mencionar que un servicio debe tener la referencia hacia un repositorio mediante una inyección de dependencias, para el procesamiento de los datos gestionados por este último. En el framework de Springboot se hace uso de la anotación @Service para definir que una plantilla funge como servicio dentro del componente.

A continuación se muestra el ejemplo de un servicio, en donde se pueden observar distintos métodos que contienen la funcionalidad propia de la lógica de negocio.

```
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User change(User user) {
        return userRepository.save(user);
    }
}
```

#### Controladores.

Los controladores contienen toda aquella lógica aplicada sobre los endpoints o recursos que se encuentran definidos en los componentes del proyecto de desarrollo. De esta manera, los controladores o recursos definen los puntos de conexión entre el backend y el frontent, esto mediante una URL única definida por servicio. 

Para validar y verificar que los resultados obtenidos mediante estos recursos sean correctos, se emplea la herramienta de testint API **Postman**, la cual permite consumir las API Rest y arrojar la respuesta de los datos en formato JSON, seleccionando uno de los métodos de petición HTTP disponibles que se enlistan a continuación:

- **GET:** Es un método de solicitud HTTP empleado para consultar datos en forma de listado o detalle.
- **POST:** Es un método de solicitud HTTP que incluye datos  en el cuerpo del mensaje de la petición, muy probablemente para almacenarlos.
- **PUT:** Es un método de solicitud HTTP que incluye datos  en el cuerpo del mensaje de la petición, muy probablemente para actualizar completamente un documento. Regularmente esta solicitud incluye un parámetro que define el identificador del recurso a modificar.
- **PATCH:** Es un método de solicitud HTTP que incluye datos  en el cuerpo del mensaje de la petición, muy probablemente para actualizar parcialmente un documento a diferencia de la solicitud HTTP PUT. Regularmente esta solicitud incluye un parámetro que define el identificador del recurso a modificar.
- **DELETE:** Es un método de solicitud HTTP que permite eliminar datos de un recurso específico. Regularmente esta solicitud incluye un parámetro que define el identificador del recurso a eliminar.

#### Pruebas unitarias.

También denominados test unitarios, son procedimientos que intentan comprobar el normal y correcto funcionamiento de los recursos o endpoints implementados por un servicio en particular.  En otras palabras, las pruebas unitarias intentan proporcionar una retroalimentación casi instantánea sobre el diseño y la implementación del código fuente.

El framework de Springboot provee dos librerías o bibliotecas útiles para procesar pruebas unitarias sobre los recursos que son **JUnit** y **Mockito**.

------------

### Ejecución del Proyecto :fa-play:

#### Paso 1 - Descarga del repositorio del proyecto con Git.

Primeramente antes de la ejecución del proyecto, deberá clonar el repositorio de origen, para ello puede emplear la herramienta **Git**, la cual es útil para el control de versiones de software. Para iniciar una instancia de Git en su equipo habrá la consola de comandos de Git e ingresé el siguiente comando.

```
git init
```

Posteriormente ingresé el siguiente comando para descargar el repositorio del presente proyecto de desarrollo directamente sobre su equipo.

```
git clone https://github.com/CarlosOteroGitHub/SpringBoot_Java_CoursesProject.git
```

#### Paso 2 - Creación de bases de datos en el SGBD PostgreSQL.

Una vez descargado el proyecto en su equipo de cómputo habrá el programa de **pgAdmin4**, el cual es una herramienta útil para el manejo de bases de datos en el SGBD de **PostgreSQL**.  Posteriormente cree dos bases de datos llamadas de la siguiente manera:

- **db_microservicio_alumnos**: Base de datos que corresponde al microservicio de alumnos.
- **db_microservicio_respuestas**: Base de datos que corresponde al microservicio de respuestas.

Es importante especificar que el puerto con el que debe ejecutarse el SGBD de PostgreSQL sobre el equipo local es el 5432. De igual forma las columnas o atributos de las entidades de las bases de datos se crearán de forma automática al momento de construir los microservicios desde el IDE de NetBeans.

___Nota:___ _También puede utilizar la herramienta **DBeaver**  para administrar las distintas bases de datos de manera más sencilla. Para ello deberá especificar el SGBD de origen (MariaDB o PostgreSQL), el nombre de usuario, contraseña y número de puerto._

#### Paso 3 - Creación de bases de datos en el SGBD MariaDB.

Una vez descargado el proyecto en su equipo de computo habra el programa de **HeidiSQL**, el cual es una herramienta util para el manejo de bases de datos en el SGBD de **MariaDB**.  Posteriormente cree dos bases de datos llamadas de la siguiente manera:

- **db_microservicio_cursos**: Base de datos que corresponde al microservicio de cursos.
- **db_microservicio_examenes**: Base de datos que corresponde al microservicio de examenes.

Es importante especificar que el puerto con el que debe ejecutarse el SGBD de MariaDB sobre el equipo local es el 3306. De igual forma las columnas o atributos de las entidades de las bases de datos se crearan de forma automatica al momento de construir los microservicios desde el IDE de NetBeans.

___Nota:___ _Tambien puede utilizar la herramienta **DBeaver**  para administrar las distintas bases de datos de manera mas sencilla. Para ello debera especificar el SGBD de origen (MariaDB o PostgreSQL), el nombre de usuario, contraseña y número de puerto._

#### Paso 4 - Construcción y Ejecución de los Servicios en el IDE NetBeans.

Una vez descargado el repositorio en el equipo local, habrá el IDE de Netbeans e importe el proyecto de desarrollo. Posteriormente seleccione el proyecto y active la funcionalidad llamada _"Clean and Build"_ lo cual permitirá construir el entorno de desarrollo y de igual forma procesar las descargas asociadas a las dependencias utilizadas en el proyecto de desarrollo. 

Al finalizar el proceso debe visualizarse en consola el mensaje _"BUILD SUCCESS"_ el cual indica una correcta ejecución de los servicios que componen el proyecto de desarrollo.

Una alternativa útil para la ejecución de los procesos de manera local, es mediante la herramienta de compilación del lenguaje de Java. Para esto, necesita la URL del archivo creado previamente mediante la funcionalidad de construcción (_Clean and Build_) y puede emplear la terminal de comandos del sistema operativo para la ejecución de los servicios. A continuación se muestra un ejemplo del comando que permite la ejecución del microservicio de alumnos:

```
java -jar C:\Users\carlo\.m2\repository\com\backend\alumns\0.0.1-SNAPSHOT\alumns-0.0.1-SNAPSHOT.jar
```
___Nota:___  _El primer componente que se debe ejecutar es Spring Cloud Eureka, dada la naturaleza del servicio, ya que como se especificó anteriormente, permite registrar los microservicios que son clientes de Eureka y que están registrados a dicho servicio._

#### Paso 5 - Creación de las imagenes de los Servicios con Docker.

Es importante mencionar que el framework de Springboot provee una funcionalidad mediante un plugin que permite crear directamente en el servicio de Docker las imágenes y además subir al servicio de Docker Hub dicha imagen para ser almacenada en la cuenta de usuario del desarrollador. 

El plugin esta comentado dentro de la sección del archivo **POM.xml** en cada uno de los microservicios en donde se requiere crear una imagen del servicio.  De tal manera que para la correcta ejecución del presente proyecto de desarrollo, deberá descomentar el plugin y volver a accionar la funcionalidad para construir el entorno de desarrollo y de igual forma procesar las descargas asociadas a las dependencias utilizadas en el proyecto de desarrollo mediante la funcionalidad llamada _"Clean and Build"_.

Es importante mencionar que en cada microservicio se encuentra un archivo denominado **Dockerfile** el cual define las reglas de creación para la imagen en el servicio de Docker.

A continuación se muestra el código fuente que define la ejecución del plugin antes mencionado:

```
<build>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>${dockerfile-maven-version}</version>
                <executions>
                    <execution>
                        <id>build-docker-image</id>
                        <phase>package</phase>
                        <goals>
                            <goal>build</goal>
                            <goal>push</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <repository>backend/microservice-docker-${project.artifactId}</repository>
                    <tag>${project.version}</tag>
                    <buildArgs>
                        <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

#### Paso 6 - Construcción de los contenedores de virtualización con Docker.

Una vez finalizado el paso anterior y ya teniendo las imágenes creadas de los microservicios en el servicio de Docker, se procederá a construir una orquestación de virtualización para la administración de las imágenes. Para ello, es necesario especificar que en la ruta raíz del repositorio se encuentra un archivo llamado **docker-compose.yml** el cual define la estructura de cada microservicio en particular, para ello se define un nombre del servicio, la ruta de la imagen, puerto de acceso interno/externo y finalmente las variables de entorno.

Ahora bien para la creación de los contenedores mediante Docker-Compose tomé en cuenta los siguientes comandos:

- **docker images:** Muestra un listado de las imágenes creadas en docker.
- **docker-compose up -d:** Crea y levanta los contenedores a partir de la imagen especificada en el archivo docker-compose.yml
- **docker ps -a:** Muestra un listado de los contenedores creados en docker-compose.
- **docker-compose start:** Inicializa los contenedores de docker-compose.
- **docker-compose stop:** Detiene los contenedores de docker-compose.

___Nota:___  _Para acceder a los microservicios en ejecución habrá un navegador web e ingresé la dirección local IPv4 del servicio seguido del número de puerto._