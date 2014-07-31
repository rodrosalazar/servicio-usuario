ALTER TABLE identificaciones
  ALTER COLUMN tipoDocumento SET NOT NULL;

ALTER TABLE identificaciones
  ALTER COLUMN numeroIdentificacion TYPE VARCHAR(20);
ALTER TABLE identificaciones
  ALTER COLUMN numeroIdentificacion SET NOT NULL;

ALTER TABLE identificaciones
  ADD COLUMN visaIndefinida BOOLEAN NOT NULL DEFAULT false;