CREATE SEQUENCE permisos_id_seq
  MINVALUE 1
  START WITH 1;
CREATE TABLE permisos (
    id integer NOT NULL DEFAULT nextval('permisos_id_seq') primary key,
    nombre text,
    perfil_id integer NOT NULL references perfiles(id)
);
ALTER SEQUENCE permisos_id_seq OWNED BY permisos.id;