# Лабораторная работа 2  
  
Generator.py - python-скрипт генерации данных.\
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
      "id" int8 NOT NULL,
      "key" int8,
      "timestamp" timestamp,
      "value" int8,
      PRIMARY KEY ("id")
    );
```

# Сборка  
1. Выкачиваем директорию src и файл pom.xml  
2. В директории сборки запускаем   
mvn install  
для тестирования и сборки. Итоговый JAR будет расположен в директории target.  

# Запуск  

1. Запустить сервис Kafka  
2. Запустить master и slave Spark  
3. Запустить приложение  
    ```console
    foo@bar:spark-submit --class 'MetricAggregationApp' --master '<spark master URL>'\
    --deploy-mode cluster \
    <jar-file> <cfg системы> <входной файл с данными> <выходная директория> <масштаб>
    ```

# Генератор данных 
Для удобства проверки предоставлен скрипт генерации тестовых данных.  

1. Выкачать директорию FakeMetricGen.  
4. Находясь в директории FakeMetricGen запустить генератор командой:  
    ```console
    foo@bar:~/fakelog$ perl ./fakeMetric.pl <количество строк> <процент неверных строк> <путь выходного файла>  
    ```
 
## Текущий статус
|Требование лабораторной  |Статус   |
|---|---|
|2.	Program which aggregate raw metrics into selected scale.  Data input format: metricId, timestamp, value  Data output format: metricId, timestamp, scale, value  |  + |
|1. Kafka producer   | +  |
|3. Ignite Native Persistence   | + |
|1. Spark RDD | +  |
  
|Требование репорта  |Статус   |
|---|---|
|1.	IDE agnostic build: Maven, Ant, Gradle, sbt, etc (10 points)| + |
|2.	Unit tests are provided (20 points) | - |
|3.	Code is well-documented (10 points) | + |
|4.	Script for input file generation or calculation setup and control (10 points) | + |
|5.	Working application that corresponds business logic, input/output format and additional Requirements that has been started on cluster (30 points) | + |
|6. The relevant report was prepared (20 points) | + |
