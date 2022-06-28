create table Comments
(
    commentId    int auto_increment
        primary key,
    Comment_Type varchar(31) null,
    Name         datetime    not null,
    isAboutId    int         null,
    MadeById     int         null,
    constraint Name
        unique (Name),
    constraint FK_Comments_MadeById
        foreign key (MadeById) references Person (personId),
    constraint FK_Comments_isAboutId
        foreign key (isAboutId) references Professor (professorId)
);

