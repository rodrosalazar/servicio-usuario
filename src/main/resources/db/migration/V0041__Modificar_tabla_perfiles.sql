DELETE FROM accesos;
DELETE FROM permisos;
DELETE FROM perfiles_usuarios;
DELETE FROM perfiles;

ALTER TABLE perfiles
    ALTER COLUMN nombre TYPE VARCHAR(100);