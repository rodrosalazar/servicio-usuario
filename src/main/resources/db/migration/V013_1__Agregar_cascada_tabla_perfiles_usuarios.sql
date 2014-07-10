ALTER TABLE perfiles_usuarios
    DROP CONSTRAINT perfiles_usuarios_usuario_id_fkey,
    ADD CONSTRAINT perfiles_usuarios_usuario_id_fkey
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE;