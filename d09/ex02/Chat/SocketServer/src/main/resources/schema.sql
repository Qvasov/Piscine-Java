CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE rooms(
    id BIGSERIAL PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL ,
    owner_id BIGSERIAL REFERENCES users(id)
);

CREATE TABLE messages
(
    id BIGSERIAL PRIMARY KEY,
    author_id BIGSERIAL REFERENCES users (id),
    room_id BIGSERIAL REFERENCES rooms (id),
    text VARCHAR(255),
    datetime TIMESTAMP(0) DEFAULT now()
);
