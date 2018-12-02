# Running the code

## Bundling and compiling using javac

Navigate to https://dev.mysql.com/downloads/connector/j/8.0.html download
**mysql-connector-java-5.1.47.zip** by clicking on **Looking for the latest GA Version?**
Unzip and copy **mysql-connector-java-5.1.47-bin.jar** to the root directory

```sh
./bundle.sh > ./App.java
javac App.java
java -cp ".:mysql-connector-java-5.1.47-bin.jar" App
```

## Running using maven

```sh
mvn clean install -U
mvn clean compile assembly:single
java -jar target/cscc43-assignment-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Automatic restart using maven

```
./watch.sh
```

# MySQL in Docker

```sh
docker-compose up
docker exec -i cscc43a_mysql_1 mysql -u root HL < schema.sql
```
