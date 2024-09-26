insert into publications (AUTHOR_ID, IMAGE, DESCRIPTION, TIME_OF_PUBLICATION)
values ((select id from USERS where NAME = 'John' and SURNAME = 'Doe'), '6ec7e660-c58d-420e-b593-72e8943e5153_23.jpg',
        'description of post', PARSEDATETIME('2024-04-26 12:00:00','yyyy-MM-dd HH:mm:ss')),
       ((select id from USERS where NAME = 'Helen' and SURNAME = 'Li'), '6ec7e660-c58d-420e-b593-72e8943e5153_23.jpg',
        'description of post', PARSEDATETIME('2024-04-26 12:10:00','yyyy-MM-dd HH:mm:ss')),
       ((select id from USERS where NAME = 'John' and SURNAME = 'Doe'), '6ec7e660-c58d-420e-b593-72e8943e5153_23.jpg',
        'description of post', PARSEDATETIME('2024-04-26 12:20:00','yyyy-MM-dd HH:mm:ss'));



insert into COMMENTS(TEXT, COMMENTATOR_ID, publication_id)
values ('text of comment', (select id from USERS where NAME = 'Anton' and SURNAME = 'Ivanov'),(select p.id from PUBLICATIONS p left join USERS U on U.ID = p.AUTHOR_ID where p.TIME_OF_PUBLICATION = PARSEDATETIME('2024-04-26 12:00:00','yyyy-MM-dd HH:mm:ss') and u.NAME = 'John' and u.SURNAME = 'Doe'));


