#!/bin/sh
npm install -g nodemon
nodemon -e java -x 'mvn package && java -jar target/cscc43-assignment-1.0-SNAPSHOT.jar'
