CREATE SEQUENCE perfiles_id_seq;
CREATE TABLE perfiles (
    id integer NOT NULL DEFAULT nextval('perfiles_id_seq'),
    nombre text
);
ALTER SEQUENCE perfiles_id_seq OWNED BY perfiles.id;