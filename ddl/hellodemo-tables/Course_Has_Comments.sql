create table Course_Comments
(
    courseId  int not null,
    commentId int not null,
    primary key (courseId, commentId),
    constraint FK_Course_Comments_commentId
        foreign key (commentId) references Comment (commentId),
    constraint FK_Course_Comments_courseId
        foreign key (courseId) references Course (courseId)
);