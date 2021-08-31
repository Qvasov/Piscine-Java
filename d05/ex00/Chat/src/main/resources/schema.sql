CREATE TABLE t_users(
    f_id BIGSERIAL PRIMARY KEY,
    f_login VARCHAR(255) NOT NULL,
    f_password VARCHAR(255) NOT NULL
);

CREATE TABLE t_chatrooms(
    f_id BIGSERIAL PRIMARY KEY,
    f_name VARCHAR(255) NOT NULL ,
    f_owner_id BIGSERIAL REFERENCES t_users(f_id)
);

CREATE TABLE t_messages
(
    f_id BIGSERIAL PRIMARY KEY,
    f_author_id BIGSERIAL REFERENCES t_users (f_id),
    f_chatroom_id BIGSERIAL REFERENCES t_chatrooms (f_id),
    f_text VARCHAR(255),
    f_datetime TIMESTAMP(0) NOT NULL DEFAULT now()
);
