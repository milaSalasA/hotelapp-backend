# Para acceder a Base de Datos
Se Creó la Base de Datos en un servidor de Supabase, pero se puede crear localmente modificando la url de datasource en application.yaml

# Scripts para crear data en las tablas

-- Inserciones para un nuevo usuario
INSERT INTO role (id_Role, name, description)
VALUES (1, 'ADMIN', 'Administrador del sistema con acceso total');

INSERT INTO role (id_Role, name, description)
VALUES (2, 'RECEPCION', 'Personal de recepción con acceso limitado a reservas y clientes');

INSERT INTO user_role (id_user, id_role)
VALUES (101, 1);

INSERT INTO user_data (id_User, username, password, enabled)
VALUES (101, 'admin', 'generar bcrypt clave', TRUE);

UPDATE user_data
SET username = 'admin@gmail.com'
WHERE id_User = 101

-- Insertar los menús de la aplicación
INSERT INTO menu (id_menu, icon, name, url) VALUES
(1, 'dashboard',    'Dashboard',    '/pages/dashboard'),
(2, 'hotel',        'Habitaciones', '/pages/room-manager'),
(3, 'event',        'Reservas',     '/pages/reservation-manager'),
(4, 'bar_chart',    'Reportes',     '/pages/report');

-- Asociar menús al rol ADMIN
INSERT INTO menu_role (id_menu, id_role) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);

-- Asociar menús al rol RECEPCION
INSERT INTO menu_role (id_menu, id_role) VALUES
(1, 2),
(3, 2);

-- Crear fn_list():
CREATE OR REPLACE FUNCTION fn_list()
RETURNS TABLE (
quantity INTEGER,
consultdate TEXT
)
AS $$
BEGIN
RETURN QUERY
SELECT
COUNT(r.id_reservation)::INTEGER AS quantity,
TO_CHAR(r.check_in_date, 'YYYY-MM-DD') AS consultdate
FROM
reservation r
GROUP BY
TO_CHAR(r.check_in_date, 'YYYY-MM-DD')
ORDER BY
consultdate;
END;
$$
LANGUAGE plpgsql;
