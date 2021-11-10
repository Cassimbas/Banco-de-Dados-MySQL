/**
Sistema  para gestão de uma assistência técnica de computadores e periféricos
@author Cássio Braga
*/

create database dbinfox;
use dbinfox;

create table usuarios(
	id int primary key auto_increment,
	usuario varchar(50) not null,
	login varchar(50) not null unique,
	senha varchar(250) not null,
	perfil varchar(50) not null
);
-- a linha abaixo insere uma senha com criptografia
insert into usuarios(usuario,login,senha,perfil)
values ('Cassio Braga','admin',md5('123456'),'administrador');

select * from usuarios;

insert into usuarios(usuario,login,senha,perfil)
values ('Dana Scully','Dana',md5('123'),'operador');

select * from usuarios;
select * from usuarios where id = 1;

-- selecionar o usuário e sua senha (tela de login)
select * from usuarios where login='admin' and senha=md5('123456');

update usuarios set usuario='Dana Willian Scully', login='danaw', senha=md5('1234'), perfil='operador' where id=2;

select * from usuarios;

-- exemplo para escluir um usuário criado
delete from usuarios where id=3;

-- char (tipo de dados que aceita uma string de caracteres não variável)
create table clientes(
	idcli int primary key auto_increment,
    nomecli varchar(50) not null,
    cepcli char(8),
    endcli varchar(50) not null,
    numcli varchar(12) not null,
    complementocli varchar(30),
    bairrocli varchar(50) not null,
    cidadecli varchar(50) not null,
    ufcli char(2) not null,
    fonecli varchar(15) not null
);

insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Clark Kent',02121000,'rua do planeta diario',001,'10º andar','diario','metropolis','DM',55555-2222);
insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Bruce Wayne',02222000,'rua da mansao',100,'casa','floresta','gotham','FG',66666-1111);
insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Berry Allen',02323000,'rua do raio',555,'casa de esquina','luz','central city','LC',44444-3333);
insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Daiana Prince',02525000,'rua das amazonas',200,'15º andar','ilha','themyscira','IT',77777-4444);
insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Coringa',02626000,'rua do palhaço',145,'nao tem','piada','gotham','FG',88888-4444);
insert into clientes(nomecli,cepcli,endcli,numcli,complementocli,bairrocli,cidadecli,ufcli,fonecli)
values ('Batman que ri',06666000,'av do batman',222,'casa do morcego','Bat','gotham','FG',99999-3333);

select * from clientes;

select * from usuarios;
use dbinfox;

-- foreign key (FK) cria um relacionamento de 1 para muitos (cliente - tbos)
create table tbos(
	os int primary key auto_increment,
    dataos timestamp default current_timestamp,
    tipoos varchar(20) not null,
    statusos varchar(30) not null,
	equipamentoos varchar(200) not null,
    defeitoos varchar(200) not null,
    tecnicoos varchar(50),
    valoros decimal(10,2),
    idcli int not null,
    foreign key(idcli) references clientes(idcli)
);
drop table tbos;

select * from tbos;
describe tbos;

insert into tbos(tipoos,statusos,equipamentoos,defeitoos,idcli)
values ('orçamento','bancada','Notebook LenovoG90','Não Liga',1);

select * from tbos;

insert into tbos(tipoos,statusos,equipamentoos,defeitoos,tecnicoos,valoros,idcli)
values ('Orçamento','aguardando aprovação','impressora HP2020','papel enroscando','Robson',60,1);

insert into tbos(tipoos,statusos,equipamentoos,defeitoos,tecnicoos,valoros,idcli)
values ('serviço','retirado','Samsung Galaxy10E','Tela frotal trincada','Robson',200,2);

insert into tbos(tipoos,statusos,equipamentoos,defeitoos,tecnicoos,valoros,idcli)
values ('serviço','retirado','Notebook Galaxy SPro','Não Liga','Robson',100,4);


insert into tbos(tipoos,statusos,equipamentoos,defeitoos,tecnicoos,valoros,idcli)
values ('Orçamento','aguardando aprovação','Samsung S21','Não Liga','Robson',250,3);
    
select * from tbos;

-- união de tabelas relacionadas para consultas e updtes
-- relatório 1
select * from tbos inner join clientes
on tbos.idcli = clientes.idcli;

select
tbos.equipamentoos as Equipamento,tbos.defeitoos as Defeito,tbos.statusos as Status_os,tbos.valoros as Valor,
clientes.nomecli as Nome_do_Cliente,clientes.fonecli as Fone_do_Cliente
from tbos inner join clientes
on tbos.idcli = clientes.idcli
where statusos = 'aguardando aprovação';

-- relatório 3 (OS, Data formatada(dd/mm/yyyy) equipamento, defeito, valor, nome do cliente) filtrando por retirado)
select
date_format(tbos.dataos,'%d/%m/%Y - %H:%i') as Data_OS, tbos.equipamentoos as Equipamento,tbos.defeitoos as Defeito,tbos.valoros as Valor,
clientes.nomecli as Nome_do_Cliente
from tbos inner join clientes
on tbos.idcli = clientes.idcli
where statusos = 'retirado';

-- relatório 4
select	sum(valoros) as Faturamento from tbos where statusos = 'retirado';

use dbinfox;

select idcli as ID, nomecli as Cliente from clientes where nomecli like "B%";