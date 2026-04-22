-- ╔══════════════════════════════════════════════════════════════════╗
-- ║   SISTEMA CONTABLE COOPERATIVO — Script de Base de Datos        ║
-- ║   Compatible con: MySQL 8+ / MariaDB 10.5+                      ║
-- ║   Norma: NIIF para PYMES + Ley Cooperativas El Salvador          ║
-- ╚══════════════════════════════════════════════════════════════════╝

-- ─────────────────────────────────────────
--  CREAR Y SELECCIONAR BASE DE DATOS
-- ─────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS cooperativa_contable
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE cooperativa_contable;

-- ─────────────────────────────────────────
--  TABLA: cuentas
--  Catálogo de cuentas contables (plan de cuentas NIIF-PYMES)
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS cuentas (
    id            INT           NOT NULL AUTO_INCREMENT,
    codigo        VARCHAR(20)   NOT NULL,
    nombre        VARCHAR(200)  NOT NULL,
    tipo          VARCHAR(20)   NOT NULL COMMENT 'Activo / Pasivo / Patrimonio / Ingreso / Gasto',
    naturaleza    VARCHAR(20)   NOT NULL COMMENT 'Deudora / Acreedora',
    clasificacion VARCHAR(20)   NOT NULL COMMENT 'Balance / Resultados',
    nivel         TINYINT       NOT NULL COMMENT '1=Grupo, 2=Subgrupo, 3=Cuenta, 4=Subcuenta',
    activa        TINYINT(1)    NOT NULL DEFAULT 1,
    creada_en     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT cuentas_pk         PRIMARY KEY (id),
    CONSTRAINT cuentas_codigo_uq  UNIQUE (codigo),
    CONSTRAINT cuentas_tipo_ck    CHECK (tipo IN ('Activo','Pasivo','Patrimonio','Ingreso','Gasto')),
    CONSTRAINT cuentas_nat_ck     CHECK (naturaleza IN ('Deudora','Acreedora')),
    CONSTRAINT cuentas_clas_ck    CHECK (clasificacion IN ('Balance','Resultados'))
) ENGINE=InnoDB COMMENT='Catálogo de cuentas — Plan de Cuentas Cooperativo';

-- ─────────────────────────────────────────
--  TABLA: periodos
--  Períodos contables (ej: Ejercicio 2025)
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS periodos (
    id            INT           NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100)  NOT NULL,
    fecha_inicio  DATE          NOT NULL,
    fecha_fin     DATE              NULL,
    estado        VARCHAR(10)   NOT NULL DEFAULT 'Abierto' COMMENT 'Abierto / Cerrado',
    observaciones TEXT              NULL,
    cerrado_en    DATETIME          NULL,
    CONSTRAINT periodos_pk      PRIMARY KEY (id),
    CONSTRAINT periodos_est_ck  CHECK (estado IN ('Abierto','Cerrado'))
) ENGINE=InnoDB COMMENT='Períodos contables';

-- ─────────────────────────────────────────
--  TABLA: asientos
--  Diario general de transacciones
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS asientos (
    id            INT           NOT NULL AUTO_INCREMENT,
    numero        VARCHAR(20)   NOT NULL,
    fecha         DATE          NOT NULL,
    concepto      VARCHAR(500)  NOT NULL,
    periodo_id    INT               NULL,
    tipo          VARCHAR(20)   NOT NULL DEFAULT 'Normal' COMMENT 'Normal / Apertura / Ajuste / Cierre',
    total_debe    DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    total_haber   DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    estado        VARCHAR(20)   NOT NULL DEFAULT 'Registrado',
    creado_en     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT asientos_pk          PRIMARY KEY (id),
    CONSTRAINT asientos_numero_uq   UNIQUE (numero),
    CONSTRAINT asientos_periodo_fk  FOREIGN KEY (periodo_id)
        REFERENCES periodos(id) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT asientos_tipo_ck     CHECK (tipo IN ('Normal','Apertura','Ajuste','Cierre'))
) ENGINE=InnoDB COMMENT='Diario general de asientos contables';

