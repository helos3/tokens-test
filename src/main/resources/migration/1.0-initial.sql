create table token (
  token                         varchar(255) not null,
  user_login                    varchar(255),
  creation_date                 timestamp,
  expired                       boolean,
  constraint pk_token primary key (token)
);

create table user_ (
  login                         varchar(255) not null,
  password                      varchar(255),
  salt                          varchar(255),
  deleted                       boolean default false not null,
  constraint pk_user primary key (login)
);

alter table token add constraint fk_token_user_login foreign key (user_login) references user_ (login) on delete restrict on update restrict;
create index ix_token_user_login on token (user_login);

