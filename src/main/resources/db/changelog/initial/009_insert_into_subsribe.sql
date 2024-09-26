insert into subscriptions (subscriber_id, user_id)
values
    ((select id from users where name = 'John' and surname = 'Doe'),
     (select id from users where name = 'Helen' and surname = 'Li')),
    ((select id from users where name = 'John' and surname = 'Doe'),
     (select id from users where name = 'Anton' and surname = 'Ivanov')),
    ((select id from users where name = 'Helen' and surname = 'Li'),
     (select id from users where name = 'Anton' and surname = 'Ivanov'));
