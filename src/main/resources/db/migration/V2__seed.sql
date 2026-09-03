-- ============================================
-- SEED DATA - EvolutionERP
-- ============================================
SET search_path TO evo, public;

-- Sociedades (requiere existir antes de todo)
INSERT INTO evo.esociedad(cod_sociedad, nom_sociedad, nit_sociedad, id_pais, id_idioma, nom_comercial, user_sis, user_mod)
VALUES ('100','INTERCORP RETAIL','20600000001','PE','ES','INTERCORP','MASTER','MASTER'),
       ('A13','ATOCONGO REGIMEN COMUN','20600000002','PE','ES','ATOCONGO','MASTER','MASTER'),
       ('1100','LA REJA','30600000001','AR','ES','LA REJA','MASTER','MASTER')
ON CONFLICT DO NOTHING;

-- Centros de costo (ECCOSTO)
INSERT INTO evo.eccosto(cod_sociedad, ccod_cencos, nom_cencos) VALUES
('100','03','ATOCONGO REGIMEN COMUN'),
('A13','03','ATOCONGO REGIMEN COMUN'),
('1100','1100','LA REJA'),
('100','01','ADMINISTRACION'),
('100','02','LOGISTICA')
ON CONFLICT DO NOTHING;

-- ECONSTANTES - Prioridades
INSERT INTO evo.econstantes(cod_sociedad, cvalor, cnom_valor, app, user_sis, user_mod) VALUES
('100','001','NORMAL','PRIO','MASTER','MASTER'),
('100','002','URGENTE','PRIO','MASTER','MASTER'),
('100','003','EMERGENCIA','PRIO','MASTER','MASTER'),
('A13','001','NORMAL','PRIO','MASTER','MASTER'),
('A13','002','URGENTE','PRIO','MASTER','MASTER'),
('A13','003','EMERGENCIA','PRIO','MASTER','MASTER'),
('1100','001','NORMAL','PRIO','MASTER','MASTER'),
('1100','002','URGENTE','PRIO','MASTER','MASTER'),
('1100','003','EMERGENCIA','PRIO','MASTER','MASTER'),
-- Estados
('100','PEN','PENDIENTE','EST','MASTER','MASTER'),
('100','MOD','MODIFICANDO','EST','MASTER','MASTER'),
('100','ANU','ANULADO','EST','MASTER','MASTER')
ON CONFLICT DO NOTHING;

-- ENUMRANGOS
INSERT INTO evo.enumrangos(cod_sociedad, app, ultimo_num) VALUES
('100','COM',24), ('A13','COM',0), ('1100','COM',0)
ON CONFLICT (cod_sociedad, app) DO UPDATE SET ultimo_num=EXCLUDED.ultimo_num;

-- Personal
INSERT INTO evo.epersonal(ccod_person, cod_sociedad, nom_person) VALUES
('P00001','100','VALENCIA ROSARIO SATURNINO'),
('P00002','100','GARCIA PEREZ JUAN'),
('P00003','A13','VALENCIA ROSARIO SATURNINO')
ON CONFLICT DO NOTHING;

-- Proveedores
INSERT INTO evo.bproveedor(ccod_proveedor, nom_prov, ruc) VALUES
('16','ABRALIT ABRASIVOS INDUSTRIALES S.A.','20123456781'),
('00001','INDUSTRIAL SUPPLIES SA','20123456782'),
('00002','METALES GENERALES','20123456783'),
('00003','TAMBORES GULF','20123456784')
ON CONFLICT DO NOTHING;

-- Materiales
INSERT INTO evo.mmaterial(cod_material, nom_material, c_unidad) VALUES
('07001019','ABRAZADERA ALTA PRESION DE 1 1/2"','PZA'),
('07001012','ABRAZADERA ALTA PRESION DE 11/4"','PZA'),
('07001036','ABRAZADERA DE ESPARRAGO 1DIAM: LONG1','PZA'),
('3000000289','TAMBOR GULF NUEVO 205L','C/U'),
('3000000139','PALLET 1.20X1.20 -4 TIR. PARA LA EXP.','C/U'),
('3000000140','PALLET 1.20X1.00 M- TRATADOS','C/U'),
('3000000149','N.O.500 ACEITE BASE GI','L')
ON CONFLICT DO NOTHING;

-- Roles y usuarios (seguridad)
INSERT INTO evo.app_role(nom_role) VALUES ('ADMIN'),('COMPRAS'),('ALMACEN'),('CONSULTA') ON CONFLICT DO NOTHING;
INSERT INTO evo.app_user(username, password_hash, nom_user) VALUES
('master','$2a$10$fakehashmaster','MASTER'),
('admin','$2a$10$fakehashadmin','ADMINISTRADOR')
ON CONFLICT DO NOTHING;
INSERT INTO evo.app_user_role(username, id_role) VALUES ('master',1),('admin',1) ON CONFLICT DO NOTHING;
INSERT INTO evo.app_user_sociedad(username, cod_sociedad) VALUES ('master','100'),('master','A13'),('master','1100'),('admin','100') ON CONFLICT DO NOTHING;

-- Requisicion de ejemplo (Doc 0000000024 del mock)
INSERT INTO evo.mmrequis_cab(cod_sociedad, nro_doc, fec_doc, fec_req, ccod_cencos, ccod_person, lugar_entr, ccod_proveedor, tip_prio, observ, estado, user_sis, user_mod)
VALUES ('100','0000000024','2026-08-12 08:09:00','2026-08-12','03','P00001','ENTREGA EN ALMACEN LA MOLINA','16','002','LOS PRODUCTOS DEBEN SER ENTREGADOS LA SEMANA 25','MODIFICANDO','VALENCIA R','VALENCIA R')
ON CONFLICT DO NOTHING;

INSERT INTO evo.mmrequis_det(cod_sociedad, nro_doc, nro_item, cod_material, c_unidad, cantid, ncantidad_recibida, observ) VALUES
('100','0000000024',1,'07001019','PZA',50.00,0.00,NULL),
('100','0000000024',2,NULL,'',0.00,0.00,'DEBE SER ACERO INOXIDABLE'),
('100','0000000024',3,NULL,'',0.00,0.00,'ADEMAS 11 PULGADAS'),
('100','0000000024',4,NULL,'',0.00,0.00,'PRESION MILIMETRICA'),
('100','0000000024',5,'07001012','PZA',150.00,0.00,NULL),
('100','0000000024',6,NULL,'',0.00,0.00,'DEBE SER ACERO INOXIDABLE'),
('100','0000000024',7,NULL,'',0.00,0.00,'PRESION 90 GRADOS IN PRESION'),
('100','0000000024',8,'07001036','PZA',300.00,0.00,NULL)
ON CONFLICT DO NOTHING;

-- Actualizar correlativo
UPDATE evo.enumrangos SET ultimo_num=24 WHERE cod_sociedad='100' AND app='COM';
