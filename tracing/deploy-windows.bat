@echo off
REM ---------------------------------------------
REM Batch file to deploy Spring Boot app with
REM OpenTelemetry Collector and Zipkin on Kubernetes
REM ---------------------------------------------

REM Set namespace (change if needed)
set NAMESPACE=otel

echo Creating namespace %NAMESPACE%...
kubectl create namespace %NAMESPACE% 2>nul

REM Deploy OpenTelemetry Collector
echo Deploying OpenTelemetry Collector...
kubectl apply -n %NAMESPACE% -f otel-collector.yaml

REM Deploy Zipkin
echo Deploying Zipkin...
kubectl apply -n %NAMESPACE% -f zipkin.yaml

REM Wait a few seconds for pods to start
timeout /t 10

REM Show pods status
kubectl get pods -n %NAMESPACE%

echo Deployment finished.
echo You can port-forward Zipkin to localhost with:
echo kubectl port-forward svc/zipkin 9411:9411 -n %NAMESPACE%
pause
