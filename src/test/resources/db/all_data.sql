insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d41', 'user1@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d42', 'user2@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

---

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d31', 'ad1', 'my ad', true,
       '2022-09-05 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d42');

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d32', 'ad2', 'my ad', true,
        '2022-09-04 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d41');

insert into advertisement (id, title, content, is_active, created, is_deleted, user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d33', 'ad3', 'my ad', true,
        '2022-09-03 00:35:49.787960', false, 'f94e6c06-2ad6-4587-9860-1f65ed307d42');

---

insert into file (id, name) values ('f94e6c06-2ad6-4587-9860-1f65ed307d50', 'link');
insert into ad_photo values ('f94e6c06-2ad6-4587-9860-1f65ed307d31', 'f94e6c06-2ad6-4587-9860-1f65ed307d50');

---

insert into deal (id, ad_id, user_id) values ('f94e6c06-2ad6-4587-9860-1f65ed307d11',
             'f94e6c06-2ad6-4587-9860-1f65ed307d33', 'f94e6c06-2ad6-4587-9860-1f65ed307d41');

insert into deal (id, ad_id, user_id) values ('f94e6c06-2ad6-4587-9860-1f65ed307d12',
            'f94e6c06-2ad6-4587-9860-1f65ed307d31', 'f94e6c06-2ad6-4587-9860-1f65ed307d42');

