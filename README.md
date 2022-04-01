# gestbifly-springboot-api

Proyecto API de gestión de préstamos en una biblioteca - librería basado en tecnologías Spring Boot, Spring Data, JPA, Hibernate, Lombok y Mapstruct

<div align="center" markdown="1">

![GestBiFly!](screenshot.png)

</div>

### Requisitos

A partir del proyecto de la librería que [tenéis subido en el Equipo de Teams](https://seresco.sharepoint.com/:u:/r/sites/2021191PROGRAMACINWEBENELENTORNOSERVIDORTARDES/Documentos%20compartidos/General/Ejercicios/Librer%C3%ADa.zip?csf=1&web=1&e=TfdFeQ) se pide: 

- Crear un proyecto Spring Boot con el mismo modelo de entidades.
- Implementación de servicios y repositorios para cada una de las entidades.
- Gestión CRUD de libros.
- Gestión CRUD de copias.
- Gestión CRUD de usuarios.
- Gestión de préstamos y devoluciones.

Las especificaciones funcionales son las siguientes:

- Se tienen distintas categorías literarias, entre las que se destacan [1.] Novela, [2.] Teatro, [3.] Poesía y [4.] Ensayo. Estas categorías deben ser gestionables por la aplicación (CRUD).
- En la biblioteca se encuentran libros, que tendrán los siguientes atributos: código, título, ISBN, categoría, autor, editorial.
- Los autores son otra entidad importante en nuestro modelo, por lo que también ser gestionará el alta, baja y modificación de los mismos.
- De cada libro existirá un número finito de copias, que tendrán un identificador y un estado: [1.] en la biblioteca, [2.] prestada, [3.] con retraso y [4.] en reparación.
- Se tendrán registrados usuarios, que contarán con una clave alfanumérica que los identifique de manera inequívoca.
- Cada uno de los usuarios puede tener un máximo de tres libros prestados, y se debe llevar un registro del histórico de usuarios que han cogido prestada cada una de las copias.


### Getting Started

#### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-validation)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#production-ready)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-spring-hateoas)

#### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)

