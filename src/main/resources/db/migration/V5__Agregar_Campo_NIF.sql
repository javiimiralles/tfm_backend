-- Agregamos el campo 'nif' en las tablas de 'clientes', 'empleados' y 'proveedores'

ALTER TABLE clientes ADD COLUMN nif VARCHAR(9);
ALTER TABLE empleados ADD COLUMN nif VARCHAR(9);
ALTER TABLE proveedores ADD COLUMN nif VARCHAR(9);