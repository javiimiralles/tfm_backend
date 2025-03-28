-- Convertir los enums en enteros

ALTER TABLE pedidos
    ALTER COLUMN estado TYPE INTEGER USING estado::INTEGER;

ALTER TABLE pedidos
    ALTER COLUMN tipo TYPE INTEGER USING tipo::INTEGER;

ALTER TABLE pedidos
    ALTER COLUMN metodo_pago TYPE INTEGER USING metodo_pago::INTEGER;

ALTER TABLE pedidos_proveedores
    ALTER COLUMN estado TYPE INTEGER USING estado::INTEGER;

ALTER TABLE empleados
    ALTER COLUMN genero TYPE INTEGER USING genero::INTEGER;