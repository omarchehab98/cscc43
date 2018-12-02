# Running the code

## Bundling and compiling using javac

```sh
./bundle.sh > ./App.java && javac App.java && java App
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
