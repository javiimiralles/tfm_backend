---- Creacion de enums ----
CREATE TYPE GeneroEnum AS ENUM ('M', 'F', 'O');
CREATE TYPE EstadoPedidoProveedorEnum AS ENUM ('Pendiente', 'Enviado', 'Recibido', 'Cancelado');
CREATE TYPE EstadoPedidoEnum AS ENUM ('Pendiente', 'Procesado', 'Enviado', 'Recibido', 'Cancelado');
CREATE TYPE TipoPedidoEnum AS ENUM ('Presupuesto', 'Pedido');
CREATE TYPE MetodoPagoEnum AS ENUM ('Efectivo', 'Tarjeta', 'Transferencia', 'Paypal');

---- Creacion de tablas ----
-- Crear tabla 'empresas'
CREATE TABLE empresas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    razon_social VARCHAR(50) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(15),
    email VARCHAR(150) NOT NULL,
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_baja TIMESTAMP
);

-- Crear tabla 'roles'
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    id_empresa INTEGER NOT NULL,
    CONSTRAINT fk_roles_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'acciones'
CREATE TABLE acciones (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT
);

-- Crear tabla 'permisos'
CREATE TABLE permisos (
    id SERIAL PRIMARY KEY,
    id_rol INTEGER NOT NULL,
    id_accion INTEGER NOT NULL,
    CONSTRAINT fk_permisos_roles FOREIGN KEY (id_rol) REFERENCES roles(id)
      ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_permisos_acciones FOREIGN KEY (id_accion) REFERENCES acciones(id)
      ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'usuarios'
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150),
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(15),
    id_rol INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    CONSTRAINT fk_usuarios_roles FOREIGN KEY (id_rol) REFERENCES roles(id)
      ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_usuarios_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
      ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'proveedores'
CREATE TABLE proveedores (
    id SERIAL PRIMARY KEY,
    id_empresa INTEGER NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    id_resp_alta INTEGER NOT NULL,
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_resp_modif INTEGER,
    fecha_modif TIMESTAMP,
    id_resp_baja INTEGER,
    fecha_baja TIMESTAMP,
    CONSTRAINT fk_proveedores_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_proveedores_usuarios_alta FOREIGN KEY (id_resp_alta) REFERENCES usuarios(id),
    CONSTRAINT fk_proveedores_usuarios_modif FOREIGN KEY (id_resp_modif) REFERENCES usuarios(id),
    CONSTRAINT fk_proveedores_usuarios_baja FOREIGN KEY (id_resp_baja) REFERENCES usuarios(id)
);

-- Crear tabla 'categorias_productos'
CREATE TABLE categorias_productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    id_empresa INTEGER NOT NULL,
    CONSTRAINT fk_categorias_productos_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'productos'
CREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    id_categoria INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    imagen VARCHAR(255),
    precio_venta DECIMAL(10,2) NOT NULL,
    impuesto_venta DECIMAL(10,2) NOT NULL,
    coste DECIMAL(10,2) NOT NULL,
    impuesto_compra DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    id_resp_alta INTEGER NOT NULL,
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_resp_modif INTEGER,
    fecha_modif TIMESTAMP,
    id_resp_baja INTEGER,
    fecha_baja TIMESTAMP,
    CONSTRAINT fk_productos_categorias FOREIGN KEY (id_categoria) REFERENCES categorias_productos(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_productos_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_productos_usuarios_alta FOREIGN KEY (id_resp_alta) REFERENCES usuarios(id),
    CONSTRAINT fk_productos_usuarios_modif FOREIGN KEY (id_resp_modif) REFERENCES usuarios(id),
    CONSTRAINT fk_productos_usuarios_baja FOREIGN KEY (id_resp_baja) REFERENCES usuarios(id)
);

