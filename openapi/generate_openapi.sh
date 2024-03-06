#!/bin/bash

if [[ "$(pwd)" =~ .*"openapi".* ]]; then
    cd ..
fi

if [ $(npm list -g | grep -c openapi-to-postmanv2) -eq 0 ]; then
  npm i -g openapi-to-postmanv2
fi

mvn test -Dtest=OpenApiGenerationTest


openapi2postmanv2 -s ./openapi/openapi.json -o ./postman-collection/backoffice-external.postman_collection.json \
-O alwaysInheritAuthentication=true,requestNameSource=operationId,parametersResolution=Example
