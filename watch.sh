#!/bin/sh
npm install -g nodemon
nodemon -e java -x 'mvn clean compile assembly:single && java -jar target/cscc43-assignment-1.0-SNAPSHOT-jar-with-dependencies.jar'