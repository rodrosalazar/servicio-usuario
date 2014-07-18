CREATE SEQUENCE portadores_titulo_id_seq
MINVALUE 1
START WITH 1;
CREATE TABLE portadores_titulo (
    id                     INTEGER NOT NULL DEFAULT nextval('portadores_titulo_id_seq') PRIMARY KEY,
    nombresCompletos       VARCHAR(255),
    tipoDocumento          VARCHAR(9),
    numeroIdentificacion   VARCHAR(10),
    email                  VARCHAR(255),
    sexo                   VARCHAR(10),
    fechaNacimiento        DATE,
    telefonoConvencional   VARCHAR(9),
    extension              VARCHAR(5),
    aceptaCondiciones      boolean,
    telefonoCelular        VARCHAR(10),
    etnia_id               VARCHAR(2) NOT NULL REFERENCES etnias(id),
    direccion_id           INTEGER NOT NULL REFERENCES direcciones(id)
);
ALTER SEQUENCE portadores_titulo_id_seq OWNED BY portadores_titulo.id;