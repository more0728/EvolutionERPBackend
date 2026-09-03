-- ============================================
-- EvolutionERP - Modulo Requisicion de Compras
-- Base de datos PostgreSQL 18
-- Generado: 2026-09-02
-- ============================================



-- Flyway gestiona el versionado: no borrar schema en migraciones
CREATE SCHEMA IF NOT EXISTS evo;
SET search_path TO evo, public;

-- ============================================
-- 01. ESOCIEDAD (Maestro Empresa)
-- ============================================
CREATE TABLE evo.esociedad (
  cod_sociedad   VARCHAR(10) PRIMARY KEY,
  nom_sociedad   VARCHAR(100) NOT NULL,
  nit_sociedad   VARCHAR(11) NOT NULL,
  id_pais        VARCHAR(2) DEFAULT 'PE',
  id_idioma      VARCHAR(2) DEFAULT 'ES',
  nom_comercial  VARCHAR(100),
  opc_mant       VARCHAR(12) DEFAULT 'ACTIVO',
  user_sis       VARCHAR(12) NOT NULL,
  user_mod       VARCHAR(12) NOT NULL,
  user_sis_date  TIMESTAMP NOT NULL DEFAULT now()
);
COMMENT ON TABLE evo.esociedad IS 'Maestro de sociedades/empresas - Intercorp Retail';
CREATE INDEX idx_esociedad_nit ON evo.esociedad(nit_sociedad);

-- ============================================
-- 02. ECCOSTO (Centro de Costos)
-- ============================================
CREATE TABLE evo.eccosto (
  cod_sociedad VARCHAR(10) NOT NULL REFERENCES evo.esociedad(cod_sociedad),
  ccod_cencos  VARCHAR(10) NOT NULL,
  nom_cencos   VARCHAR(100) NOT NULL,
  opc_mant     VARCHAR(12) DEFAULT 'ACTIVO',
  PRIMARY KEY (cod_sociedad, ccod_cencos)
);
COMMENT ON TABLE evo.eccosto IS 'Centro de costos - Ej: 03 - ATOCONGO REGIMEN COMUN';
CREATE INDEX idx_eccosto_nom ON evo.eccosto(nom_cencos);

-- ============================================
-- 03. ECONSTANTES (Tabla parametrica)
--    app=PRIO -> 001 NORMAL, 002 URGENTE, 003 EMERGENCIA
--    app=EST  -> estados, app=COM -> modulo
-- ============================================
CREATE TABLE evo.econstantes (
  cod_sociedad   VARCHAR(10) NOT NULL REFERENCES evo.esociedad(cod_sociedad),
  cvalor         VARCHAR(10) NOT NULL,
  cnom_valor     VARCHAR(100) NOT NULL,
  app            VARCHAR(4) NOT NULL,
  opc_mant       VARCHAR(12) DEFAULT 'ACTIVO',
  user_sis       VARCHAR(12) NOT NULL,
  user_mod       VARCHAR(12) NOT NULL,
  user_sis_date  TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (cod_sociedad, cvalor, app)
);
COMMENT ON TABLE evo.econstantes IS 'Parametrica: prioridades, estados, etc.';

-- ============================================
-- 04. ENUMRANGOS (Control correlativo)
-- ============================================
CREATE TABLE evo.enumrangos (
  cod_sociedad VARCHAR(10) NOT NULL REFERENCES evo.esociedad(cod_sociedad),
  app          VARCHAR(4) NOT NULL DEFAULT 'COM',
  ultimo_num   INTEGER NOT NULL DEFAULT 0,
  nro_doc      VARCHAR(12) GENERATED ALWAYS AS (LPAD(ultimo_num::TEXT,12,'0')) STORED,
  PRIMARY KEY (cod_sociedad, app)
);
COMMENT ON TABLE evo.enumrangos IS 'Control de rangos y correlativos secuenciales';

-- ============================================
-- 05. EPERSONAL (inferida - Lista personal)
-- ============================================
CREATE TABLE evo.epersonal (
  ccod_person VARCHAR(12) PRIMARY KEY,
  cod_sociedad VARCHAR(10) REFERENCES evo.esociedad(cod_sociedad),
  nom_person   VARCHAR(100) NOT NULL,
  opc_mant     VARCHAR(12) DEFAULT 'ACTIVO'
);
CREATE INDEX idx_epersonal_nom ON evo.epersonal(nom_person);

