-- Añadimos nuevas acciones a la tabla de 'acciones'

-- Presupuestos
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Acceso a presupuestos', 'Permite ver la lista de presupuestos', 'ACCESO_PRESUPUESTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Creación de presupuestos', 'Permite crear un nuevo presupuesto', 'CREACION_PRESUPUESTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Edición de presupuestos', 'Permite editar un presupuesto', 'EDICION_PRESUPUESTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Eliminación de presupuestos', 'Permite eliminar un presupuesto', 'ELIMINACION_PRESUPUESTOS');

-- Proveedores
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Acceso a proveedores', 'Permite ver la lista de proveedores', 'ACCESO_PROVEEDORES');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Creación de proveedores', 'Permite crear un nuevo proveedor', 'CREACION_PROVEEDORES');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Edición de proveedores', 'Permite editar un proveedor', 'EDICION_PROVEEDORES');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Eliminación de proveedores', 'Permite eliminar un proveedor', 'ELIMINACION_PROVEEDORES');

-- Categorias productos
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Acceso a categorías de productos', 'Permite ver la lista de categorias de productos', 'ACCESO_CATEGORIAS_PRODUCTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Creación de categorías de productos', 'Permite crear una nueva categoría de productos', 'CREACION_CATEGORIAS_PRODUCTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Edición de categorías de productos', 'Permite editar una categoría de productos', 'EDICION_CATEGORIAS_PRODUCTOS');
INSERT INTO acciones (nombre, descripcion, accion) VALUES ('Eliminación de categorías de productos', 'Permite eliminar una categoría de productos', 'ELIMINACION_CATEGORIAS_PRODUCTOS');