# servicio-usuario

Micro servicio para el manejo de usuarios usando Dropwizard

## Requisitos
- PostgreSql
- Java 8
- Postgres.app (opcional, pero muy útil para no tener que configurar PostgreSql en tu línea de comandos y tener un lindo ícono de elefante en tu barra de menú del sistema operativo)

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

- Si instalaste PostgreSql por Homebrew, como buen Jedi, al momento de levantar PostgreSql te pedirá el archivo de configuración. Para ello necesitas establecer la variable PGDATA
```export PGDATA=/usr/local/var/postgres```

Crear el artefacto con todas las dependencias (fat jar)
---------------

- Correr:

```
$ ./gradlew oneJar
```

- El fat jar se crea como build/lib/servicio-usuario-standalone.jar

- Para ejecutar el fat jar:

```
$ java -jar build/lib/servicio-usuario-standalone.jar server servicio-usuario.yml
```
