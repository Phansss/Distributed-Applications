ALTER TABLE `hellodemo`.`Comment`
    AUTO_INCREMENT = 1 ;
insert into hellodemo.Comment(Date, Comment_Type)
values ('2022-01-01 00:00:00','T'), ('2022-01-02 00:00:00','T'), ('2022-01-03 00:00:00','T'),
       ('2022-01-04 00:00:00','T'),  ('2022-01-05 00:00:00','T'), ('2022-01-06 00:00:00', 'T'),
       ('2022-01-07 00:00:00','T'), ('2022-01-08 00:00:00','T'), ('2022-01-09 00:00:00','T'),
       ('2022-01-10 00:00:00', 'T'), ('2022-01-11 00:00:00', 'T'), ('2022-01-12 00:00:00', 'T'),
       ('2022-01-13 00:00:00', 'T'), ('2022-01-14 00:00:00','T'),('2022-01-15 00:00:00','T'),
       ('2022-01-16 00:00:00','T'), ('2022-01-17 00:00:00','T'), ('2022-01-18 00:00:00','T'),
       ('2022-01-19 00:00:00', 'T'), ('2022-01-20 00:00:00','T'),
       ('2022-01-21 00:00:00', 'RT'),
       ('2022-01-22 00:00:00','RT'), ('2022-01-23 00:00:00','RT'), ('2022-01-24 00:00:00', 'RT'),
       ('2022-01-25 00:00:00', 'RT'), ('2022-01-26 00:00:00','RT'), ('2022-01-27 00:00:00','RT'),
       ('2022-01-28 00:00:00', 'RT'), ('2022-01-29 00:00:00','RT'), ('2022-01-30 00:00:00', 'RT'),
       ('2022-01-31 00:00:00','RT'), ('2022-02-01 00:00:00','RT'), ('2022-02-02 00:00:00', 'RT'),
       ('2022-02-03 00:00:00', 'RT'), ('2022-02-04 00:00:00', 'RT'), ('2022-02-05 00:00:00','RT'),
       ('2022-02-06 00:00:00', 'RT'), ('2022-02-07 00:00:00', 'RT'), ('2022-02-08 00:00:00', 'RT'),
       ('2022-02-09 00:00:00', 'RT'), ('2022-02-10 00:00:00', 'RT'), ('2022-02-11 00:00:00', 'RT'),
       ('2022-02-12 00:00:00','RT'),
       ('2022-01-01 01:00:00','R'), ('2022-01-01 02:00:00', 'R'),
       ('2022-01-01 03:00:00','R'), ('2022-01-01 04:00:00', 'R'), ('2022-01-01 05:00:00', 'R'),
       ('2022-01-01 06:00:00', 'R'), ('2022-01-01 07:00:00', 'R'), ('2022-01-01 08:00:00', 'R'),
       ('2022-01-01 09:00:00', 'R'), ('2022-01-01 10:00:00', 'R'), ('2022-01-01 11:00:00', 'R'),
       ('2022-01-01 12:00:00', 'R'), ('2022-01-01 13:00:00', 'R'), ('2022-01-01 14:00:00', 'R'),
       ('2022-01-01 15:00:00', 'R'), ('2022-01-01 16:00:00', 'R'), ('2022-01-01 17:00:00','R');

    ALTER TABLE `hellodemo`.`textComment`
    AUTO_INCREMENT = 1 ;
insert into hellodemo.textComment(commentId, Comment_Text)
values (1, 'Super prof!'),
       (2, 'Saaie lessen maar goede cursus'),
       (3, 'Kan het goed uitleggen!'),
       (4, 'Funny professor'),
       (5, 'This course doesnt make sense but the professor does'),
       (6, 'I dont get why we need to study this material, cool guy tho'),
       (7, 'mr pom pom pom'),
       (8, 'I hate the course but I like the professor'),
       (9, 'I like the course but I dont understand the professor'),
       (10, 'Met zo een goede uitleg snap ik niet dat iemand kan buizen vvoor dit vak'),
       (11, 'Een goede prof of een goede cursus, maar nooit beide. Hier zitten we met het eerste geval.'),
       (12, 'Daar kunnen ze bij mij op het middelbaar nog wat van leren'),
       (13, 'Ik begreep nooit iets van dit vak tot nu'),
       (14, 'His English is horrible but I can understand it'),
       (15, 'Als ik zo zie wat die doet zou ik zelf ook wel prof willen worden #hardwerken #ofniet?'),
       (16, 'Als ik zou moeten kiezen tussen chillen in het park of lessen volgen bij deze prof, kies ik chillen in het park
            met de videolectures van deze prof.'),
       (17, 'Wat een kanjer van een lesgever!'),
       (18, 'Interessante lectures, moeilijk examen'),
       (19, 'Typische GroepT prof!'),
       (20, 'Interessanter dan uw opa, grappiger dan uw zatte nonkel op het familiefeest');

ALTER TABLE `hellodemo`.`ratingTextComment`
    AUTO_INCREMENT = 1 ;
insert into hellodemo.ratingTextComment(commentId, Comment_Rating, Comment_Text)
values  (21, 2, 'De uitleg is overbodig. alles spreekt voor zich'),
        (22, 2, 'Zonder deze prof geen frustraties op Groep T'),
        (23, 1, 'Zonder deze prof geen buizen op Groep T'),
        (24, 5, 'De beste prof ooit!'),
        (25, 5, 'Wat een kanjer'),
        (26, 4, 'Ah, zo ziet die er dus uit, oeps'),
        (27, 3, 'Kan goed lesgeven, kan ook goed boos kijken'),
        (28, 3, 'Valt bijna zelf in slaap tijdens zijn eigen les'),
        (29, 4, 'Love the humour, hate the course. You never can have both at Group T'),
        (30, 5, 'This prof rocks!'),
        (31, 3, 'meh'),
        (32, 1, 'I Attended 1 lecture, skipped the others. Aint nobody got time for that!'),
        (33, 2, 'Why in heaven would you go to these lectures?'),
        (34, 4, 'Why in heaven would you not go to the lectures?'),
        (35, 5, 'Should we tell him that theyre baking croqueskes during his lectures? #truestory'),
        (36, 4, 'When he laughs, he reminds me of the "xd" emoji'),
        (37, 1, 'Its been six times I have taken his final and still have not been abe to pass. See you at Thomas More guys'),
        (38, 1, 'The reason why I need meds everyday'),
        (39, 5, 'Chance dat er soms nog goede proffen op deze aardbol ondlopen. Dikke S/O naar deze mens'),
        (40, 1, 'Een goede prof of een goede cursus maar nooit beide. In dit geval geen van de twee..'),
        (41, 5, 'Een goede prof of een goede cursus. Hier gelukkig beide!'),
        (42, 5, 'Kan goed zuipen tijdens de proffentap in de recup.'),
        (43, 5, 'Trakteert gratis pinten tijdens de proffentap. MVP');

ALTER TABLE `hellodemo`.`ratingComment`
    AUTO_INCREMENT = 1 ;
insert into hellodemo.ratingComment(commentId, Comment_Rating)
values (44, 3),
       (45, 4),
       (46, 2),
       (47, 5),
       (48, 5),
       (49, 5),
       (50, 4),
       (51, 1),
       (52, 1),
       (53, 2),
       (54, 3),
       (55, 2),
       (56, 3),
       (57, 4),
       (58, 5),
       (59, 4),
       (60, 4);

