create table jnd_course_professor
(
    course_fk    int not null,
    professor_fk int not null,
    primary key (course_fk, professor_fk),
    constraint FK_jnd_course_professor_course_fk
        foreign key (course_fk) references Course (id),
    constraint FK_jnd_course_professor_professor_fk
        foreign key (professor_fk) references Professor (id)
);

