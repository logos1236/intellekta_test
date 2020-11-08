INSERT INTO product (name) VALUES ('car_test');
INSERT INTO product (name) VALUES ('car_test');
INSERT INTO product (name) VALUES ('bike_test');

INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (1, 100, '2020-05-01', '2020-05-04', 1);
INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (2, 100, '2020-05-06', '2020-05-08', 1);
INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (3, 50, '2020-05-01', '2020-05-07', 2);
INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (4, 60, '2020-05-08', null, 2);
INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (5, 120, '2020-05-09', '2020-05-13', 1);
INSERT INTO sales_period (id, price, date_from, date_to, product) VALUES (6, 2000, '2020-03-31', '2020-04-04', 1);
