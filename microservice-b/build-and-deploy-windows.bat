@echo off
setlocal

REM ===============================
REM Build Docker image
REM ===============================
echo Building Docker image: microservice-b:latest ...
docker build -t microservice-b:latest .
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
REM Install PostgreSQL via Helm
REM ===============================
echo Installing PostgreSQL in namespace microservices...
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

helm upgrade --install microservice-b-postgres bitnami/postgresql ^
  --namespace microservices ^
  -f ./values.yaml

REM ===============================
REM Deploy microservice-b via Helm
REM ===============================
echo Deploying microservice-b via Helm...
helm upgrade --install microservice-b . ^
  --namespace microservices ^
  --set image.repository=microservice-b ^
  --set image.tag=latest

if errorlevel 1 (
    echo Helm deployment failed!
    exit /b 1
)

REM ===============================
REM Restarting pods
REM ===============================
kubectl delete pod -l app.kubernetes.io/name=postgresql,app.kubernetes.io/instance=microservice-b-postgres -n microservices
kubectl delete pod -l app=microservice-b -n microservices

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
