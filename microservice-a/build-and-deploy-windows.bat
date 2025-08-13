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
REM Install PostgreSQL via Helm
REM ===============================
echo Installing PostgreSQL in namespace microservices...
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

helm upgrade --install microservice-a-postgres bitnami/postgresql ^
  --namespace microservices ^
  -f ./values.yaml

REM ===============================
REM Deploy microservice-a via Helm
REM ===============================
echo Deploying microservice-a via Helm...
helm upgrade --install microservice-a . ^
  --namespace microservices ^
  --set image.repository=microservice-a ^
  --set image.tag=latest

if errorlevel 1 (
    echo Helm deployment failed!
    exit /b 1
)

REM ===============================
REM Restarting pods
REM ===============================
kubectl delete pod -l app.kubernetes.io/name=postgresql,app.kubernetes.io/instance=microservice-a-postgres -n microservices
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
