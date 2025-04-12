-- Añadimos los campos id_cliente y fecha_vencimiento a la tabla 'facturas'

ALTER TABLE facturas
    ADD id_cliente INTEGER NOT NULL;

ALTER TABLE facturas
    ADD fecha_vencimiento DATE NOT NULL;

ALTER TABLE facturas
    ADD CONSTRAINT fk_facturas_clientes FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE CASCADE ON UPDATE CASCADE;