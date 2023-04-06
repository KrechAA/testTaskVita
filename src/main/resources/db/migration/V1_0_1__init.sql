create TABLE vita.users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name    VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


create TABLE vita.tasks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    author        BIGINT NOT NULL,
    status         VARCHAR(255) NOT NULL,
    text            VARCHAR(511) NOT NULL,
    date            TIMESTAMP
);

create TABLE vita.roles
(
    id          INT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE
);



create TABLE vita.idusers_idroles
(
    id_role        INT NOT NULL,
    id_user        BIGINT NOT NULL
);
alter table vita.idusers_idroles
    add CONSTRAINT fk_users_id FOREIGN KEY (id_user) REFERENCES users (id);
alter table vita.idusers_idroles
    add CONSTRAINT fk_roles_id FOREIGN KEY (id_role) REFERENCES roles (id);