ALTER TABLE direcciones DROP COLUMN numeroCasa;
ALTER TABLE direcciones DROP COLUMN calleSecundaria;
ALTER TABLE direcciones RENAME COLUMN callePrincipal TO direccionCompleta;