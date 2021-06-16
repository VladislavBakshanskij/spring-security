create type roles as enum ('ADMIN', 'USER');
create table auth_user
(
    id       serial primary key,
    name     varchar(30),
    password varchar,
    role     roles
);

create unique index user_has_unique_name on auth_user (lower(name));


create table token
(
    token   varchar primary key,
    user_id bigint,
    constraint token_has_user foreign key (user_id) references auth_user (id) on delete cascade on update cascade
);

insert into auth_user(name, password, role)
values ('admin', '$2y$12$VE5VxfV1nH50k4vR77xzqOgJk5PH0EEWO6Th75vKljm71t7pPe53W', 'ADMIN'),
       ('user', '$2y$12$.uQemocqjJ5CD9coA.abk.0qOg4E6TlUCCRcDaKPXLexuTcLJoPaS', 'USER');
