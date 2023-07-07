--population districts
insert into district (name, code, archive) values ('Антоновский', '011', false);
insert into district (name, code, archive) values ('Берёзовый', '013', true);
insert into district (name, code, archive) values ('Борисовский', '019', false);
insert into district (name, code, archive) values ('Виленский', '028', true);
insert into district (name, code, archive) values ('Воскресенский', '033', false);
insert into district (name, code, archive) values ('Греческий', '046', false);
insert into district (name, code, archive) values ('Гусарский', '047', true);
insert into district (name, code, archive) values ('Демидовский', '049', false);
insert into district (name, code, archive) values ('Ежовский', '052', false);
insert into district (name, code, archive) values ('Жуковский', '055', false);
insert into district (name, code, archive) values ('Зеленый', '061', false);
insert into district (name, code, archive) values ('Игристый', '064', false);
--population farmers
insert into farmer (name, legal_form, inn, kpp, ogrn, district_id, registration_date, archive)
    values ('Агрофирма Антоновская', 'ЮЛ', '1234567890', '123456789', '1234567890123', 1, '2022-06-12', false);
insert into farmer (name, legal_form, inn, kpp, ogrn, district_id, registration_date, archive)
values ('Сибирские морозы', 'ЮЛ', '1264567890', '127456789', '1230567890123', 1, '2020-12-31', false);
insert into farmer (name, legal_form, inn, kpp, ogrn, district_id, registration_date, archive)
values ('Ежовские посевные', 'ИП', '1234567980', '123456879', '1234567890143', 9, '2022-07-23', false);
insert into farmer (name, legal_form, inn, kpp, ogrn, district_id, registration_date, archive)
values ('Иванов Петр Федорович', 'ФЛ', '7534567890', '453456789', '1238767890123', 6, '2023-03-01', false);
insert into farmer (name, legal_form, inn, kpp, ogrn, district_id, registration_date, archive)
values ('Агрокомплекс Зеленый', 'ЮЛ', '7534567120', '453116789', '1238760090123', 6, '2023-03-01', true);
--population fields
insert into farmer_district (farmer_id, district_id) values (1, 1);
insert into farmer_district (farmer_id, district_id) values (1, 3);
insert into farmer_district (farmer_id, district_id) values (1, 5);
insert into farmer_district (farmer_id, district_id) values (2, 9);
insert into farmer_district (farmer_id, district_id) values (3, 6);
insert into farmer_district (farmer_id, district_id) values (3, 12);
insert into farmer_district (farmer_id, district_id) values (4, 8);
insert into farmer_district (farmer_id, district_id) values (4, 11);