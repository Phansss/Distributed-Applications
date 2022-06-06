create table ratingComment
(
    commentId      int not null
        primary key,
    Comment_Rating int null,
    constraint FK_ratingComment_commentId
        foreign key (commentId) references Comments (commentId)
);

