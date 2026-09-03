-- V3: deja SOLO EVO (elimina public.users/roles y evo.app_*, crea evo.users/roles)
DROP TABLE IF EXISTS public.roles;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS evo.app_user_role;
DROP TABLE IF EXISTS evo.app_user_sociedad;
DROP TABLE IF EXISTS evo.app_role;
DROP TABLE IF EXISTS evo.app_user;

CREATE TABLE IF NOT EXISTS evo.users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR(200) NOT NULL,
  enabled BOOLEAN DEFAULT true
);
CREATE TABLE IF NOT EXISTS evo.roles (
  id BIGSERIAL PRIMARY KEY,
  rol VARCHAR(50) NOT NULL,
  user_id BIGINT NOT NULL REFERENCES evo.users(id) ON DELETE CASCADE,
  UNIQUE(user_id, rol)
);

-- V1 ya crea cod_sociedad VARCHAR(10); si vienes de BD vieja con VARCHAR(3):
-- DROP VIEW IF EXISTS evo.v_requisicion_detalle;
-- ALTER TABLE evo.mmrequis_det ALTER COLUMN cod_sociedad TYPE VARCHAR(10);

-- Seed usuarios con BCrypt real: master/master123 , admin/admin123
INSERT INTO evo.users(username, password, enabled) VALUES
('master','$2b$10$N6hOF7tVRH8fBVg0dzSYe.3jWqh8oVSysxP7pxNyF5WGcBELOQrL.',true),
('admin','$2b$10$KuSw.83//3rbPN1lvmTYMOMC8u/d5ER9agSHOAkv.70usjAjH.xS.',true)
ON CONFLICT (username) DO UPDATE SET password=EXCLUDED.password, enabled=true;
INSERT INTO evo.roles(rol, user_id) SELECT 'ADMIN', id FROM evo.users WHERE username IN ('master','admin')
ON CONFLICT DO NOTHING;