-- ============================================
-- 06. BPROVEEDOR (inferida - Business Partner)
-- ============================================
CREATE TABLE evo.bproveedor (
  ccod_proveedor VARCHAR(12) PRIMARY KEY,
  nom_prov       VARCHAR(150) NOT NULL,
  ruc            VARCHAR(11),
  opc_mant       VARCHAR(12) DEFAULT 'ACTIVO'
);
CREATE INDEX idx_bproveedor_nom ON evo.bproveedor(nom_prov);

-- ============================================
-- 07. MMATERIAL (inferida - Catalogo articulos)
-- ============================================
CREATE TABLE evo.mmaterial (
  cod_material  VARCHAR(42) PRIMARY KEY,
  nom_material  VARCHAR(200) NOT NULL,
  c_unidad      VARCHAR(10) NOT NULL,
  opc_mant      VARCHAR(12) DEFAULT 'ACTIVO'
);
CREATE INDEX idx_mmaterial_nom ON evo.mmaterial(nom_material);

-- ============================================
-- 08. MMREQUIS_CAB (Cabecera requisicion)
-- ============================================
CREATE TABLE evo.mmrequis_cab (
  cod_sociedad    VARCHAR(10) NOT NULL REFERENCES evo.esociedad(cod_sociedad),
  nro_doc         VARCHAR(12) NOT NULL,
  fec_doc         TIMESTAMP NOT NULL DEFAULT now(),
  fec_req         TIMESTAMP NOT NULL,
  ccod_cencos     VARCHAR(10) NOT NULL,
  ccod_person     VARCHAR(12) REFERENCES evo.epersonal(ccod_person),
  lugar_entr      VARCHAR(200),
  ccod_proveedor  VARCHAR(12) REFERENCES evo.bproveedor(ccod_proveedor),
  tip_prio        VARCHAR(10), -- FK a econstantes(cvalor) donde app=PRIO
  observ          VARCHAR(400),
  estado          VARCHAR(12) DEFAULT 'PENDIENTE',
  nota_entrada    VARCHAR(4),
  condic          CHAR(1) DEFAULT 'A',
  opc_mant        VARCHAR(12) DEFAULT 'ACTIVO',
  app             VARCHAR(4) NOT NULL DEFAULT 'COM',
  user_sis        VARCHAR(12) NOT NULL,
  user_mod        VARCHAR(12) NOT NULL,
  user_sis_date   TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY (cod_sociedad, nro_doc),
  FOREIGN KEY (cod_sociedad, ccod_cencos) REFERENCES evo.eccosto(cod_sociedad, ccod_cencos),
  CONSTRAINT chk_estado CHECK (estado IN ('PENDIENTE','MODIFICANDO','APROBADO','ANULADO','CERRADO')),
  CONSTRAINT chk_prio CHECK (tip_prio IN ('001','002','003'))
);
CREATE INDEX idx_cab_fec_doc ON evo.mmrequis_cab(fec_doc);
CREATE INDEX idx_cab_fec_req ON evo.mmrequis_cab(fec_req);
CREATE INDEX idx_cab_estado ON evo.mmrequis_cab(estado);
CREATE INDEX idx_cab_cencos ON evo.mmrequis_cab(cod_sociedad, ccod_cencos);
CREATE INDEX idx_cab_person ON evo.mmrequis_cab(ccod_person);

-- ============================================
-- 09. MMREQUIS_DET (Detalle requisicion)
--     Filas "*" del mock se guardan en observ
-- ============================================
CREATE TABLE evo.mmrequis_det (
  cod_sociedad        VARCHAR(10) NOT NULL,
  nro_item            NUMERIC(18,0) NOT NULL,
  nro_doc             VARCHAR(12) NOT NULL,
  cod_material        VARCHAR(42) REFERENCES evo.mmaterial(cod_material),
  c_unidad            VARCHAR(10) NOT NULL,
  cantid              NUMERIC(18,4) NOT NULL CHECK (cantid >= 0),
  ncantidad_recibida  NUMERIC(18,4) NOT NULL DEFAULT 0 CHECK (ncantidad_recibida >= 0),
  observ              VARCHAR(400),
  ccod_proveedor      VARCHAR(12) REFERENCES evo.bproveedor(ccod_proveedor),
  estado              VARCHAR(12) DEFAULT 'PENDIENTE',
  opc_mant            VARCHAR(12) DEFAULT 'ACTIVO',
  PRIMARY KEY (cod_sociedad, nro_doc, nro_item),
  FOREIGN KEY (cod_sociedad, nro_doc) REFERENCES evo.mmrequis_cab(cod_sociedad, nro_doc) ON DELETE CASCADE
);
CREATE INDEX idx_det_material ON evo.mmrequis_det(cod_material);
CREATE INDEX idx_det_estado ON evo.mmrequis_det(estado);

