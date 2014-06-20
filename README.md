# servicio-usuario

Micro servicio para el manejo de usuarios usando Dropwizard

## Requisitos
- PostgreSql
- Java 8

## Primera Vez

Para crear la base de datos en PostgreSQL (este proceso será automatizado):
```
$ createdb senescyt

$ createuser -W thoughtworks

$ psql

=# GRANT ALL ON DATABASE senescyt TO thoughtworks;
```

## Pasos para correr el servicio web:

- Ejecutar gradle:

```
$ ./gradlew run
```
      
- Ir a <http://localtest.me:8081>


## Para crear un proyecto de IntelliJ:

- Correr:

```
$ ./gradlew idea
```

## Para convertir archivos CSV de instituciones a SQL:

- Correr:

```
$ ./gradlew runCsv -P params="ruta-entrada [ruta-salida]"
```

Resolución de Problemas:
----------------

- Tal vez necesites instalar [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
