# BackOffice External

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=pagopa_pagopa-backoffice-external&metric=alert_status)](https://sonarcloud.io/dashboard?id=pagopa_pagopa-backoffice-external)
[![Integration Tests](https://github.com/pagopa/pagopa-backoffice-external/actions/workflows/integration_test.yml/badge.svg?branch=main)](https://github.com/pagopa/pagopa-backoffice-external/actions/workflows/integration_test.yml)

Microservice with external APIs of PagoPA Backoffice

- [BackOffice External](#backoffice-external)
    * [Api Documentation 📖](#api-documentation-)
    * [Api Development 💻](#api-development-)
      * [Add new api group](#add-new-api-group)
    * [Technology Stack](#technology-stack)
    * [Start Project Locally 🚀](#start-project-locally-)
        + [Prerequisites](#prerequisites)
        + [Run docker container](#run-docker-container)
    * [Develop Locally 💻](#develop-locally-)
        + [Prerequisites](#prerequisites-1)
        + [Run the project](#run-the-project)
            - [Local Environment](#local-environment)
        + [Spring Profiles](#spring-profiles)
        + [Testing 🧪](#testing-)
            - [Unit testing](#unit-testing)
            - [Integration testing](#integration-testing)
            - [Performance testing](#performance-testing)
    * [Contributors 👥](#contributors-)
        + [Maintainers](#maintainers)

---

## Api Documentation 📖

See the [OpenApi 3 here.](https://editor.swagger.io/?url=https://raw.githubusercontent.com/pagopa/pagopa-backoffice-external/main/openapi/openapi.json)

---

## Api Development 💻

This project use code-first approach for api development:

documentation is generated starting from code annotations that is: openapi descriptor is generated from code swagger annotations.

### Add new api group

That said, in order to create a new api you have to:

1. implement controller or add new api to existing one along with all DTOs (request, response, parameters...)
2. add all documentation (ex: io.swagger.v3.oas.annotations.*) annotations, bean validation annotations and so one
3. check into test application.properties file for `springdoc.group-configs` configuration and add api to already created openapi and/or create a new group
4. (only for new openapi configurations) configure `OpenApiGenerationTest` class for wanted openapi file to generate based 
5. (only for new openapi configurations) check into `OpenApiConfig` class base path configurations and add appropriate ones for newly added api
6. generate openapi executing the `openapi/generate_openapi.sh` -> this step will re-generate each openapi files starting from code definitions
7. check for generated openapi and reiterate step 6 after each modification to see updated openapi descriptor being generated
8. (optional) - add terraform resource/modules to expose api on Azure Api Management into the repo `infra` folder

---

## Technology Stack

- Java 17   
- Spring Boot
- Spring Web
- Hibernate
- JPA
- Maven 3

---

## Start Project Locally 🚀

### Prerequisites

- docker

### Run docker container

from `./docker` directory

`sh ./run_docker.sh local`

ℹ️ Note: for PagoPa ACR is required the login `az acr login -n <acr-name>`

---

## Develop Locally 💻

### Prerequisites

- git
- maven
- jdk-17

### Run the project

Start the springboot application with this command:

`mvn spring-boot:run -Dspring.profiles.active=local`

#### Local Environment

👀 You need to put in your local environment some variables

| VARIABLE                                | USAGE                                       |  DEFAULT VALUE   |
|-----------------------------------------|---------------------------------------------|:----------------:|
| `MONGODB_CONNECTION_URI`                | Connection string to the Backoffice MongoDB |                  |
| `MONGODB_NAME`                          | Name of the Backoffice MongoDB              | pagopaBackoffice |
| `PAGOPA_APIM_API_CONFIG_API_KEY_PAGOPA` | APIM Key to connect to Api-config           |                  |

### Spring Profiles

- **local**: to develop locally.
- _default (no profile set)_: The application gets the properties from the environment (for Azure).

### Testing 🧪

#### Unit testing

To run the **Junit** tests:

`mvn clean verify`

#### Integration testing

From `./integration-test/src`

1. `yarn install`
2. `yarn test`

#### Performance testing

install [k6](https://k6.io/) and then from `./performance-test/src`

1. `k6 run --env VARS=local.environment.json --env TEST_TYPE=./test-types/load.json main_scenario.js`

---

## Contributors 👥

Made with ❤️ by PagoPa S.p.A.

### Maintainers

See `CODEOWNERS` file
