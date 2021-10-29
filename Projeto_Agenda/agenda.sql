/**
	Agenda de contato
    @author Cassio Rodrigues Braga
*/

-- Abaixo alguns comandos básicos

-- Exibir banco de dados
show databases;

-- Criar um banco de dados
create database dbagenda;

-- Excluir banco de dados
drop database dbteste;

-- Selecionar banco de dados
use dbagenda;

-- Exibir tabelas existentes
show tables;
-- Criando tabela (Toda tabela tem que ter uma chave primária (PK)
-- int (Tipo de dados => números inteiros)
-- primary key = transforma esse campo em chave primária
-- auto_increment = numeração automática
-- varchar = tipo de dados equivalente a String (50) limitação do campo de caracteres
-- not null = preenchimento obrigatório
-- unique = não permite valores duplicados na tabela
create table contatos(
	id int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50) unique
);

-- Descrição da tabela
describe contatos;

-- Alterar o nome de um campo na tabela
alter table contatos change nome contato varchar(50) not null;

-- Adicionar um novo campo(coluna) a tabela
alter table contatos add column obs varchar(250);

-- Adicionar um novo campo(coluna) em um local especifico na tabela
alter table contatos add column fone2 varchar(15) after fone;
 
 -- Modificar tipos de dados e/ou validação na coluna
 alter table contatos modify column fone2 int;
 alter table contatos modify column email varchar(100);
 
 -- Excluir uma coluna da tabela
 alter table contatos drop column obs;
 
 -- Escluir da tabela
 drop table contatos;
 
 use dbagenda;
 
 -- GRUD (Create - Read - Update - Delete)
 -- Operação básicas do banco de dados
 
 -- GRUD - create
 insert into contatos(nome,fone,email) values ('Batman que Ri','555-555','btri@ri.com');
 insert into contatos(nome,fone,email) values ('Bruce Wayne','555-777','bw@wb.com');
 insert into contatos(nome,fone,email) values ('Clark Kent','555-888','spman@sp.com');
 insert into contatos(nome,fone,email) values ('Daiana Prince','555-999','dai@pr.com');
 insert into contatos(nome,fone,email) values ('Berry Allen','555-444','fls@fsh.com');
 insert into contatos(nome,fone) values ('Arthur Curry','555-333');
insert into contatos (nome,fone) values ('teste','666-555'); 
 -- GRUD Read
 -- Selecionar todos os registros(dados) da tabela
 select * from contatos;
 
 -- Selecionar uma tabela específica
 select nome, fone from contatos;
 
 -- Selecionar colunas em ordem crescente e decrescente
 select * from contatos order by nome;
 select id, nome from contatos order by id desc;
 select id, nome from contatos order by id asc;
 
 -- Uso de filtros
 select * from contatos where id = 1;
 select * from contatos where id >=3;
 select * from contatos where nome = 'Bruce Wayne';
 select * from contatos where nome like 'B%';
 
 -- GRUD Update
 -- ATENÇÃO! Não esqueça da função where quando precisar alterar algum dado da tabela e de um id específico
 update contatos set email='flash@fsh.com' where id=5;
 update contatos set fone='555-3333' where id=4;
 
 -- GRUD Delete
 -- ATENÇÃO! Não esqueça do where e o Id que deseja apagar
 delete from contatos where id=7;
 
 select * from contatos;