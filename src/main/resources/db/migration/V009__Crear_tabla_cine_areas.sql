CREATE TABLE cine_areas (
  id VARCHAR(2) NOT NULL PRIMARY KEY,
  nombre VARCHAR(64) NOT NULL,
  clasificacion_id VARCHAR(3) NOT NULL REFERENCES cine_clasificaciones(id)
);