-- Crear tabla 'pedidos_proveedores'
CREATE TABLE pedidos_proveedores (
    id SERIAL PRIMARY KEY,
    id_proveedor INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado EstadoPedidoProveedorEnum NOT NULL,
    coste_total DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_pedidos_proveedores_proveedores FOREIGN KEY (id_proveedor) REFERENCES proveedores(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pedidos_proveedores_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'detalle_pedidos_proveedores'
CREATE TABLE detalle_pedidos_proveedores (
    id SERIAL PRIMARY KEY,
    id_pedido_proveedor INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_pedidos_proveedores_pedidos FOREIGN KEY (id_pedido_proveedor) REFERENCES pedidos_proveedores(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detalle_pedidos_proveedores_productos FOREIGN KEY (id_producto) REFERENCES productos(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'clientes'
CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    id_empresa INTEGER NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150),
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    id_resp_alta INTEGER NOT NULL,
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_resp_modif INTEGER,
    fecha_modif TIMESTAMP,
    id_resp_baja INTEGER,
    fecha_baja TIMESTAMP,
    CONSTRAINT fk_clientes_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_clientes_usuarios_alta FOREIGN KEY (id_resp_alta) REFERENCES usuarios(id),
    CONSTRAINT fk_clientes_usuarios_modif FOREIGN KEY (id_resp_modif) REFERENCES usuarios(id),
    CONSTRAINT fk_clientes_usuarios_baja FOREIGN KEY (id_resp_baja) REFERENCES usuarios(id)
);

-- Crear tabla 'pedidos'
CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado EstadoPedidoEnum NOT NULL,
    fecha_actualizacion TIMESTAMP,
    metodo_pago MetodoPagoEnum NOT NULL,
    coste_total DECIMAL(10,2) NOT NULL,
    observaciones TEXT,
    CONSTRAINT fk_pedidos_clientes FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pedidos_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'detalle_pedidos'
CREATE TABLE detalle_pedidos (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalle_pedidos_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_detalle_pedidos_productos FOREIGN KEY (id_producto) REFERENCES productos(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'pagos'
CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    fecha_pago TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    importe DECIMAL(10,2) NOT NULL,
    metodo_pago MetodoPagoEnum NOT NULL,
    CONSTRAINT fk_pagos_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pagos_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'facturas'
CREATE TABLE facturas (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    fecha_factura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    importe DECIMAL(10,2) NOT NULL,
    numero_factura VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT fk_facturas_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_facturas_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'historial_pedidos'
CREATE TABLE historial_pedidos (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    estado_anterior EstadoPedidoEnum NOT NULL,
    estado_nuevo EstadoPedidoEnum NOT NULL,
    id_resp_modif INTEGER NOT NULL,
    fecha_modif TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_pedidos_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_historial_pedidos_usuarios_modif FOREIGN KEY (id_resp_modif) REFERENCES usuarios(id)
);

-- Crear tabla 'historial_compras'
CREATE TABLE historial_compras (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_cliente INTEGER NOT NULL,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    importe DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_historial_compras_pedidos FOREIGN KEY (id_pedido) REFERENCES pedidos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_historial_compras_clientes FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Crear tabla 'empleados'
CREATE TABLE empleados (
    id SERIAL PRIMARY KEY,
    id_empresa INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    apellidos VARCHAR(150),
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    fecha_nacimiento DATE,
    genero GeneroEnum,
    id_resp_alta INTEGER NOT NULL,
    fecha_alta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_resp_modif INTEGER,
    fecha_modif TIMESTAMP,
    id_resp_baja INTEGER,
    fecha_baja TIMESTAMP,
    CONSTRAINT fk_empleados_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_empleados_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_empleados_usuarios_alta FOREIGN KEY (id_resp_alta) REFERENCES usuarios(id),
    CONSTRAINT fk_empleados_usuarios_modif FOREIGN KEY (id_resp_modif) REFERENCES usuarios(id),
    CONSTRAINT fk_empleados_usuarios_baja FOREIGN KEY (id_resp_baja) REFERENCES usuarios(id)
);