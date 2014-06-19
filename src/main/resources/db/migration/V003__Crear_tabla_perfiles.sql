CREATE SEQUENCE perfiles_id_seq
  MINVALUE 1
  START WITH 1;
CREATE TABLE perfiles (
    id integer NOT NULL DEFAULT nextval('perfiles_id_seq') primary key,
    nombre text
);
ALTER SEQUENCE perfiles_id_seq OWNED BY perfiles.id;