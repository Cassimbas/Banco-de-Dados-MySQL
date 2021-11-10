/**
Projeto de uma assistência técnica de video games
@authores Cássio Braga e Djeniffer Vidal
*/

create database dbazstec;
use dbazstec;
drop database dbazstec;

drop table usuarios;
create table usuarios(
	id int primary key	auto_increment,
    usuario varchar(50) not null,
    login varchar(50) not null unique,
    senha varchar(250) not null,
    perfil varchar(50) not null
);

-- a linha abaixo insere uma senha com criptografia
insert into usuarios(usuario,login,senha,perfil)
values ('Cassio Braga','braga',md5('123456'),'administrador');
insert into usuarios(usuario,login,senha,perfil)
values ('Djeniffer Vidal','vidal',md5('12345'),'administrador');
insert into usuarios(usuario,login,senha,perfil)
values ('Fox Mulder','fox',md5('1234'),'operador');
insert into usuarios(usuario,login,senha,perfil)
values ('Danna Scully','danna',md5('12344'), 'operador');


use dbasstec;
select * from usuarios;
drop table clientes;
create table clientes(
	idcli int primary key auto_increment,
    nome varchar(100) not null,
    cpf char(11) unique not null,
    fone1 varchar(11) not null,
    endereco varchar(100) not null,
    numero varchar(10) not null,
    complemento varchar(50),
    bairro varchar(50) not null,
    cidade varchar(50) not null,
    uf char(2) not null,
    cep char(8),
    email varchar(100) unique
);
describe clientes;
alter table clientes modify cpf varchar(15);
alter table clientes modify cpf varchar(15);

describe clientes;
insert into clientes(nome,cpf,fone1,endereco,numero,complemento,bairro,cidade,uf,cep,email)
values ('Ariovaldo da silva','51142089002','11555566666','Rua Nova','1','','Jardim São Cristóvão','São Luís','MA','65055360','');
insert into clientes(nome,cpf,fone1,endereco,numero,complemento,bairro,cidade,uf,cep,email)
values ('Pereira da Silva','75301366076','1155557777','Rua Professor Cantuária','2','','Samapi','Teresina','PI','64058100','d@dd.com');
-- delete from clientes where idcli=1;


select * from clientes;
drop table tbos;
create table tbos(
	os int primary key auto_increment,
    dataos timestamp default current_timestamp,
    tipo varchar (20) not null,
    statusos varchar(50) not null,
    equipos varchar(50) not null,
    serieos varchar(50),
    defeitoos varchar(100) not null,
    obsos varchar(100),
    tecnico varchar(50),
    valorserv decimal (10,2),
    formadepagto varchar(50),
    idcli int not null,
    foreign key (idcli) references clientes(idcli),
    id int not null,
    foreign key (id) references usuarios(id)
);

describe tbos;
select * from clientes;
select * from usuarios;
select * from tbos;

insert into tbos(statuos,equipos,serieos,defeitoos,obsos,tecnico,valorserv,formadepagto,idcli)
values (,?,?,?,?,?,?,?,?);

select * from usuarios where usuario like 'd%';

insert into tbos(tipo,statusos,equipos,serieos,defeitoos,obsos,idcli,id)
values ('Orçamento','Orçamento','PlayStation5','nd132da234','Não Liga','Aguardando ok do cliente',1,3);
insert into tbos(statusos,equipos,serieos,defeitoos,obsos,tecnico,valorserv,formadepagto,idcli,id)
values ('Bancada','XBox Serie S','ngpo2658eww','SSD não carrega','Equipamento em manutenção','Fox',580,'Cartão de Crédito',2,3);
insert into tbos(statusos,equipos,serieos,defeitoos,obsos,tecnico,valorserv,formadepagto,idcli,id)
values ('Aprovado','Controle PS4','kpoo2569888det','Botão Analógico com Defeito','Trocamos por um botão novo','Danna',600,'Cartão de Crédito',2,4);
insert into tbos(statusos,equipos,defeitoos,obsos,tecnico,valorserv,formadepagto,idcli,id)
values ('Retirado','Nintendo Switch','Tela trincada','Trocamos a tela do aparelho','Fox',870,'Dinheiro',1,3);
insert into tbos(statusos,equipos,serieos,defeitoos,obsos,tecnico,idcli,id)
values ('Orçamento','PlayStation5','nd132d1254','Esquenta','Aguardando ok do cliente','Fox',2,3);
insert into tbos(statusos,equipos,serieos,defeitoos,servos,tecnico,valorserv,formadepagto,idcli,id)
values ('Bancada','XBox Serie X','ngpo26569eww','SSD não carrega','Equipamento em manutenção','Danna',780,'Cartão de Crédito',2,4);


delete from tbos where id=3;

use dbazstec;

select * from tbos;

-- relatório 1
select * from tbos inner join clientes
on tbos.idcli = clientes.idcli;

-- relatório 2
select
tbos.equipos as Equipamento,tbos.defeitoos as Defeito,tbos.statusos as Status_OS,tbos.valorserv as Valor_Serviço,
tbos.valorpeca as Valor_Peça, tbos.valorpeca + tbos.valorserv, clientes.nomecli as Nome_do_Cliente,clientes.fone1cli as Telefone_1
from tbos inner join clientes
on tbos.idcli = clientes.idcli
where statusos = 'Aprovado';

-- relatório 3 (OS, Data formatada(dd/mm/yyyy) equipamento, defeito, valor, nome do cliente) filtrando por retirado)
select
date_format(tbos.dataos,'%d/%m/%Y - %H:%i') as Data_OS, tbos.equipos as Equipamento,tbos.defeitoos as Defeito,tbos.valorserv as Valor_Serviço,
tbos.valorpeca as Valor_da_Peça, tbos.valorpeca + tbos.valorserv as Total, clientes.nomecli as Nome_do_Cliente
from tbos inner join clientes
on tbos.idcli = clientes.idcli
where statusos = 'Retirado';

-- relatório 4
select clientes.idcli, tbos.valorserv as Valor_de_Serviços, tbos.valorpeca as Valor_da_Peça,(valorserv + valorpeca - valordesc) as Faturamento from tbos where statusos = 'Retirado';




-- Relatório de Garantia
select os as Ordem_Serviço, equipos,date_format(dataret,'%d/%m/%Y') as data_retirada
from tbos;

-- curdate() data autal
select os as Ordem_Serviço,equipos,date_format(dataret, '%d/%m/%Y') as data_Retirada,
adddate(dataret, Interval 90 day) as Garantia from tbos;
