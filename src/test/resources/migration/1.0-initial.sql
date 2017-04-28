DROP TABLE if EXISTS token;
DROP TABLE if exists account;

create table account (
  login                         varchar(255) not null,
  password                      varchar(255),
  salt                          varchar(255),
  deleted                       boolean default false not null,
  constraint pk_account primary key (login)
);

create table token (
  token                         varchar(255) not null,
  user_login                    varchar(255),
  creation_date                 timestamp,
  expired                       boolean,
  constraint pk_token primary key (token)
);

alter table token add constraint fk_token_user_login foreign key (user_login) references account (login) on delete restrict on update restrict;
create index ix_token_user_login on token (user_login);
