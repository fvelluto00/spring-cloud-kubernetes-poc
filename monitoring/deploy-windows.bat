@echo off

echo.
echo Updating Helm Repo
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

echo.
echo Deploying Prometheus
helm upgrade --install prometheus prometheus-community/kube-prometheus-stack --namespace microservices

echo Successfully deployed Prometheus
pause