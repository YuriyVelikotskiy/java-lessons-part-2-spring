create table users.users (
    user_id bigint primary key generated always as identity,
    name text,
    age integer,
    email text,
    created_at date
);
