-- Cambiar el nombre de la columna imagen a imagen_url en 'productos'
ALTER TABLE productos
    rename COLUMN imagen TO imagen_url;

-- Agregar columna imagen_url en 'empleados'
ALTER TABLE empleados
    ADD imagen_url VARCHAR(255);