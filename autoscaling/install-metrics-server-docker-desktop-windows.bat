@echo off
echo ==========================================
echo Installing metrics-server in Docker Desktop Kubernetes...
echo ==========================================

REM Step 1: install metrics-server
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

echo Waiting 10 seconds for metrics-server to create pods...
timeout /t 10

REM Step 2: patch deployment to add --kubelet-insecure-tls
kubectl -n kube-system patch deployment metrics-server --type=json -p "[{\"op\": \"add\", \"path\": \"/spec/template/spec/containers/0/args/-\", \"value\": \"--kubelet-insecure-tls\"}]"

REM Step 3: restart deployment
kubectl -n kube-system rollout restart deployment metrics-server

echo ==========================================
echo Metrics-server installation complete.
echo Wait 1-2 minutes and then run:
echo   kubectl top nodes
echo   kubectl top pods -n microservices
echo ==========================================
pause
