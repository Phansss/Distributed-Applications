create table jnd_course_person
(
    course_fk int not null,
    person_fk int not null,
    primary key (course_fk, person_fk),
    constraint FK_jnd_course_person_course_fk
        foreign key (course_fk) references Course (id),
    constraint FK_jnd_course_person_person_fk
        foreign key (person_fk) references Person (id)
);

