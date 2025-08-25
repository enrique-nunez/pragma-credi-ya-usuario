CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    correo_electronico VARCHAR(150) UNIQUE NOT NULL,
    salario_base DECIMAL(15,2),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);