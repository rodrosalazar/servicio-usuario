ALTER TABLE identificaciones
    ADD COLUMN id_categoria_visa VARCHAR(6) REFERENCES categorias_de_visa(id);

UPDATE identificaciones SET id_categoria_visa = '5' WHERE id_tipo_visa IS NOT NULL;

ALTER TABLE identificaciones
    DROP COLUMN id_tipo_visa;