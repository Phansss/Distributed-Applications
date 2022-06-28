create table Professor_Comments
(
    ProfessorEntity_id      int not null,
    commentsAbout_commentId int not null,
    primary key (ProfessorEntity_id, commentsAbout_commentId),
    constraint FK_Professor_Comments_ProfessorEntity_id
        foreign key (ProfessorEntity_id) references Professor (professorId),
    constraint FK_Professor_Comments_commentsAbout_commentId
        foreign key (commentsAbout_commentId) references Comments (commentId)
);

