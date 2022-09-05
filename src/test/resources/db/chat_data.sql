insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d40', 'user@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d41', 'user1@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d42', 'user2@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

---

insert into chat (id, first_user_id, second_user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d50', 'f94e6c06-2ad6-4587-9860-1f65ed307d40',
        'f94e6c06-2ad6-4587-9860-1f65ed307d41');

insert into chat (id, first_user_id, second_user_id)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d51', 'f94e6c06-2ad6-4587-9860-1f65ed307d42',
        'f94e6c06-2ad6-4587-9860-1f65ed307d41');

---

insert into chat_message (id, sender_id, chat_id, status, content)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d60', 'f94e6c06-2ad6-4587-9860-1f65ed307d41',
        'f94e6c06-2ad6-4587-9860-1f65ed307d50', 'OLD', 'good bye');

insert into chat_message (id, sender_id, chat_id, status, content)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d61', 'f94e6c06-2ad6-4587-9860-1f65ed307d41',
        'f94e6c06-2ad6-4587-9860-1f65ed307d50', 'NEW', 'hello');

insert into chat_message (id, sender_id, chat_id, status, content)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d62', 'f94e6c06-2ad6-4587-9860-1f65ed307d41',
        'f94e6c06-2ad6-4587-9860-1f65ed307d50', 'NEW', 'what is up?');
