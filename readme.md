# Лабораторная работа 2  
  
generator.py - python-скрипт генерации данных.\
sqoop.py - python-скрипт переноса данных из SQL в Cassandra\
src - исходники. \
pom.xml - конфигурационный файл для сборки.

# Требования  
1. RPM-based дистрибутив Linux с ядром версии не ниже 3.10 с доступом в интернет.  
2. JDK 1.8
3. PostgreSQL
4. Sqoop
5. Cassandra
6. Ignite
7. Maven
8. Python

# Зависимости Python
```console
    pip install psycopg2-binary 
    pip install cassandra-driver
    pip install pysqoop
```

# Скрипт создания БД postgres
```sql
    CREATE DATABASE "hw2"
    WITH
      OWNER = "postgres"
      TEMPLATE = "postgres"
      ENCODING = 'UTF8'
      TABLESPACE = "pg_default"
    ;
```

# Создание таблицы в БД postgres
```sql
    CREATE TABLE "public"."data" (
      "id" serial NOT NULL,
      "key" int8,
      "timestamp" int8,
      "value" int8,
      PRIMARY KEY ("id")
    );
```

# Сборка  
1. Выкачиваем директорию src и файл pom.xml

# Запуск  

1. Запустить скрипт sqoop.py  
    ```console
    sqoop.py -s <IP адрес БД Postgres> -l <Login> -p <Password>
    ```
2. Запустить приложение через Idea
3. Зайти в браузер по следующему адресу: http://localhost:8080/fetch/cassandra/ignite/<Указание временной отметки по которой необходимо группировать>

# Генератор данных 
Для удобства проверки предоставлен скрипт генерации тестовых данных.  

1. Выкачать скрипт generator.py  
4. Находясь в директории проекта запустить генератор командой:  
    ```console
    generator.py -c <Количество генерируемых строк> -s <IP адрес БД Postgres> -l <Login> -p <Password>
    ```
 
  
