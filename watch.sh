#!/bin/sh
npm install -g nodemon
nodemon -e java -x 'mvn package && java -cp target/cscc43-assignment-1.0-SNAPSHOT.jar cscc43.assignment.App'
