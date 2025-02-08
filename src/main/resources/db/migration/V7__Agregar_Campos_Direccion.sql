-- Agregamos campos que añadan información a la dirección de 'empleados', 'clientes' y 'proveedores'

-- Agregamos campos a la tabla 'empleados'
ALTER TABLE empleados ADD COLUMN id_pais INT
    REFERENCES paises(id) ON DELETE SET NULL;
ALTER TABLE empleados ADD COLUMN provincia VARCHAR(100);
ALTER TABLE empleados ADD COLUMN poblacion VARCHAR(100);
ALTER TABLE empleados ADD COLUMN codigo_postal VARCHAR(10);

-- Agregamos campos a la tabla 'clientes'
ALTER TABLE clientes ADD COLUMN id_pais INT
    REFERENCES paises(id) ON DELETE SET NULL;
ALTER TABLE clientes ADD COLUMN provincia VARCHAR(100);
ALTER TABLE clientes ADD COLUMN poblacion VARCHAR(100);
ALTER TABLE clientes ADD COLUMN codigo_postal VARCHAR(10);

-- Agregamos campos a la tabla 'proveedores'
ALTER TABLE proveedores ADD COLUMN id_pais INT
    REFERENCES paises(id) ON DELETE SET NULL;
ALTER TABLE proveedores ADD COLUMN provincia VARCHAR(100);
ALTER TABLE proveedores ADD COLUMN poblacion VARCHAR(100);
ALTER TABLE proveedores ADD COLUMN codigo_postal VARCHAR(10);