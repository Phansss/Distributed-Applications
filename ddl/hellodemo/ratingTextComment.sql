create table ratingTextComment
(
    commentId      int          not null
        primary key,
    Comment_Rating int          null,
    Comment_Text   varchar(255) null,
    constraint FK_ratingTextComment_commentId
        foreign key (commentId) references Comments (commentId)
);

