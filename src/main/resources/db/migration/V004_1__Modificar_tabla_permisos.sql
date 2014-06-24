DROP TABLE permisos;

CREATE TABLE permisos (
    id serial primary key,
    moduloId integer not null,
    funcionId integer not null,
    perfil_id integer NOT NULL references perfiles(id)
);