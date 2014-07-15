CREATE TABLE categorias_de_visa (
  id VARCHAR(6) NOT NULL PRIMARY KEY,
  tipo_visa_id VARCHAR(4) NOT NULL REFERENCES tipos_de_visa(id),
  nombre VARCHAR(100) NOT NULL
);