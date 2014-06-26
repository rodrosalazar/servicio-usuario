CREATE SEQUENCE usuarios_id_seq
MINVALUE 1
START WITH 1;
CREATE TABLE usuarios (
  id                       INTEGER NOT NULL DEFAULT nextval('usuarios_id_seq') PRIMARY KEY,
  tipoDocumento            VARCHAR(9),
  numeroIdentificacion     VARCHAR(10),
  primerNombre             VARCHAR(255),
  segundoNombre            VARCHAR(255),
  primerApellido           VARCHAR(255),
  segundoApellido          VARCHAR(255),
  emailInstitucional       VARCHAR(255),
  numeroAutorizacionQuipux VARCHAR(30),
  finDeVigencia            DATE,
  idInstitucion            INT,
  nombreUsuario            VARCHAR(255)
);
ALTER SEQUENCE usuarios_id_seq OWNED BY usuarios.id;