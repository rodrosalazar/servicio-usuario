CREATE TABLE usuario_perfil_modulo_relaciones (
  usuario_perfil_id INTEGER NOT NULL,
  modulo_id INTEGER NOT NULL,
  PRIMARY KEY (usuario_perfil_id, modulo_id),
  CONSTRAINT usuario_perfil_modulo_relaciones_usuario_perfil_id FOREIGN KEY (usuario_perfil_id)
    REFERENCES usuario_perfiles (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  CONSTRAINT usuario_perfil_modulo_relaciones_modulo_id FOREIGN KEY (modulo_id)
    REFERENCES modulos (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);