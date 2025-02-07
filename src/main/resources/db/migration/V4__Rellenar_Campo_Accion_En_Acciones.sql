-- Rellenamos el campo 'accion' creado en la migración V3__Crear_Campo_Accion_En_Acciones.sql

-- Empleados
UPDATE acciones SET accion = 'ACCESO_EMPLEADOS' WHERE nombre = 'Acceso a empleados';
UPDATE acciones SET accion = 'CREACION_EMPLEADOS' WHERE nombre = 'Creación de empleados';
UPDATE acciones SET accion = 'EDICION_EMPLEADOS' WHERE nombre = 'Edición de empleados';
UPDATE acciones SET accion = 'ELIMINACION_EMPLEADOS' WHERE nombre = 'Eliminación de empleados';

-- Empresa
UPDATE acciones SET accion = 'ACCESO_EMPRESA' WHERE nombre = 'Acceso a información de la empresa';
UPDATE acciones SET accion = 'EDICION_EMPRESA' WHERE nombre = 'Edición de información de la empresa';

-- Roles
UPDATE acciones SET accion = 'ACCESO_ROLES' WHERE nombre = 'Acceso a roles';
UPDATE acciones SET accion = 'CREACION_ROLES' WHERE nombre = 'Creación de roles';
UPDATE acciones SET accion = 'EDICION_ROLES' WHERE nombre = 'Edición de roles';
UPDATE acciones SET accion = 'ELIMINACION_ROLES' WHERE nombre = 'Eliminación de roles';

-- Clientes
UPDATE acciones SET accion = 'ACCESO_CLIENTES' WHERE nombre = 'Acceso a clientes';
UPDATE acciones SET accion = 'CREACION_CLIENTES' WHERE nombre = 'Creación de clientes';
UPDATE acciones SET accion = 'EDICION_CLIENTES' WHERE nombre = 'Edición de clientes';
UPDATE acciones SET accion = 'ELIMINACION_CLIENTES' WHERE nombre = 'Eliminación de clientes';

-- Facturas
UPDATE acciones SET accion = 'ACCESO_FACTURAS' WHERE nombre = 'Acceso a facturas';
UPDATE acciones SET accion = 'CREACION_FACTURAS' WHERE nombre = 'Creación de facturas';
UPDATE acciones SET accion = 'EDICION_FACTURAS' WHERE nombre = 'Edición de facturas';
UPDATE acciones SET accion = 'ELIMINACION_FACTURAS' WHERE nombre = 'Eliminación de facturas';

-- Pagos
UPDATE acciones SET accion = 'ACCESO_PAGOS' WHERE nombre = 'Acceso a pagos';
UPDATE acciones SET accion = 'CREACION_PAGOS' WHERE nombre = 'Creación de pagos';
UPDATE acciones SET accion = 'EDICION_PAGOS' WHERE nombre = 'Edición de pagos';
UPDATE acciones SET accion = 'ELIMINACION_PAGOS' WHERE nombre = 'Eliminación de pagos';

-- Pedidos
UPDATE acciones SET accion = 'ACCESO_PEDIDOS' WHERE nombre = 'Acceso a pedidos';
UPDATE acciones SET accion = 'CREACION_PEDIDOS' WHERE nombre = 'Creación de pedidos';
UPDATE acciones SET accion = 'EDICION_PEDIDOS' WHERE nombre = 'Edición de pedidos';
UPDATE acciones SET accion = 'ELIMINACION_PEDIDOS' WHERE nombre = 'Eliminación de pedidos';

-- Pedidos a proveedores
UPDATE acciones SET accion = 'ACCESO_PEDIDOS_PROVEEDORES' WHERE nombre = 'Acceso a pedidos a proveedores';
UPDATE acciones SET accion = 'CREACION_PEDIDOS_PROVEEDORES' WHERE nombre = 'Creación de pedidos a proveedores';
UPDATE acciones SET accion = 'EDICION_PEDIDOS_PROVEEDORES' WHERE nombre = 'Edición de pedidos a proveedores';
UPDATE acciones SET accion = 'ELIMINACION_PEDIDOS_PROVEEDORES' WHERE nombre = 'Eliminación de pedidos a proveedores';

-- Productos
UPDATE acciones SET accion = 'ACCESO_PRODUCTOS' WHERE nombre = 'Acceso a productos';
UPDATE acciones SET accion = 'CREACION_PRODUCTOS' WHERE nombre = 'Creación de productos';
UPDATE acciones SET accion = 'EDICION_PRODUCTOS' WHERE nombre = 'Edición de productos';
UPDATE acciones SET accion = 'ELIMINACION_PRODUCTOS' WHERE nombre = 'Eliminación de productos';
