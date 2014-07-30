CREATE SEQUENCE identificaciones_id_seq
MINVALUE 1
START WITH 1;

CREATE TABLE identificaciones (

  id BIGINT NOT NULL DEFAULT nextval('identificaciones_id_seq') PRIMARY KEY,
  tipoDocumento VARCHAR(9),
  numeroIdentificacion VARCHAR(10),
  finVigenciaPasaporte DATE,
  finVigenciaVisa DATE,
  id_tipo_visa VARCHAR(1) REFERENCES tipos_de_visa(id)
);