CREATE TABLE cine_subareas (
  id VARCHAR(3) NOT NULL PRIMARY KEY,
  nombre VARCHAR(64) NOT NULL,
  area_id VARCHAR(2) NOT NULL REFERENCES cine_areas(id)
);