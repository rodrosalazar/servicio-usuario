CREATE TABLE modulo_funcion_relaciones (
  modulo_id INTEGER NOT NULL,
  funcion_id INTEGER NOT NULL,
  PRIMARY KEY (modulo_id, funcion_id),
  CONSTRAINT modulo_funcion_relaciones_modulo_id FOREIGN KEY (modulo_id)
    REFERENCES modulos (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  CONSTRAINT modulo_funcion_relaciones_function_id FOREIGN KEY (funcion_id)
    REFERENCES funciones (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);