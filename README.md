# MFA Service

A microservice that issues and verifies Multi-Factor Authentication (MFA) emails.

## Description

The microservice has an API endpoint that accepts an email address as
input and sends an MFA email to that address with a unique time-sensitive verification code.
There is another API endpoint where users can verify the MFA
code sent to their email. 

## Getting Started

### Dependencies

* Java 17
* Maven
* Docker

### Starting the Application

* Edit the docker-compose.yaml file 
  * Set the email username and password. This is the be email that sends the MFA codes.
  * Set the email host and port. Currently, this is defaulted to Gmail.

* Build the application using Docker Compose
```
docker-compose build
```

* Run the application with Docker Compose
```
docker-compose up -d
```

### Using the Application 

* Send a request to generate an MFA code to /mfa/generate
```
curl -X POST --location "http://localhost:8080/mfa/generate" \
    -H "Content-Type: application/json" \
    -d '{
          "email": "<YOUR EMAIL>"
        }'
```

* Send a request to verify the MFA code to /mfa/generate
```
curl -X POST --location "http://localhost:8080/mfa/verify" \
    -H "Content-Type: application/json" \
    -d '{
          "email": "<YOUR EMAIL>",
          "mfaCode": "<MFA CODE>"
        }'
```

## Authors
Milen Orfeev  