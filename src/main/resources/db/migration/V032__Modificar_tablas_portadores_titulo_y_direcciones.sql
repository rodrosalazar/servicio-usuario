ALTER TABLE direcciones
  DROP COLUMN idPais;

ALTER TABLE portadores_titulo
  ADD COLUMN idPaisNacionalidad VARCHAR(6) NOT NULL REFERENCES paises(id);