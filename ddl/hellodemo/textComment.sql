create table textComment
(
    commentId    int          not null
        primary key,
    Comment_Text varchar(255) null,
    constraint FK_textComment_commentId
        foreign key (commentId) references Comments (commentId)
);

