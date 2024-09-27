insert into authorities (authority)
values('FULL'),
      ('READ_ONLY'),
      ('client');

INSERT INTO users (name, surname, age, email, password, phone_number, nick_name, bio, authority_id)
VALUES
    ('John', 'Doe', 33, 'qqq@qqq.com', '$2a$12$58QSHFZi5E7tafrtWCahJOEEJ2bH1k7tIWOjzBIL/SBBZG5I.DyiS', '996555145145', 'johnD', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla commodo justo at elit ultrices, ac suscipit quam tempus.', 3),
    ('Helen', 'Li', 20, 'www@www.com', '$2a$12$58QSHFZi5E7tafrtWCahJOEEJ2bH1k7tIWOjzBIL/SBBZG5I.DyiS', '996555112233', 'liH',  'Sed euismod urna nec felis fringilla, vel varius ipsum cursus.', 3),
    ('Anton', 'Ivanov', 29, 'eee@eee.com', '$2a$12$58QSHFZi5E7tafrtWCahJOEEJ2bH1k7tIWOjzBIL/SBBZG5I.DyiS', '996555334455', 'aaaIII',  'Phasellus ac purus in nunc vehicula dictum.', 3);


insert into subscriptions (subscriber_id, user_id)
values
    ((select id from users where name = 'John' and surname = 'Doe'),
     (select id from users where name = 'Helen' and surname = 'Li')),
    ((select id from users where name = 'John' and surname = 'Doe'),
     (select id from users where name = 'Anton' and surname = 'Ivanov')),
    ((select id from users where name = 'Helen' and surname = 'Li'),
     (select id from users where name = 'Anton' and surname = 'Ivanov'));

insert into publications (AUTHOR_ID, IMAGE, DESCRIPTION, TIME_OF_PUBLICATION)
values ((select id from USERS where NAME = 'John' and SURNAME = 'Doe'), 'photo1.jpg',
        'Ребята, как вам мой новый пост?', PARSEDATETIME('2024-04-26 12:00:00','yyyy-MM-dd HH:mm:ss')),
       ((select id from USERS where NAME = 'Helen' and SURNAME = 'Li'), 'photo2.jpg',
        'Хе-хе-хе', PARSEDATETIME('2024-04-26 12:10:00','yyyy-MM-dd HH:mm:ss')),
       ((select id from USERS where NAME = 'John' and SURNAME = 'Doe'), 'photo3.jpg',
        'Hard coding!', PARSEDATETIME('2024-04-26 12:20:00','yyyy-MM-dd HH:mm:ss'));

insert into COMMENTS(TEXT, COMMENTATOR_ID, publication_id)
values ('text of comment', (select id from USERS where NAME = 'Anton' and SURNAME = 'Ivanov'),(select p.id from PUBLICATIONS p left join USERS U on U.ID = p.AUTHOR_ID where p.TIME_OF_PUBLICATION = PARSEDATETIME('2024-04-26 12:00:00','yyyy-MM-dd HH:mm:ss') and u.NAME = 'John' and u.SURNAME = 'Doe'));


