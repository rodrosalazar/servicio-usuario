CREATE TABLE cine_detalles (
  id VARCHAR(4) NOT NULL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  subarea_id VARCHAR(3) NOT NULL REFERENCES cine_subareas(id)
);