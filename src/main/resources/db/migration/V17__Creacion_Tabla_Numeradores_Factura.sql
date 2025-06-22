-- Crear tabla 'numeradores_factura'

CREATE TABLE numeradores_factura (
    id SERIAL PRIMARY KEY,
    "year" INTEGER NOT NULL,
    ultimo_numero INTEGER NOT NULL,
    id_empresa INTEGER NOT NULL,
    CONSTRAINT fk_numeradores_factura_empresas FOREIGN KEY (id_empresa) REFERENCES empresas(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_year_id_empresa UNIQUE ("year", id_empresa)
);