CREATE TABLE accesos (
    permiso_id integer not null references permisos(id),
    accesos text
);