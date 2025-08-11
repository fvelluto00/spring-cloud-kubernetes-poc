@echo off
setlocal

REM ===============================
REM Build Docker image
REM ===============================
echo Building Docker image: microservice-a:latest ...
docker build -t microservice-a:latest .
if errorlevel 1 (
    echo Docker build failed!
    exit /b 1
)

REM ===============================
REM Create namespace (if not exists)
REM ===============================
kubectl get namespace microservices >nul 2>&1
if errorlevel 1 (
    echo Creating namespace: microservices ...
    kubectl create namespace microservices
)

REM ===============================
REM Deploy to Kubernetes
REM ===============================
echo Deploying microservice-a to Kubernetes...
kubectl apply -f .\k8s\ --namespace=microservices
if errorlevel 1 (
    echo Kubernetes deployment failed!
    exit /b 1
)

REM ===============================
REM Restarting pods
REM ===============================
kubectl delete pod -l app=microservice-a -n microservices
if errorlevel 1 (
    echo Kubernetes pod restart failed!
    exit /b 1
)

REM ===============================
REM Show deployment status
REM ===============================
echo Checking pods...
kubectl get pods -n microservices

echo.
echo Deployment completed successfully!
