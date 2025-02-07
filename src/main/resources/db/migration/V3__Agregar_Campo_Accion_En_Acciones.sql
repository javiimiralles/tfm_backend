-- Añadimos el campo 'accion' en la tabla 'acciones'
ALTER TABLE acciones
    ADD accion VARCHAR(50) NOT NULL DEFAULT '';