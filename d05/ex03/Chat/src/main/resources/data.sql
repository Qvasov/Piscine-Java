INSERT INTO t_users(f_login, f_password) VALUES
    ('bhugo', '1111'),
    ('dbennie', '2222'),
    ('mcanhand', '3333'),
    ('ffood', '4444'),
    ('smight', '5555');

INSERT INTO t_chatrooms(f_name, f_owner_id) VALUES
   ('room #1', (SELECT f_id FROM t_users WHERE f_login = 'bhugo')),
   ('room #2', (SELECT f_id FROM t_users WHERE f_login = 'dbennie')),
   ('room #3', (SELECT f_id FROM t_users WHERE f_login = 'mcanhand')),
   ('room #4', (SELECT f_id FROM t_users WHERE f_login = 'ffood')),
   ('room #5', (SELECT f_id FROM t_users WHERE f_login = 'smight')),
   ('room #6', (SELECT f_id FROM t_users WHERE f_login = 'bhugo')),
   ('room #7', (SELECT f_id FROM t_users WHERE f_login = 'dbennie')),
   ('room #8', (SELECT f_id FROM t_users WHERE f_login = 'mcanhand')),
   ('room #9', (SELECT f_id FROM t_users WHERE f_login = 'ffood')),
   ('room #10', (SELECT f_id FROM t_users WHERE f_login = 'smight'));

INSERT INTO t_messages(f_author_id, f_chatroom_id, f_text) VALUES
    ((SELECT f_id FROM t_users WHERE f_login = 'bhugo'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #1'),
     'Hello!'),
    ((SELECT f_id FROM t_users WHERE f_login = 'dbennie'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #1'),
     'Hi!'),
    ((SELECT f_id FROM t_users WHERE f_login = 'mcanhand'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #2'),
     'Salut!'),
    ((SELECT f_id FROM t_users WHERE f_login = 'ffood'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #2'),
     'Privet!'),
    ((SELECT f_id FROM t_users WHERE f_login = 'smight'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #3'),
     'Halo!'),
    ((SELECT f_id FROM t_users WHERE f_login = 'bhugo'),
     (SELECT f_id FROM t_chatrooms WHERE f_name = 'room #3'),
     'Bonjour!');