-- ============================================
-- 10. SEGURIDAD (Login + seleccion sociedad) - SOLO EVO
--     Arquitectura_BackEnd.pdf: User/Role/Menu
-- ============================================
CREATE TABLE evo.users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR(200) NOT NULL,
  enabled BOOLEAN DEFAULT true
);
CREATE TABLE evo.roles (
  id BIGSERIAL PRIMARY KEY,
  rol VARCHAR(50) NOT NULL,
  user_id BIGINT NOT NULL REFERENCES evo.users(id) ON DELETE CASCADE,
  UNIQUE(user_id, rol)
);

-- ============================================
-- TRIGGERS: Mayusculas + Auditoria (Req 6)
-- ============================================
CREATE OR REPLACE FUNCTION evo.trg_upper_cab() RETURNS TRIGGER AS $$
BEGIN
  NEW.lugar_entr := UPPER(NEW.lugar_entr);
  NEW.observ := UPPER(NEW.observ);
  NEW.estado := UPPER(NEW.estado);
  NEW.opc_mant := UPPER(NEW.opc_mant);
  NEW.app := UPPER(NEW.app);
  NEW.user_sis := UPPER(NEW.user_sis);
  NEW.user_mod := UPPER(NEW.user_mod);
  NEW.user_sis_date := COALESCE(NEW.user_sis_date, now());
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION evo.trg_upper_det() RETURNS TRIGGER AS $$
BEGIN
  NEW.observ := UPPER(NEW.observ);
  NEW.estado := UPPER(NEW.estado);
  NEW.opc_mant := UPPER(NEW.opc_mant);
  NEW.c_unidad := UPPER(NEW.c_unidad);
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION evo.trg_upper_generic() RETURNS TRIGGER AS $$
BEGIN
  IF TG_TABLE_NAME = 'esociedad' THEN
    NEW.nom_sociedad := UPPER(NEW.nom_sociedad); NEW.nom_comercial := UPPER(NEW.nom_comercial);
  ELSIF TG_TABLE_NAME = 'eccosto' THEN
    NEW.nom_cencos := UPPER(NEW.nom_cencos);
  ELSIF TG_TABLE_NAME = 'econstantes' THEN
    NEW.cnom_valor := UPPER(NEW.cnom_valor); NEW.app := UPPER(NEW.app);
  ELSIF TG_TABLE_NAME = 'epersonal' THEN
    NEW.nom_person := UPPER(NEW.nom_person);
  ELSIF TG_TABLE_NAME = 'bproveedor' THEN
    NEW.nom_prov := UPPER(NEW.nom_prov);
  ELSIF TG_TABLE_NAME = 'mmaterial' THEN
    NEW.nom_material := UPPER(NEW.nom_material); NEW.c_unidad := UPPER(NEW.c_unidad);
  END IF;
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_cab_upper ON evo.mmrequis_cab;
CREATE TRIGGER trg_cab_upper BEFORE INSERT OR UPDATE ON evo.mmrequis_cab FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_cab();

DROP TRIGGER IF EXISTS trg_det_upper ON evo.mmrequis_det;
CREATE TRIGGER trg_det_upper BEFORE INSERT OR UPDATE ON evo.mmrequis_det FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_det();

