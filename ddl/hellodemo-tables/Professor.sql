create table Professor
(
    id              int auto_increment
        primary key,
    amountOfRatings int          null,
    name            varchar(255) null,
    rating          int          null,
    surname         varchar(255) null
);

insert into
