CREATE SEQUENCE direcciones_id_seq
MINVALUE 1
START WITH 1;
CREATE TABLE direcciones (
    id               INTEGER NOT NULL DEFAULT nextval('direcciones_id_seq') PRIMARY KEY,
    callePrincipal   VARCHAR(255),
    numeroCasa       VARCHAR(255),
    calleSecundaria  VARCHAR(255),
    idProvincia      VARCHAR(2),
    idCanton         VARCHAR(4),
    idParroquia      VARCHAR(6),
    idPais           VARCHAR(6)
);
ALTER SEQUENCE direcciones_id_seq OWNED BY direcciones.id;