DROP TRIGGER IF EXISTS trg_esoc_upper ON evo.esociedad;
CREATE TRIGGER trg_esoc_upper BEFORE INSERT OR UPDATE ON evo.esociedad FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();
DROP TRIGGER IF EXISTS trg_eccosto_upper ON evo.eccosto;
CREATE TRIGGER trg_eccosto_upper BEFORE INSERT OR UPDATE ON evo.eccosto FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();
DROP TRIGGER IF EXISTS trg_econst_upper ON evo.econstantes;
CREATE TRIGGER trg_econst_upper BEFORE INSERT OR UPDATE ON evo.econstantes FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();
DROP TRIGGER IF EXISTS trg_epers_upper ON evo.epersonal;
CREATE TRIGGER trg_epers_upper BEFORE INSERT OR UPDATE ON evo.epersonal FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();
DROP TRIGGER IF EXISTS trg_prov_upper ON evo.bproveedor;
CREATE TRIGGER trg_prov_upper BEFORE INSERT OR UPDATE ON evo.bproveedor FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();
DROP TRIGGER IF EXISTS trg_mat_upper ON evo.mmaterial;
CREATE TRIGGER trg_mat_upper BEFORE INSERT OR UPDATE ON evo.mmaterial FOR EACH ROW EXECUTE FUNCTION evo.trg_upper_generic();

-- ============================================
-- FUNCION CORRELATIVO SECUENCIAL (Req 6.1)
-- ============================================
CREATE OR REPLACE FUNCTION evo.next_correlativo(p_soc VARCHAR(10), p_app VARCHAR(4) DEFAULT 'COM')
RETURNS VARCHAR(12) LANGUAGE plpgsql AS $$
DECLARE v INT;
BEGIN
  -- Bloqueo pesimista para evitar duplicados concurrentes
  SELECT ultimo_num INTO v FROM evo.enumrangos WHERE cod_sociedad=p_soc AND app=p_app FOR UPDATE;
  IF NOT FOUND THEN
    INSERT INTO evo.enumrangos(cod_sociedad, app, ultimo_num) VALUES (p_soc, p_app, 1) RETURNING ultimo_num INTO v;
  ELSE
    UPDATE evo.enumrangos SET ultimo_num = ultimo_num + 1 WHERE cod_sociedad=p_soc AND app=p_app RETURNING ultimo_num INTO v;
  END IF;
  RETURN LPAD(v::TEXT, 12, '0');
END; $$;
COMMENT ON FUNCTION evo.next_correlativo IS 'Genera correlativo secuencial thread-safe. Uso: next_correlativo(''100'',''COM'')';

-- ============================================
-- STORED PROCEDURES / FUNCIONES PARA LISTAS (Req 7)
-- ============================================

-- Lista1: List_ECECOS (centros de costo)
CREATE OR REPLACE FUNCTION evo.sp_list_eccosto(p_soc VARCHAR(10))
RETURNS TABLE(ccod_cencos VARCHAR(10), nom_cencos VARCHAR(100)) LANGUAGE sql AS $$
  SELECT ccod_cencos, nom_cencos FROM evo.eccosto WHERE cod_sociedad=p_soc AND opc_mant='ACTIVO' ORDER BY nom_cencos;
$$;

-- Lista2: personal
CREATE OR REPLACE FUNCTION evo.sp_list_personal(p_soc VARCHAR(10))
RETURNS TABLE(ccod_person VARCHAR(12), nom_person VARCHAR(100)) LANGUAGE sql AS $$
  SELECT ccod_person, nom_person FROM evo.epersonal WHERE cod_sociedad=p_soc AND opc_mant='ACTIVO' ORDER BY nom_person;
$$;

-- Lista3: proveedores
CREATE OR REPLACE FUNCTION evo.sp_list_proveedores(p_q TEXT DEFAULT NULL)
RETURNS TABLE(ccod_proveedor VARCHAR(12), nom_prov VARCHAR(150)) LANGUAGE sql AS $$
  SELECT ccod_proveedor, nom_prov FROM evo.bproveedor WHERE opc_mant='ACTIVO'
  AND (p_q IS NULL OR UPPER(nom_prov) LIKE '%'||UPPER(p_q)||'%' OR ccod_proveedor LIKE p_q||'%' )
  ORDER BY nom_prov LIMIT 50;
$$;

-- Lista prioridades (ECONSTANTES app=PRIO)
CREATE OR REPLACE FUNCTION evo.sp_list_prioridades(p_soc VARCHAR(10))
RETURNS TABLE(cvalor VARCHAR(10), cnom_valor VARCHAR(100)) LANGUAGE sql AS $$
  SELECT cvalor, cnom_valor FROM evo.econstantes WHERE cod_sociedad=p_soc AND app='PRIO' AND opc_mant='ACTIVO' ORDER BY cvalor;
$$;

