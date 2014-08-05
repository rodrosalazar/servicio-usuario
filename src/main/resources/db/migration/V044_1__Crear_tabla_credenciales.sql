CREATE TABLE credenciales (
  nombreUsuario VARCHAR(255) NOT NULL PRIMARY KEY REFERENCES usuarios(nombreUsuario),
  hash VARCHAR(60) NOT NULL
);