# Revisão de OO e SQL

## Nesta aula vamos revisar
- Chave primária, chave estrangeira
- DDL (create table, alter table)
- SQL
  - INSERT
  - SELECT
  - INNER JOIN
- Classes e objetos
- Encapsulamento, get/set
- Tipos enumerados
- Composição de objetos
- Coleções (list, map)
- Acessar dados em BD relacional e instanciar objetos correspondentes

> [!NOTE]
> Link da aula: [https://www.youtube.com/watch?v=xC_yKw3MYX4](https://www.youtube.com/watch?v=xC_yKw3MYX4)

## Pré-requisitos
- Git
- JDK
- STS (ou outra IDE)
- MySQL Server ou XAMPP
- MySQL Workbench ou phpMyAdmin

## Criação e instanciação da base de dados
```sql
create database if not exists dsdeliver;
use dsdeliver;

create table if not exists tb_order (
    id int auto_increment, 
    latitude float, 
    longitude float, 
    moment datetime, 
    status int, 
    primary key (id)
);

create table if not exists tb_order_product (
    order_id int not null, 
    product_id int not null, 
    primary key (order_id, product_id)
);

create table if not exists tb_product (
    id int auto_increment, 
    description TEXT, 
    image_uri varchar(255), 
    name varchar(255), 
    price float, 
    primary key (id)
);

alter table tb_order_product
	add constraint fk_tb_order_product_tb_product foreign key (product_id) references tb_product(id);

alter table tb_order_product
	add constraint fk_tb_order_product_tb_order foreign key (order_id) references tb_order(id);

INSERT INTO
	tb_product (name, price, image_Uri, description)
VALUES
	('Pizza de Calabresa', 50.0, 'https://github.com/devsuperior/1.png', 'Pizza calabresa com queijo, molho e massa especial'),
	('Pizza Quatro Queijos', 40.0, 'https://github.com/devsuperior/2.png', 'Pizza quatro queijos muito boa'),
	('Pizza de Escarola', 60.0, 'https://github.com/devsuperior/3.png', 'Pizza escarola muito boa');

INSERT INTO
	tb_order (status, latitude, longitude, moment)
VALUES
	(0, 213123, 12323, '2021-01-04 11:00:00'),
	(1, 3453453, 3534534, '2021-01-05 11:00:00');

INSERT INTO
	tb_order_product (order_id, product_id)
VALUES
	(1 , 1),
	(1 , 2),
	(2 , 2),
	(2 , 3);
```

## Consulta para recuperar os pedidos com seus produtos
```sql
SELECT * FROM tb_order
INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id
INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id
```
