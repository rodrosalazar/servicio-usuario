# servicio-usuario

Micro servicio para el manejo de usuarios usando Dropwizard

## Requisitos
- PostgreSql
- Java 8
- Postgres.app (opcional, pero muy útil para no tener que configurar PostgreSql en tu línea de comandos y tener un lindo ícono de elefante en tu barra de menú del sistema operativo)

## Primera Vez

```
$ cp servicio-usuario-dev.yml.example servicio-usuario-dev.yml
```
\* Para este archivo, pregunta a alguien del equipo por las credenciales para la configuración BSG, además de reemplazar las credenciales del correo electrónico por las tuyas o algún servicio SMTP.

```
$ cp src/test/resources/test-integracion.yml.example src/test/resources/test-integracion.yml
```

Para crear la base de datos en PostgreSQL (este proceso será automatizado):
```
$ createdb senescyt

$ createuser -W thoughtworks

$ psql

=# GRANT ALL ON DATABASE senescyt TO thoughtworks;
```

## Pasos para correr el servicio web

- Ejecutar gradle:

```
$ ./gradlew run
```

- Ir a <http://localtest.me:8081>


## Para correr las pruebas

- Correr:

```
$ ./gradlew test
```

## Para crear un proyecto de IntelliJ

- Correr:

```
$ ./gradlew idea
```

## Para convertir archivos CSV de instituciones a SQL

- Correr:

```
$ ./gradlew runCsv -P params="ruta-entrada [ruta-salida]"
```

## Para correr las migraciones

- Correr:

```
$ ./gradlew flywayMigrate
```

## Para borrar la base de datos

- Correr:

```
$ ./gradlew flywayClean
```

## Para ver el estado de las migraciones

- Correr:

```
$ ./gradlew flywayInfo
```


Resolución de Problemas
----------------

- Tal vez necesites instalar [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

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

Habilitar HTTPS
-----------------
1. Crear el certificado autofirmado:
```
$ keytool -genkey -keyalg RSA -alias sniese -keystore sniese.keystore -storepass password -validity 360 -keysize 2048
```

Cuando en la consola aparezca la siguiente pregunta:

What is your first and last name?

Responder con: localhost


2. Exportar el certificado a un formato binario:
```
$ keytool -exportcert -alias sniese -keystore sniese.keystore -file sniese.der -rfc
```

3. Importar el certificado a la JVM:
```
$ sudo keytool -import -alias sniese -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home/jre/lib/security/cacerts -file sniese.der
```

Si se solicita un password es: changeit

4. En os archivos YML que se necesiten setear las propiedades keyStorePath y keyStorePassword con el password que se ha ingresado en el proceso de generacion del certificado
