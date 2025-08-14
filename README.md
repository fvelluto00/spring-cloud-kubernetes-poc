# Spring Cloud Kubernetes PoC

## Prerequisites

Make sure you have installed the following:
- Docker Desktop (Docker daemon + Kubernetes)
- Helm

## Deploy ELK Stack for Logging and Monitoring

Deploy the ELK for centralized logging and monitoring of the microservices.

```
cd logging
./deploy-windows
```

## Build and Deploy Microservices

The PoC is made of two different microservices.
In order to make the build and deploy on local Kubernetes (on Windows), execute:

```
# build microservice a
cd microservice-a
./build-and-deploy-windows

# build microservice b
cd microservice-b
./build-and-deploy-windows
```
