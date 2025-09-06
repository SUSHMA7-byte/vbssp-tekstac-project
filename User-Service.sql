create database vehicle_platform_user_db;
use vehicle_platform_user_db;

select * from users;
select * from roles;
select * from user_roles;

INSERT INTO roles (role_name) VALUES ('ROLE_USER');
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');

desc users;
-- CREATE TABLE users (
--     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     full_name VARCHAR(100) NOT NULL,
--     email VARCHAR(150) NOT NULL UNIQUE,
--     password_hash VARCHAR(255) NOT NULL,
--     phone_number VARCHAR(15),
--     status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED') DEFAULT 'ACTIVE',
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
-- );

-- CREATE TABLE roles (
--     role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     role_name VARCHAR(50) NOT NULL UNIQUE, -- ADMIN, BUYER, SELLER, SERVICE_PROVIDER, MECHANIC
-- );

-- CREATE TABLE user_roles (
--     user_id BIGINT NOT NULL,
--     role_id BIGINT NOT NULL,
--     assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     PRIMARY KEY (user_id, role_id),
--     FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
--     FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
-- );
