insert into users (id, email, password, role, created)
values ('f94e6c06-2ad6-4587-9860-1f65ed307d40', 'repeat@mail.ru',
        '$2a$10$Klroly6.NSXwmSypYZuYt.VI64nSdJTvmmrGartBHrLksNm57xTwS', 'USER', '2022-09-03 06:35:49.787960');

insert into refresh_token(id, token, expiry_date, user_id) values ('f94e6c06-2ad6-4587-9860-1f65ed307d10',
         'f94e6c06-2ad6-4587-9860-1f65ed307d11', '2021-09-05 21:03:27.638322', 'f94e6c06-2ad6-4587-9860-1f65ed307d40')
