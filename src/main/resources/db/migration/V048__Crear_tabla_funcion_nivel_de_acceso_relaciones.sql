CREATE TABLE funcion_nivel_de_acceso_relaciones (
  funcion_id INTEGER NOT NULL,
  nivel_de_acceso_id INTEGER NOT NULL,
  PRIMARY KEY (funcion_id, nivel_de_acceso_id),
  CONSTRAINT funcion_nivel_de_acceso_relaciones_funcion_id_fkey FOREIGN KEY (funcion_id)
    REFERENCES funciones (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  CONSTRAINT funcion_nivel_de_acceso_relaciones_nivel_de_acceso_id_fkey FOREIGN KEY (nivel_de_acceso_id)
    REFERENCES niveles_de_acceso (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);