-- ─────────────────────────────────────────
--  TABLA: partidas
--  Líneas de debe/haber de cada asiento
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS partidas (
    id            INT           NOT NULL AUTO_INCREMENT,
    asiento_id    INT           NOT NULL,
    cuenta_id     INT           NOT NULL,
    debe          DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    haber         DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT partidas_pk          PRIMARY KEY (id),
    CONSTRAINT partidas_asiento_fk  FOREIGN KEY (asiento_id)
        REFERENCES asientos(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT partidas_cuenta_fk   FOREIGN KEY (cuenta_id)
        REFERENCES cuentas(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='Partidas (líneas) de cada asiento contable';

-- ─────────────────────────────────────────
--  TABLA: cierres
--  Historial de cierres de período
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS cierres (
    id               INT           NOT NULL AUTO_INCREMENT,
    periodo_id       INT               NULL,
    fecha_cierre     DATE          NOT NULL,
    excedente        DECIMAL(15,2) NOT NULL,
    total_ingresos   DECIMAL(15,2)     NULL,
    total_gastos     DECIMAL(15,2)     NULL,
    total_activos    DECIMAL(15,2)     NULL,
    total_pasivos    DECIMAL(15,2)     NULL,
    total_patrimonio DECIMAL(15,2)     NULL,
    observaciones    TEXT              NULL,
    ejecutado_en     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT cierres_pk         PRIMARY KEY (id),
    CONSTRAINT cierres_periodo_fk FOREIGN KEY (periodo_id)
        REFERENCES periodos(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB COMMENT='Historial de cierres de período contable';

-- ─────────────────────────────────────────
--  ÍNDICES de rendimiento
-- ─────────────────────────────────────────
CREATE INDEX idx_cuentas_tipo      ON cuentas  (tipo);
CREATE INDEX idx_cuentas_nivel     ON cuentas  (nivel);
CREATE INDEX idx_asientos_fecha    ON asientos (fecha);
CREATE INDEX idx_asientos_periodo  ON asientos (periodo_id);
CREATE INDEX idx_partidas_asiento  ON partidas (asiento_id);
CREATE INDEX idx_partidas_cuenta   ON partidas (cuenta_id);
CREATE INDEX idx_periodos_estado   ON periodos (estado);

-- ─────────────────────────────────────────
--  DATOS INICIALES: Catálogo de Cuentas
-- ─────────────────────────────────────────
INSERT INTO cuentas (codigo, nombre, tipo, naturaleza, clasificacion, nivel) VALUES
-- ── ACTIVO ──
('1',       'ACTIVO',                              'Activo',    'Deudora',   'Balance',    1),
('11',      'ACTIVO CORRIENTE',                    'Activo',    'Deudora',   'Balance',    2),
('1101',    'Caja General',                        'Activo',    'Deudora',   'Balance',    3),
('1102',    'Caja Chica',                          'Activo',    'Deudora',   'Balance',    3),
('1103',    'Bancos y Corresponsales',             'Activo',    'Deudora',   'Balance',    3),
('1103-01', 'Banco Agrícola - Cta. Corriente',     'Activo',    'Deudora',   'Balance',    4),
('1103-02', 'Banco de Fomento Cooperativo',        'Activo',    'Deudora',   'Balance',    4),
('1104',    'Inversiones Temporales',              'Activo',    'Deudora',   'Balance',    3),
('1105',    'Cartera de Créditos Vigente',         'Activo',    'Deudora',   'Balance',    3),
('1105-01', 'Créditos de Consumo',                'Activo',    'Deudora',   'Balance',    4),
('1105-02', 'Créditos Microempresa',              'Activo',    'Deudora',   'Balance',    4),
('1105-03', 'Créditos Agropecuarios',             'Activo',    'Deudora',   'Balance',    4),
('1106',    'Cartera de Créditos Vencida',         'Activo',    'Deudora',   'Balance',    3),
('1107',    'Provisión Créditos Incobrables',      'Activo',    'Acreedora', 'Balance',    3),
('1108',    'Cuentas por Cobrar Socios',           'Activo',    'Deudora',   'Balance',    3),
('1109',    'Intereses por Cobrar',                'Activo',    'Deudora',   'Balance',    3),
('1110',    'Inventarios de Suministros',          'Activo',    'Deudora',   'Balance',    3),
('1111',    'Gastos Pagados por Anticipado',       'Activo',    'Deudora',   'Balance',    3),
('12',      'ACTIVO NO CORRIENTE',                 'Activo',    'Deudora',   'Balance',    2),
('1201',    'Propiedades, Planta y Equipo',        'Activo',    'Deudora',   'Balance',    3),
('1201-01', 'Terrenos',                            'Activo',    'Deudora',   'Balance',    4),
('1201-02', 'Edificios e Instalaciones',           'Activo',    'Deudora',   'Balance',    4),
('1201-03', 'Mobiliario y Equipo de Oficina',      'Activo',    'Deudora',   'Balance',    4),
('1201-04', 'Equipo de Cómputo',                  'Activo',    'Deudora',   'Balance',    4),
('1201-05', 'Vehículos',                           'Activo',    'Deudora',   'Balance',    4),
('1202',    'Depreciación Acumulada',              'Activo',    'Acreedora', 'Balance',    3),
('1203',    'Intangibles - Software',              'Activo',    'Deudora',   'Balance',    3),
('1204',    'Inversiones a Largo Plazo',           'Activo',    'Deudora',   'Balance',    3),
-- ── PASIVO ──
('2',       'PASIVO',                              'Pasivo',    'Acreedora', 'Balance',    1),
('21',      'PASIVO CORRIENTE',                    'Pasivo',    'Acreedora', 'Balance',    2),
('2101',    'Depósitos de Ahorro de Socios',       'Pasivo',    'Acreedora', 'Balance',    3),
('2101-01', 'Ahorros a la Vista',                  'Pasivo',    'Acreedora', 'Balance',    4),
('2101-02', 'Depósitos a Plazo Fijo',              'Pasivo',    'Acreedora', 'Balance',    4),
('2102',    'Obligaciones Financieras C/P',        'Pasivo',    'Acreedora', 'Balance',    3),
('2103',    'Cuentas por Pagar Proveedores',       'Pasivo',    'Acreedora', 'Balance',    3),
('2104',    'Intereses por Pagar a Socios',        'Pasivo',    'Acreedora', 'Balance',    3),
('2105',    'Retenciones y Descuentos por Pagar',  'Pasivo',    'Acreedora', 'Balance',    3),
('2106',    'ISSS y AFP por Pagar',                'Pasivo',    'Acreedora', 'Balance',    3),
('2107',    'Impuestos por Pagar',                 'Pasivo',    'Acreedora', 'Balance',    3),
('2108',    'Gastos Acumulados por Pagar',         'Pasivo',    'Acreedora', 'Balance',    3),
('22',      'PASIVO NO CORRIENTE',                 'Pasivo',    'Acreedora', 'Balance',    2),
('2201',    'Obligaciones Financieras L/P',        'Pasivo',    'Acreedora', 'Balance',    3),
('2201-01', 'Préstamo Bandesal L/P',               'Pasivo',    'Acreedora', 'Balance',    4),
('2202',    'Previsiones y Provisiones',           'Pasivo',    'Acreedora', 'Balance',    3),
-- ── PATRIMONIO ──
('3',       'PATRIMONIO / CAPITAL SOCIAL',         'Patrimonio','Acreedora', 'Balance',    1),
('31',      'CAPITAL SOCIAL',                      'Patrimonio','Acreedora', 'Balance',    2),
('3101',    'Certificados de Aportación',          'Patrimonio','Acreedora', 'Balance',    3),
('3102',    'Capital Adicional',                   'Patrimonio','Acreedora', 'Balance',    3),
('32',      'RESERVAS',                            'Patrimonio','Acreedora', 'Balance',    2),
('3201',    'Fondo de Reserva Legal (10%)',         'Patrimonio','Acreedora', 'Balance',    3),
('3202',    'Fondo de Educación Cooperativa',      'Patrimonio','Acreedora', 'Balance',    3),
('3203',    'Fondo de Bienestar Social',           'Patrimonio','Acreedora', 'Balance',    3),
('33',      'RESULTADOS',                          'Patrimonio','Acreedora', 'Balance',    2),
('3301',    'Excedentes de Ejercicios Anteriores', 'Patrimonio','Acreedora', 'Balance',    3),
('3302',    'Déficit Acumulado',                   'Patrimonio','Deudora',   'Balance',    3),
('3303',    'Excedente / Déficit del Ejercicio',   'Patrimonio','Acreedora', 'Balance',    3),
-- ── INGRESOS ──
('4',       'INGRESOS',                            'Ingreso',   'Acreedora', 'Resultados', 1),
('41',      'INGRESOS FINANCIEROS',                'Ingreso',   'Acreedora', 'Resultados', 2),
('4101',    'Intereses Ganados en Créditos',       'Ingreso',   'Acreedora', 'Resultados', 3),
('4101-01', 'Int. Créditos de Consumo',            'Ingreso',   'Acreedora', 'Resultados', 4),
('4101-02', 'Int. Créditos Microempresa',          'Ingreso',   'Acreedora', 'Resultados', 4),
('4102',    'Comisiones e Ingresos por Servicios', 'Ingreso',   'Acreedora', 'Resultados', 3),
('4103',    'Ingresos por Inversiones',            'Ingreso',   'Acreedora', 'Resultados', 3),
('42',      'INGRESOS NO FINANCIEROS',             'Ingreso',   'Acreedora', 'Resultados', 2),
('4201',    'Cuotas de Membresía y Adhesión',      'Ingreso',   'Acreedora', 'Resultados', 3),
('4202',    'Donaciones Recibidas',                'Ingreso',   'Acreedora', 'Resultados', 3),
('4203',    'Otros Ingresos',                      'Ingreso',   'Acreedora', 'Resultados', 3),
-- ── GASTOS ──
('5',       'GASTOS',                              'Gasto',     'Deudora',   'Resultados', 1),
('51',      'GASTOS FINANCIEROS',                  'Gasto',     'Deudora',   'Resultados', 2),
('5101',    'Intereses Pagados a Socios (Ahorro)', 'Gasto',     'Deudora',   'Resultados', 3),
('5102',    'Intereses Pagados Oblig. Financieras','Gasto',     'Deudora',   'Resultados', 3),
('5103',    'Comisiones y Cargos Financieros',     'Gasto',     'Deudora',   'Resultados', 3),
('52',      'GASTOS ADMINISTRATIVOS',              'Gasto',     'Deudora',   'Resultados', 2),
('5201',    'Sueldos y Salarios',                  'Gasto',     'Deudora',   'Resultados', 3),
('5202',    'Prestaciones Laborales (ISSS, AFP)',  'Gasto',     'Deudora',   'Resultados', 3),
('5203',    'Honorarios Profesionales',            'Gasto',     'Deudora',   'Resultados', 3),
('5204',    'Alquiler de Instalaciones',           'Gasto',     'Deudora',   'Resultados', 3),
('5205',    'Servicios Básicos (Agua, Luz, Tel.)', 'Gasto',     'Deudora',   'Resultados', 3),
('5206',    'Depreciaciones del Período',          'Gasto',     'Deudora',   'Resultados', 3),
('5207',    'Amortización de Intangibles',         'Gasto',     'Deudora',   'Resultados', 3),
('5208',    'Gastos de Papelería y Útiles',        'Gasto',     'Deudora',   'Resultados', 3),
('5209',    'Gastos de Publicidad y Mercadeo',     'Gasto',     'Deudora',   'Resultados', 3),
('5210',    'Gastos de Transporte y Viáticos',     'Gasto',     'Deudora',   'Resultados', 3),
('53',      'GASTOS DE PREVISIÓN',                 'Gasto',     'Deudora',   'Resultados', 2),
('5301',    'Provisión para Créditos Incobrables', 'Gasto',     'Deudora',   'Resultados', 3),
('5302',    'Provisión para Indemnizaciones',      'Gasto',     'Deudora',   'Resultados', 3),
('54',      'GASTOS COOPERATIVOS',                 'Gasto',     'Deudora',   'Resultados', 2),
('5401',    'Gastos de Educación Cooperativa',     'Gasto',     'Deudora',   'Resultados', 3),
('5402',    'Gastos de Bienestar Social',          'Gasto',     'Deudora',   'Resultados', 3),
('5403',    'Contribuciones e Impuestos',          'Gasto',     'Deudora',   'Resultados', 3);

-- ─────────────────────────────────────────
--  DATO INICIAL: Período contable
-- ─────────────────────────────────────────
INSERT INTO periodos (nombre, fecha_inicio, estado)
VALUES (CONCAT('Ejercicio ', YEAR(CURDATE())), DATE_FORMAT(CURDATE(), '%Y-01-01'), 'Abierto');
