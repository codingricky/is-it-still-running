# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table persistable_token (
  uuid                      varchar(255) not null,
  email                     varchar(255),
  creation_time             timestamp,
  expiration_time           timestamp,
  is_sign_up                boolean,
  constraint pk_persistable_token primary key (uuid))
;

create table SecureSocialuser (
  id                        bigint not null,
  user_id                   varchar(255),
  provider_id               varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  full_name                 varchar(255),
  email                     varchar(255),
  avatar_url                varchar(255),
  hasher                    varchar(255),
  password                  varchar(255),
  salt                      varchar(255),
  role                      varchar(255),
  constraint pk_SecureSocialuser primary key (id))
;

create sequence persistable_token_seq;

create sequence SecureSocialuser_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists persistable_token;

drop table if exists SecureSocialuser;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists persistable_token_seq;

drop sequence if exists SecureSocialuser_seq;

