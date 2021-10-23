/**
Projeto de uma assistência técnica de video games
@authores Cássio Braga e Djeniffer Vidal
*/

create database dbazstec;
use dbazstec;
drop database dbazstec;

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
values ('Fox Mulder','fox',md5('1234'),'tecnico');
insert into usuarios(usuario,login,senha,perfil)
values ('Danna Scully','danna',md5('12344'), 'tecnico');


use dbasstec;
select * from usuarios;
drop table clientes;
create table clientes(
	idcli int primary key auto_increment,
    nome varchar(100) not null,
    cpf varchar(15) not null,
    fone1 varchar(11) not null,
    endereco varchar(100),
    numero varchar(10),
    complemento varchar(50),
    bairro varchar(50),
    cidade varchar(50),
    Uf varchar(2),
    Cep varchar(8),
    email varchar(100) unique
);

insert into clientes(nome,cpf,fone1,email)
values ('Ariovaldo da silva',12345678900,11666665555,'ari@ari.com');
insert into clientes(nome,cpf,fone1,email)
values ('Valdirene dos Santos',98765432100,11999993333,'val@val.com');
insert into clientes(nome,cpf,fone1,email)
values ('Jose Alfredo',12312312300,11111112222,'jose.alfredo@hotmail.com');
insert into clientes(nome,cpf,fone1,email)
values ('lucas Maria',32132132100,12312312300,'lucas95@lm.com.br');
insert into clientes(nome,cpf,fone1,email)
values ('Bruce Waynne',45896321788,1155551111,'bw@bw.com');
insert into clientes(nome,cpf,fone1,email)
values ('Berry Allen',15935789655,1223659862,'flash@hotmail.com');
insert into clientes(nome,cpf,fone1,email)
values ('Batman que Ri',66666666633,666999333,'batqri@br.com.br');
update clientes set nome='Bernardo Silva' where idcli=7;
update clientes set fone1=111234567 where idcli=6;
-- delete from clientes where idcli=1;


select * from clientes;
drop table tbos;
create table tbos(
	os int primary key auto_increment,
    dataos timestamp default current_timestamp,
    dataret date,
    statusos varchar(50) not null,
    equipos varchar(50) not null,
    modos varchar(50) not null,
    marcaos varchar(50) not null,
    acessos varchar(50) not null,
    serieos varchar(50),
    defeitoos varchar(100) not null,
    servos varchar(100) not null,
    obsos varchar(100),
    tecnico varchar(50),
    valorserv decimal (10,2),
    valorpeca decimal (10,2),
    formadepagto varchar(50),
    idcli int not null,
    foreign key (idcli) references clientes(idcli),
    id int not null,
    foreign key (id) references usuarios(id)
);

select * from clientes;
select * from usuarios;

insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,idcli,id)
values ('Orçamento','Console','PlayStation5','Sony','Sem acessórios','nd132da234','Não Liga','Aguardando ok do cliente',1,3);
insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,tecnico,valorserv,valorpeca,formadepagto,idcli,id)
values ('Bancada','Console','XBox Serie S','Microsoft','Sem acessórios','ngpo2658eww','SSD não carrega','Equipamento em manutenção','Fox',200,580,'Cartão de Crédito',2,3);
insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,tecnico,valorserv,valorpeca,formadepagto,idcli,id)
values ('Aprovado','Console','PlayStation4','Sony','Com controle','kpoo2569888det','Botão Analógico com Defeito','Trocamos por um botão novo','Danna',200,400,'Cartão de Crédito',4,4);
insert into tbos(statusos,equipos,modos,marcaos,acessos,defeitoos,servos,tecnico,valorserv,valorpeca,formadepagto,dataret,idcli,id)
values ('Retirado','Console','Nintendo Switch','Nintendo','Sem acessórios','Tela trincada','Trocamos a tela do aparelho','Fox',200,770,'Dinheiro',20211224,3,3);
insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,idcli,id)
values ('Orçamento','Console','PlayStation5','Sony','Sem acessórios','nd132d1254','Esquenta','Aguardando ok do cliente',5,3);
insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,tecnico,valorserv,valorpeca,formadepagto,idcli,id)
values ('Bancada','Console','XBox Serie X','Microsoft','Sem acessórios','ngpo26569eww','SSD não carrega','Equipamento em manutenção','Danna',200,580,'Cartão de Crédito',6,4);
insert into tbos(statusos,equipos,modos,marcaos,acessos,serieos,defeitoos,servos,tecnico,valorserv,valorpeca,formadepagto,idcli,id)
values ('Aprovado','Console','PlayStation5','Sony','Com controle','kpoo25645dd8det','Blue Ray não lê CD','Trocamos o Equipamento do BlueRay','Fox',600,400,'Cartão de Crédito',7,3);


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
