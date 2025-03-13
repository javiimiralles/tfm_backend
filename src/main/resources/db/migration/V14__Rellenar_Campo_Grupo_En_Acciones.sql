-- Añadimos a qué grupo pertenece cada acción

-- Empleados
UPDATE acciones SET grupo = 'Empleados' WHERE accion = 'ACCESO_EMPLEADOS';
UPDATE acciones SET grupo = 'Empleados' WHERE accion = 'CREACION_EMPLEADOS';
UPDATE acciones SET grupo = 'Empleados' WHERE accion = 'EDICION_EMPLEADOS';
UPDATE acciones SET grupo = 'Empleados' WHERE accion = 'ELIMINACION_EMPLEADOS';

-- Empresa
UPDATE acciones SET grupo = 'Empresa' WHERE accion = 'ACCESO_EMPRESA';
UPDATE acciones SET grupo = 'Empresa' WHERE accion = 'EDICION_EMPRESA';

-- Roles
UPDATE acciones SET grupo = 'Roles' WHERE accion = 'ACCESO_ROLES';
UPDATE acciones SET grupo = 'Roles' WHERE accion = 'CREACION_ROLES';
UPDATE acciones SET grupo = 'Roles' WHERE accion = 'EDICION_ROLES';
UPDATE acciones SET grupo = 'Roles' WHERE accion = 'ELIMINACION_ROLES';

-- Clientes
UPDATE acciones SET grupo = 'Clientes' WHERE accion = 'ACCESO_CLIENTES';
UPDATE acciones SET grupo = 'Clientes' WHERE accion = 'CREACION_CLIENTES';
UPDATE acciones SET grupo = 'Clientes' WHERE accion = 'EDICION_CLIENTES';
UPDATE acciones SET grupo = 'Clientes' WHERE accion = 'ELIMINACION_CLIENTES';

-- Facturas
UPDATE acciones SET grupo = 'Facturas' WHERE accion = 'ACCESO_FACTURAS';
UPDATE acciones SET grupo = 'Facturas' WHERE accion = 'CREACION_FACTURAS';
UPDATE acciones SET grupo = 'Facturas' WHERE accion = 'EDICION_FACTURAS';
UPDATE acciones SET grupo = 'Facturas' WHERE accion = 'ELIMINACION_FACTURAS';

-- Pagos
UPDATE acciones SET grupo = 'Pagos' WHERE accion = 'ACCESO_PAGOS';
UPDATE acciones SET grupo = 'Pagos' WHERE accion = 'CREACION_PAGOS';
UPDATE acciones SET grupo = 'Pagos' WHERE accion = 'EDICION_PAGOS';
UPDATE acciones SET grupo = 'Pagos' WHERE accion = 'ELIMINACION_PAGOS';

-- Pedidos
UPDATE acciones SET grupo = 'Pedidos' WHERE accion = 'ACCESO_PEDIDOS';
UPDATE acciones SET grupo = 'Pedidos' WHERE accion = 'CREACION_PEDIDOS';
UPDATE acciones SET grupo = 'Pedidos' WHERE accion = 'EDICION_PEDIDOS';
UPDATE acciones SET grupo = 'Pedidos' WHERE accion = 'ELIMINACION_PEDIDOS';

-- Pedidos a proveedores
UPDATE acciones SET grupo = 'Pedidos a proveedores' WHERE accion = 'ACCESO_PEDIDOS_PROVEEDORES';
UPDATE acciones SET grupo = 'Pedidos a proveedores' WHERE accion = 'CREACION_PEDIDOS_PROVEEDORES';
UPDATE acciones SET grupo = 'Pedidos a proveedores' WHERE accion = 'EDICION_PEDIDOS_PROVEEDORES';
UPDATE acciones SET grupo = 'Pedidos a proveedores' WHERE accion = 'ELIMINACION_PEDIDOS_PROVEEDORES';

-- Productos
UPDATE acciones SET grupo = 'Productos' WHERE accion = 'ACCESO_PRODUCTOS';
UPDATE acciones SET grupo = 'Productos' WHERE accion = 'CREACION_PRODUCTOS';
UPDATE acciones SET grupo = 'Productos' WHERE accion = 'EDICION_PRODUCTOS';
UPDATE acciones SET grupo = 'Productos' WHERE accion = 'ELIMINACION_PRODUCTOS';

-- Presupuestos
UPDATE acciones SET grupo = 'Presupuestos' WHERE accion = 'ACCESO_PRESUPUESTOS';
UPDATE acciones SET grupo = 'Presupuestos' WHERE accion = 'CREACION_PRESUPUESTOS';
UPDATE acciones SET grupo = 'Presupuestos' WHERE accion = 'EDICION_PRESUPUESTOS';
UPDATE acciones SET grupo = 'Presupuestos' WHERE accion = 'ELIMINACION_PRESUPUESTOS';

-- Proveedores
UPDATE acciones SET grupo = 'Proveedores' WHERE accion = 'ACCESO_PROVEEDORES';
UPDATE acciones SET grupo = 'Proveedores' WHERE accion = 'CREACION_PROVEEDORES';
UPDATE acciones SET grupo = 'Proveedores' WHERE accion = 'EDICION_PROVEEDORES';
UPDATE acciones SET grupo = 'Proveedores' WHERE accion = 'ELIMINACION_PROVEEDORES';

-- Categorias productos
UPDATE acciones SET grupo = 'Categorías productos' WHERE accion = 'ACCESO_CATEGORIAS_PRODUCTOS';
UPDATE acciones SET grupo = 'Categorías productos' WHERE accion = 'CREACION_CATEGORIAS_PRODUCTOS';
UPDATE acciones SET grupo = 'Categorías productos' WHERE accion = 'EDICION_CATEGORIAS_PRODUCTOS';
UPDATE acciones SET grupo = 'Categorías productos' WHERE accion = 'ELIMINACION_CATEGORIAS_PRODUCTOS';

