-- Quitar la restriccion de obligatoriedad en la columna id_categoria en 'productos'
ALTER TABLE productos
    ALTER COLUMN id_categoria DROP NOT NULL;