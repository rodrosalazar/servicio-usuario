ALTER TABLE usuarios
    ADD CONSTRAINT nombreUsuarioUnico UNIQUE (nombreUsuario);