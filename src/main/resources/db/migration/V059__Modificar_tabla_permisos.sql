ALTER TABLE permisos RENAME COLUMN nombre to funcion;

ALTER TABLE permisos ADD COLUMN modulo VARCHAR(100) NOT NULL ;