## Задача 
1. Реализовать API для работы с реестром районов.

**_Атрибуты районов:_**
* название (required)
* код района
* статус архивности (true/false)

Реализовать следующие запросы:
* Получение списка районов, внесенных в реестр. Реализовать фильтрацию возвращаемого списка по названию и коду района
* Добавление района
* Изменение записи района
* Отправить в архив (архивные не выводим в реестр)

2. Реализовать реестр фермеров

**_Атрибуты фермеров:_**
* название организации (обязательное, фильтр)
* организационно-правовая форма (ЮР, ИП, ФЛ)
* ИНН (обязательное, фильтр)
* КПП
* ОГРН
* район регистрации (связь с районом/ID - района) (фильтр)
* районы посевных полей (множественный выбор, связь с районом)
* дата регистрации (фильтр)
* статус архивности (true/false) (фильтр)

Реализовать следующие запросы:
* Получение списка фермеров, внесенных в реестр. Реализовать фильтрацию возвращаемого списка по указанным атрибутам. 
* Получение данных по фермеру. По районам необходимо предоставлять наименования.
* Добавление фермера 
* Изменение записи фермера
* Отправить в архив (архивные не выводим в реестр)

_один район может содержать несколько фермеров (один ко многим); один фермер может иметь посевные поля в разных районах (многие ко многим)_

### Для запуска приложения вам понадобится установленный Docker и Maven
Быстрый старт, чтобы запустить проект у себя локально:
* Скачайте проект из облака или клонируйте из github себе в папку
* Перейдите в папку с проектом с помощью консоли (cmd, shell)
* Для сборки проекта введите ```mvn clean package```
* Запустите установку docker-образа ```docker build -t farmers:0.0.1 .```
* Запустите docker compose ```docker-compose up```

Чтобы убедиться, что приложение запустилось, перейдите к документации приложения [Swagger doc](http://localhost:8080/doc)

После запуска приложения база данных заполнится тестовыми данными.
* получить список райнов: http://localhost:8080/api/district
* получить список фермеров: http://localhost:8080/api/farmer
* остальные endpoints в [OpenAPI](http://localhost:8080/doc)

Используемые настройки для подключения к БД
```
  url: jdbc:postgresql://localhost:5432/farmers_db
  username: user
  password: password
```
