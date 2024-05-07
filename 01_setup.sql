create table users (
    id serial primary key,
    username varchar(255) not null,
    email_address varchar(255) not null,
    birth_month varchar(6)
);

create table inventory (
    id serial primary key,
    item_name varchar(255) not null,
    value int not null default 0
);

create table users_items (
    user_id bigint unsigned not null,
    inventory_id bigint unsigned not null,
    foreign key (user_id) references users(id),
    foreign key (inventory_id) references inventory(id)
);