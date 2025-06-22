-- Eliminamos la restriccion UNIQUE del email de la tabla clientes

-- ALTER TABLE clientes
--     DROP CONSTRAINT clientes_email_key;

ALTER TABLE clientes
    ALTER COLUMN email DROP NOT NULL;