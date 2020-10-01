create table user (
                      id bigint auto_increment primary key,
                      password varchar(255) null ,
                      username varchar(255) null

);
create table roles
(
    id   bigint auto_increment primary key,
    name varchar(60) null,
    constraint uname unique (name)
);
create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint userid foreign key (user_id) references user (id),
    constraint roleid foreign key (role_id) references roles (id)
);
create table sales (
                       id bigint auto_increment primary key ,
                       product_name varchar(255) null ,
                       product_quantity bigint,
                       unit_price double,
                       date date,
                       user bigint null,
                       constraint usersale foreign key (user) references user (id)
)