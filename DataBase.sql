create database biblioteca;
use biblioteca;

create table usuarios(
	id int primary key auto_increment,
    usuario varchar(30) not null,
    contra varchar(30) not null
);

drop table usuarios;

insert into usuarios (usuario, contra)
values ('admin', '123');

insert into usuarios (usuario, contra)
values ("ariel", "123");

use biblioteca;

create table libros (
    id int primary key auto_increment,
    titulo varchar(100),
    autor varchar(100),
    anio int
);

insert into libros (titulo, autor, anio) values
('cien años de soledad', 'gabriel garcia marquez', 1967),
('el principito', 'antoine de saint-exupery', 1943),
('1984', 'george orwell', 1949);
