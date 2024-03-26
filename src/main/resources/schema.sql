create schema crazycarddb collate utf8mb4_0900_ai_ci;

create table cards
(
    id       bigint auto_increment
        primary key,
    balance  double      not null,
    number   varchar(16) null,
    pin      varchar(6)  not null,
    state    bit         not null,
    owner_id bigint      null,
    constraint card_owner_fk
        foreign key (owner_id) references users (id)
);



create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) not null,
    constraint role_name_uk
    unique (name)
    );



create table stores
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) not null,
    locality   varchar(255) not null,
    authorized bit          not null,
    constraint store_name_uk
    unique (name)
    );



create table transactions
(
    id       bigint auto_increment
        primary key,
    amount   double not null,
    date     date   not null,
    card_id  bigint null,
    store_id bigint null,
    constraint transaction_store_id_fk
    foreign key (store_id) references stores (id),
    constraint transaction_card_id_fk
        foreign key (card_id) references cards (id)
);



create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null,
    store_id bigint       null,
    constraint user_email_uk
    unique (email),
    constraint user_store_id_fk
        foreign key (store_id) references stores (id)
);



create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    constraint users_roles_user_id_fk
    foreign key (user_id) references users (id),
    constraint users_roles_role_id_fk
        foreign key (role_id) references roles (id)
);

