CREATE DATABASE spring_microservice;

USE spring_microservice;

CREATE TABLE client
(
    id            INT          NOT NULL AUTO_INCREMENT,
    client_name   VARCHAR(100) NOT NULL ,
    client_id     VARCHAR(100) NOT NULL,
    client_secret VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled  BOOLEAN      NOT NULL
);

CREATE TABLE authorities
(
    id        INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (id) REFERENCES users (id)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

INSERT INTO users(username, password, enabled) VALUES ('admin', '$2a$12$aj.szKkn.Uf/kCCtJ1.yFeugEuaK5VFyUGQ.qaruynphVwtOtcEwe', 1);
# password admin
INSERT INTO authorities(username, authority) VALUES ('admin', 'ADMIN');

