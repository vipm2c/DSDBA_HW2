import os
import shutil
import re

print("=====PREPARE START=====")
os.system("yum -y install docker")
os.system("docker pull cassandra:3.0.0")
print("=====PREPARE DONE=====")
os.system("docker container run --name cassandra-0 -p 9042:9042 -d cassandra:3.0.0")
print("=====RUN CASSANDRA CLUSTER STARTED=====")
print("=====Start Export from Postgres with Sqoop=====")
shutil.rmtree('input')
os.system("/opt/sqoop/bin/sqoop import -fs local --connect jdbc:postgresql://192.168.234.129:5432/hw2?ssl=false --username postgres --password postgres --query \"SELECT * FROM public.data WHERE \$CONDITIONS ORDER BY (timestamp, id)\" --target-dir input -m 1")#--table data --target-dir input -m 1")
print("=====Scoop Done=====")
print("=====Collect SQL file=====")
files = []
for r, d, f in os.walk('input'):
    for fileName in f:
        if re.search(r'^part-m.*', fileName):
            files.append(os.path.join(r, fileName))
output = open("cassandra.sql", "w")
output.write("CREATE KEYSPACE IF NOT EXISTS log WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};\n")
output.write("CREATE TABLE IF NOT EXISTS log.timestamp (id int, key int, time timestamp, value int, PRIMARY KEY (id));\n")
count = 0
for fileName in files:
    inputFile = open(fileName, "r")
    for line in inputFile:
        newLine = "INSERT INTO log.timestamp (id, key, time, value) VALUES ("+re.sub("\n", "", line)+");"
        #os.system("docker exec -it cassandra-0 bash -c \'cqlsh localhost -e \\\""+newLine+"\\\"\'")
        output.write(newLine+"\n")
        count += 1
output.close()
print("=====FILE COLLECTED %s lines=====" % count)
os.system("docker exec cassandra-0 bash -c \"rm -rf /tmp/cassandra.sql\"")
os.system("docker cp cassandra.sql cassandra-0:/tmp")
print("=====FILE SENDED TO CASSANDRA=====")
os.system("docker exec cassandra-0 bash -c \"cqlsh localhost -e \\\"TRUNCATE log.timestamp\\\"\"")
os.system("docker exec cassandra-0 bash -c 'cqlsh -f /tmp/cassandra.sql'")
print("=====FILE EXECUTED IN CASSANDRA=====")
os.system("docker exec cassandra-0 bash -c 'cqlsh localhost -e \"SELECT COUNT(*) FROM log.timestamp;\"'")
cassandraIp = os.system("docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' cassandra-0")
os.system("echo $(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' cassandra-0)\"   cassandra\" >> /etc/hosts")

#docker rm -f $(docker ps -a -q)
#docker volume rm $(docker volume ls -q)

#echo "127.0.0.7   localhost localhost.localdomain localhost4 localhost4.localdomain4" -> /etc/hosts
#echo "::1         localhost localhost.localdomain localhost6 localhost6.localdomain6" >> /etc/hosts

#cqlsh cassandra -e "SELECT COUNT(*) FROM log.timestamp;"


