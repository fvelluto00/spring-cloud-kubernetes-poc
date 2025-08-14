@echo off
echo Deploying ELK stack to Kubernetes...

kubectl apply -f namespace.yaml
kubectl apply -f filebeat-rbac.yaml
kubectl apply -f filebeat-configmap.yaml
kubectl apply -f filebeat-daemonset.yaml
kubectl apply -f elasticsearch.yaml
kubectl apply -f logstash-configmap.yaml
kubectl apply -f logstash.yaml
kubectl apply -f kibana.yaml

echo ELK stack deployed.
pause
