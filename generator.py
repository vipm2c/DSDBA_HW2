#!/usr/bin/python2.7

import random
import datetime
import calendar
import getopt
import sys
import psycopg2
from psycopg2 import sql

#2020-11-27 22:20:49
def generate():
    #date = datetime.datetime(random.randint(2019, 2019), random.randint(1, 1), random.randint(1, 5),random.randint(0, 23), random.randint(0, 59), random.randint(0, 59))
    #calendar.timegm(date.timetuple())
    return "%s,%s,%s" % (
        random.randint(1, 254),
        1510670000000 + random.randint(1, 9999999), #"%s-%s-%s %s:%s:%s" % (random.randint(2019, 2019), random.randint(1, 1), random.randint(1, 1),random.randint(0, 23), random.randint(0, 59), random.randint(0, 59)),#1510670000000 + random.randint(1, 100000)),
        random.randint(1, 999)
    )


def main():
    opts, args = getopt.getopt(sys.argv[1:], "c:s:", ["count=", "server="])
    count = 0
    server = ""
    for o, a in opts:
        if o in ("-c", "--count"):
            count = int(a)
        elif o in ("-s", "--server"):
            server = a
        else:
            print("unhandled option")

    conn = psycopg2.connect(dbname='hw2', user='postgres',
                            password='postgres', host=server)
    cursor = conn.cursor()
    for i in range(0, count):
        insert = sql.SQL('INSERT INTO "public"."data"(key,timestamp,value) VALUES ('+generate()+')')
        cursor.execute(insert)

    cursor.execute('SELECT * FROM "data"')
    records = cursor.fetchall()
    #for record in records:
    #   print(record)
    print('Total items in table: %d' %(len(records)))
    conn.commit()
    cursor.close()
    conn.close()


if __name__ == "__main__":
    main()
