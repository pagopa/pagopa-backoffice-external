#!/bin/bash

# install newman if not present
if [ $(npm list -g | grep -c newman) -eq 0 ]; then
  npm install -g newman
fi

# run the collection
newman run backoffice-external.postman_collection.json \
  --environment=local.postman_environment.json \
  --reporters cli,junit \
  --reporter-junit-export Results/results-TEST.xml
