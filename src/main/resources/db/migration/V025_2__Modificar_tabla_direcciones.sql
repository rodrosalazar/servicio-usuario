ALTER TABLE direcciones
ALTER COLUMN numeroCasa TYPE VARCHAR(50);
ALTER TABLE direcciones
ADD FOREIGN KEY (idProvincia) REFERENCES provincias(id);
ALTER TABLE direcciones
ADD FOREIGN KEY (idCanton) REFERENCES cantones(id);
ALTER TABLE direcciones
ADD FOREIGN KEY (idParroquia) REFERENCES parroquias(id);
ALTER TABLE direcciones
ADD FOREIGN KEY (idPais) REFERENCES paises(id);