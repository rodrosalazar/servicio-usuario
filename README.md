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
**\* Para este archivo, pregunta a alguien del equipo por las credenciales para la configuración BSG y del correo electrónico que se emplea en el desarrollo.**

```
$ cp src/test/resources/test-integracion.yml.example src/test/resources/test-integracion.yml
```
**\* Para este archivo copia las credenciales que usaste en el archivo anterior.**

```
./gradlew build
```

Luego en tu IntelliJ ve al menú ```File>Project Structure``` y en la ventana de diálogo ve a la sección ```Modules``` y en la pestaña Sources verás la estructura de fólders que
son considerados como fuentes para el proyecto. Aquí por defecto se incluye a todo el fólder de ```build```, excluyamos a este fólder así como a todos sus fólders internos,
a excepción del fólder ```generated-sources```.

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

- Luego que termine su ejecución deberías ver en la consola que se levantaron los tres puertos 8080, 8081 y 8443.
Algo parecido a:

```
INFO  [2014-08-11 15:09:21,254] org.eclipse.jetty.server.ServerConnector: Started application@36327cec{HTTP/1.1}{0.0.0.0:8080}
INFO  [2014-08-11 15:09:21,260] org.eclipse.jetty.server.ServerConnector: Started application@3664f108{SSL-HTTP/1.1}{0.0.0.0:8443}
INFO  [2014-08-11 15:09:21,266] org.eclipse.jetty.server.ServerConnector: Started admin@2740e316{HTTP/1.1}{0.0.0.0:8081}
```
- Prueba en tu browser con uno de los servicios que reciba GET (i.e. ```https://localhost:8443/<nombre de servicio con GET>```).

- Verificar si todo está bien yendo a <http://localtest.me:8081>.


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
1. Crear el certificado autofirmado (modificar la línea de comando para poner un password de verdad):
```
$ keytool -genkey -keyalg RSA -alias sniese -keystore sniese.keystore -storepass <ingresar aqui el password> -validity 360 -keysize 2048
```
Cuando en la consola aparezca la siguiente pregunta:
```What is your first and last name?```
Responder: ```localhost```

2. Exportar el certificado a un formato binario:
```
$ keytool -exportcert -alias sniese -keystore sniese.keystore -file sniese.der -rfc
```

3. Importar el certificado a la JVM (Si se solicita un password es: ```changeit```)
```
$ sudo keytool -import -alias sniese -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_05.jdk/Contents/Home/jre/lib/security/cacerts -file sniese.der
```

4. En los archivos YML que se necesiten setear las propiedades keyStorePath y keyStorePassword con el password que se ha ingresado en el proceso de generacion del certificado
