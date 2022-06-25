create table Course
(
    id           int auto_increment
        primary key,
    Name         varchar(255) not null,
    YEAROFCOURSE int          null,
    constraint Name
        unique (Name)
)
    auto_increment = 11;

