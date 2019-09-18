create table reviews (
     id serial primary key,
     book_id integer references books(id),
     description varchar(200) null,
     rating integer null
);