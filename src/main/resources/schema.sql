
DROP TABLE IF EXISTS account;

create table account(
OwnerID numeric(15) not null,
Currency varchar(25) not null,
Balance numeric(15,2) not null,primary key(OwnerID));