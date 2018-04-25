/**
 * Author:  rodrigobento
 * Created: Apr 23, 2018
 */

create table user (
    id serial primary key,
    email varchar(40) not null,
    senha varchar(30) not null,
    appId varchar(40),
    FOREIGN KEY (appId) REFERENCES provider (appId) ON DELETE CASCADE ON UPDATE CASCADE
);
