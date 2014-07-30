DELETE FROM portadores_titulo;

ALTER TABLE portadores_titulo
    DROP COLUMN tipoDocumento;

ALTER TABLE portadores_titulo
DROP COLUMN numeroIdentificacion;

ALTER TABLE portadores_titulo
    ADD COLUMN id_identificacion BIGINT NOT NULL REFERENCES identificaciones(id);