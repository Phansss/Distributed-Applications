-- auto-generated definition
create table Person_Comments
(
    personId  int not null,
    commentId int not null,
    primary key (personId, commentId),
    constraint FK_Person_Comments_commentId
        foreign key (commentId) references Comment (commentId),
    constraint FK_Person_Comments_personId
        foreign key (personId) references Person (personId)
);

