CREATE TABLE perfiles_usuarios (
    usuario_id integer not null references usuarios(id),
    perfil_id integer not null references perfiles(id)
);