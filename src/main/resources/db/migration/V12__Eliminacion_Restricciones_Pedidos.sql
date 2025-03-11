-- Eliminamos algunas restricciones de la tabla pedidos e historial_pedidos

ALTER TABLE pedidos
    ALTER COLUMN fecha_pedido DROP NOT NULL;

ALTER TABLE pedidos
    ALTER COLUMN metodo_pago DROP NOT NULL;

ALTER TABLE historial_pedidos
    ALTER COLUMN estado_anterior DROP NOT NULL;