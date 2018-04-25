/**
 * Author:  rodrigobento
 * Created: Apr 21, 2018
 */

create table provider (
    appId varchar(40) primary key,
    host varchar(40) not null,
    name varchar(40) not null,
    port int not null
);

create table user (
    id serial primary key,
    email varchar(40) not null,
    senha varchar(30) not null,
    appId varchar(40),
    FOREIGN KEY (appId) REFERENCES provider (appId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Alterar localhost quando implantar no docker, para o valor do network
-- INSERT INTO provider (appId, host, name, port) VALUES ('sd-provider', 'localhost', 'rmi:/provider', 1095);
INSERT INTO provider (appId, host, name, port) VALUES ('sd-provider', 'provider', 'rmi:/provider', 1095);
INSERT INTO user (email, senha, appId) VALUES ('rodrigo@gmail.com', '123', 'sd-provider');

create table token (
    value varchar(60) primary key,
    isValid boolean,
    id_user int,
    created timestamp,
    appId varchar(40),
    FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (appId) REFERENCES provider (appId) ON DELETE CASCADE ON UPDATE CASCADE
);
