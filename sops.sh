#!/bin/bash

#set -x

action=$1

azurekvurl=`az keyvault key show --name backoffice-sops-key --vault-name pagopa-d-selfcare-kv --query key.kid | sed 's/"//g'`

if [ "$action" == "enc" ]; then
  sops --encrypt --azure-kv $azurekvurl --input-type dotenv --output-type  dotenv ./src/main/resources/application-local.properties > ./src/main/resources/application-local.properties.enc
fi;

if [ "$action" == "dec" ]; then
  sops --decrypt --azure-kv $azurekvurl --input-type dotenv --output-type dotenv ./src/main/resources/application-local.properties.enc > ./src/main/resources/application-local.properties
fi;


echo 'done'
