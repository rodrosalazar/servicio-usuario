CREATE TABLE perfil_permiso_relaciones (
  perfil_id INTEGER NOT NULL,
  permiso_id INTEGER NOT NULL,
  PRIMARY KEY (perfil_id, permiso_id),
  CONSTRAINT perfil_permiso_relaciones_perfil_id FOREIGN KEY (perfil_id)
    REFERENCES perfiles (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
  CONSTRAINT perfil_permiso_relaciones_permiso_id FOREIGN KEY (permiso_id)
    REFERENCES permisos (id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);