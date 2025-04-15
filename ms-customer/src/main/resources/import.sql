-- Inserir 10 registros fictícios na tabela Customer
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES(true, '1990-05-15',  '123.456.789-00', 'ana.silva@example.com', 'Ana', 'Silva', 'pass123', 'FEMALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1985-08-22', '987.654.321-00', 'carlos.oliveira@example.com', 'Carlos', 'Oliveira', 'pass456', 'MALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1992-11-03', '456.123.789-00', 'mariana.santos@example.com', 'Mariana', 'Santos', 'pass789', 'FEMALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1988-06-12', '741.852.963-00', 'joao.pereira@example.com', 'João', 'Pereira', 'pass321', 'MALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1995-01-25', '852.963.741-00', 'fernanda.costa@example.com', 'Fernanda', 'Costa', 'pass654', 'FEMALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1997-09-18', '369.258.147-00', 'lucas.martins@example.com', 'Lucas', 'Martins', 'pass987', 'NON_BINARY');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1993-04-05', '258.147.369-00', 'bruna.almeida@example.com', 'Bruna', 'Almeida', 'pass159', 'FEMALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1989-12-08', '147.369.258-00', 'ricardo.lima@example.com', 'Ricardo', 'Lima', 'pass753', 'MALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1994-07-30', '963.741.852-00', 'paula.ferreira@example.com', 'Paula', 'Ferreira', 'pass951', 'FEMALE');
INSERT INTO customer (active, birthdate, cpf, email, first_name, last_name, password, gender) VALUES (true, '1996-03-11', '159.753.456-00', 'gabriel.rocha@example.com', 'Gabriel', 'Rocha', 'pass357', 'NON_BINARY');

-- Inserir 15 registros fictícios na tabela Address
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('SP', 'São Paulo', 'Centro', 'Rua A', '100', '01000-000', 'Apto 101', 1);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RJ', 'Rio de Janeiro', 'Copacabana', 'Rua B', '200', '22000-000', 'Bloco 2', 2);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('MG', 'Belo Horizonte', 'Savassi', 'Rua C', '300', '30000-000', NULL, 3);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RS', 'Porto Alegre', 'Moinhos', 'Rua D', '400', '90000-000', 'Casa 1', 4);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('PR', 'Curitiba', 'Centro', 'Rua E', '500', '80000-000', NULL, 5);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('SP', 'São Paulo', 'Moema', 'Rua F', '600', '04500-000', 'Apto 202', 6);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RJ', 'Rio de Janeiro', 'Ipanema', 'Rua G', '700', '22400-000', NULL, 7);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('MG', 'Belo Horizonte', 'Lourdes', 'Rua H', '800', '30100-000', 'Cobertura', 8);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RS', 'Porto Alegre', 'Centro', 'Rua I', '900', '90100-000', NULL, 9);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('PR', 'Curitiba', 'Batel', 'Rua J', '1000', '80200-000', 'Sala 5', 10);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('SP', 'São Paulo', 'Jardins', 'Rua K', '1100', '01400-000', NULL, 1);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RJ', 'Rio de Janeiro', 'Leblon', 'Rua L', '1200', '22450-000', 'Bloco A', 2);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('MG', 'Belo Horizonte', 'Funcionários', 'Rua M', '1300', '30200-000', NULL, 3);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('RS', 'Porto Alegre', 'Petrópolis', 'Rua N', '1400', '90400-000', NULL, 4);
INSERT INTO address (state, city, district, street, number, zipcode, complement, customer_id) VALUES ('PR', 'Curitiba', 'Água Verde', 'Rua O', '1500', '80620-000', 'Casa 3', 5);
