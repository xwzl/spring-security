DROP DATABASE
IF
	EXISTS product_master;
CREATE DATABASE product_master;
CREATE TABLE product_master.product ( id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR ( 50 ) NOT NULL, price DOUBLE ( 10, 2 ) NOT NULL DEFAULT 0 );
INSERT INTO product_master.product ( NAME, price )
VALUES
	( 'master', '1' );

DROP DATABASE
IF
	EXISTS product_slave_alpha;
CREATE DATABASE product_slave_alpha;
CREATE TABLE product_slave_alpha.product ( id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR ( 50 ) NOT NULL, price DOUBLE ( 10, 2 ) NOT NULL DEFAULT 0 );
INSERT INTO product_slave_alpha.product ( NAME, price )
VALUES
	( 'slaveAlpha', '1' );

DROP DATABASE
IF
	EXISTS product_slave_beta;
CREATE DATABASE product_slave_beta;
CREATE TABLE product_slave_beta.product ( id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR ( 50 ) NOT NULL, price DOUBLE ( 10, 2 ) NOT NULL DEFAULT 0 );
INSERT INTO product_slave_beta.product ( NAME, price )
VALUES
	( 'slaveBeta', '1' );

DROP DATABASE
IF
	EXISTS product_slave_gamma;
CREATE DATABASE product_slave_gamma;
CREATE TABLE product_slave_gamma.product ( id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR ( 50 ) NOT NULL, price DOUBLE ( 10, 2 ) NOT NULL DEFAULT 0 );
INSERT INTO product_slave_gamma.product ( NAME, price )
VALUES
	( 'slaveGamma', '1' );