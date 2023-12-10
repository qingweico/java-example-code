drop table if exists `user`;
create table `user` (
    `id` bigint not null AUTO_INCREMENT,
    `name` varchar(20) not null,
    `uname` varchar(20) not null,
    `pwd` char(64) not null,
    `salt` char(8),
    `tel` char(20),
    `address` varchar(50) default null,
    `avatar` varchar(100) default null,
    `ip` int default 0,
    `created` datetime default current_timestamp,
    `modified` datetime default current_timestamp,
    primary key(`id`)
);

drop table if exists `post`;
create table post (
    `id` bigint not null AUTO_INCREMENT,
    `user_id` bigint not null,
    `name` varchar(20) not null,
    `title` char(12) not null,
    `info` text default null,
    `avatar` varchar(100) default null,
    `approve` int default 0,
    `dislike` int default 0,
    `state` tinyint default 0,
    `created` datetime default current_timestamp,
    `modified` datetime default current_timestamp,
    primary key(id)
);
