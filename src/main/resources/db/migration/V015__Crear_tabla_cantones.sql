CREATE TABLE cantones (
  provincia_id VARCHAR(2) NOT NULL REFERENCES provincias(id),
  id VARCHAR(4) NOT NULL PRIMARY KEY,
  nombre VARCHAR(64) NOT NULL
);