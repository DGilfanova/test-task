insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d40', 'user@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d41', 'user1@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

---

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d30', 'special ad', 'my ad', true,
       '2022-09-05 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d41');

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d31', 'new ad', 'my ad', true,
        '2022-09-04 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d40');

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d32', 'super new ad', 'my ad', true,
        '2022-09-03 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d40');

---

insert into file (id, name) values ('f94e6c06-2ad6-4587-9860-1f65ed307d50', 'link');
insert into ad_photo values ('f94e6c06-2ad6-4587-9860-1f65ed307d30', 'f94e6c06-2ad6-4587-9860-1f65ed307d50');

---

insert into deal (id, ad_id, user_id) values ('475e40e8-c396-4bb2-b841-7365cafbaa7a',
             'f94e6c06-2ad6-4587-9860-1f65ed307d32', 'f94e6c06-2ad6-4587-9860-1f65ed307d40');

insert into deal (id, ad_id, user_id) values ('475e40e8-c396-4bb2-b841-7365cafbaa7b',
            'f94e6c06-2ad6-4587-9860-1f65ed307d30', 'f94e6c06-2ad6-4587-9860-1f65ed307d40');