-- Buscar materiales (modal añadir articulo)
CREATE OR REPLACE FUNCTION evo.sp_search_material(p_q TEXT)
RETURNS TABLE(cod_material VARCHAR(42), nom_material VARCHAR(200), c_unidad VARCHAR(10)) LANGUAGE sql AS $$
  SELECT cod_material, nom_material, c_unidad FROM evo.mmaterial WHERE opc_mant='ACTIVO'
  AND (UPPER(nom_material) LIKE '%'||UPPER(p_q)||'%' OR cod_material LIKE p_q||'%') LIMIT 20;
$$;

-- Historial paginado + filtros (Front 5_FRONT_-FINAL)
CREATE OR REPLACE FUNCTION evo.sp_list_requisiciones(
  p_soc VARCHAR(10), p_estado VARCHAR(12) DEFAULT NULL,
  p_cencos VARCHAR(10) DEFAULT NULL, p_person VARCHAR(12) DEFAULT NULL,
  p_fec_ini DATE DEFAULT NULL, p_fec_fin DATE DEFAULT NULL,
  p_page INT DEFAULT 1, p_size INT DEFAULT 20
)
RETURNS TABLE(cod_sociedad VARCHAR(10), nro_doc VARCHAR(12), fec_doc TIMESTAMP, fec_req TIMESTAMP, ccod_cencos VARCHAR(10), nom_cencos VARCHAR(100), tip_prio VARCHAR(10), estado VARCHAR(12), total_items BIGINT) LANGUAGE sql AS $$
  SELECT c.cod_sociedad, c.nro_doc, c.fec_doc, c.fec_req, c.ccod_cencos, e.nom_cencos, c.tip_prio, c.estado, COUNT(d.nro_item)::BIGINT
  FROM evo.mmrequis_cab c
  LEFT JOIN evo.eccosto e ON e.cod_sociedad=c.cod_sociedad AND e.ccod_cencos=c.ccod_cencos
  LEFT JOIN evo.mmrequis_det d ON d.cod_sociedad=c.cod_sociedad AND d.nro_doc=c.nro_doc
  WHERE c.cod_sociedad=p_soc
    AND (p_estado IS NULL OR c.estado=p_estado)
    AND (p_cencos IS NULL OR c.ccod_cencos=p_cencos)
    AND (p_person IS NULL OR c.ccod_person=p_person)
    AND (p_fec_ini IS NULL OR c.fec_doc::DATE >= p_fec_ini)
    AND (p_fec_fin IS NULL OR c.fec_doc::DATE <= p_fec_fin)
  GROUP BY c.cod_sociedad, c.nro_doc, e.nom_cencos
  ORDER BY c.fec_doc DESC LIMIT p_size OFFSET (p_page-1)*p_size;
$$;

-- Vista detalle completo (para el UPDATE/READ)
CREATE OR REPLACE VIEW evo.v_requisicion_detalle AS
SELECT c.cod_sociedad, c.nro_doc, c.fec_doc, c.fec_req, c.ccod_cencos, ec.nom_cencos,
       c.ccod_person, ep.nom_person, c.lugar_entr, c.ccod_proveedor, bp.nom_prov,
       c.tip_prio, et.cnom_valor as nom_prio, c.observ as observ_cab, c.estado, c.user_sis, c.user_mod,
       d.nro_item, d.cod_material, m.nom_material, d.c_unidad, d.cantid, d.ncantidad_recibida, d.observ as observ_det
FROM evo.mmrequis_cab c
JOIN evo.eccosto ec ON ec.cod_sociedad=c.cod_sociedad AND ec.ccod_cencos=c.ccod_cencos
LEFT JOIN evo.epersonal ep ON ep.ccod_person=c.ccod_person
LEFT JOIN evo.bproveedor bp ON bp.ccod_proveedor=c.ccod_proveedor
LEFT JOIN evo.econstantes et ON et.cod_sociedad=c.cod_sociedad AND et.cvalor=c.tip_prio AND et.app='PRIO'
LEFT JOIN evo.mmrequis_det d ON d.cod_sociedad=c.cod_sociedad AND d.nro_doc=c.nro_doc
LEFT JOIN evo.mmaterial m ON m.cod_material=d.cod_material;


-- NOTA: seguridad vive SOLO en evo.users / evo.roles (sin tablas en public).
