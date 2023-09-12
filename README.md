### SpringBoot Web Application with Micrometer Prometheus metrics exposure example

## Prerequisites

 - ensure Java 19 installed locally
 - have a Docker Desktop with a DockerHub logged in and a Kubernetes cluster enabled

## Get up and running instruction

 - Build project with a `./gradlew build` command
 - Build and push a docker image of the just built app with `./docker-build.sh`
 - Run `kubectl apply -f kube/app.yaml` for spinning up the app Kubernetes service
 - Check that the app is running on localhost having `http://localhost:3080/read` endpoint responding

### App Prometheus metrics endpoint
 - Should be accessible by `http://localhost:3080/actuator/prometheus`
 - Metrics directly written by app logic, among others:
   - `api_read_get_total`
   - `method_timed_seconds` with labels

## Install Prometheus and Grafana on local Kubernetes cluster

  Detailed guides and docs could be taken from here:
   - Prometheus setup: https://devopscube.com/setup-prometheus-monitoring-on-kubernetes/
   - Grafana setup: https://grafana.com/docs/grafana/latest/setup-grafana/installation/kubernetes/

  Execute the following commands in terminal in order to get Grafana and Prometheus services
  running:
   - `kubectl create namespace monitoring`
   - `kubectl create -f kube/prometheus/clusterRole.yaml`
   - `kubectl create -f kube/prometheus/config-map.yaml`
   - `kubectl create  -f kube/prometheus/prometheus-deployment.yaml`
   - `kubectl create  -f kube/prometheus/prometheus-service.yaml`
   - `kubectl create  -f kube/grafana.yaml`

  Make sure that deployments are running:
   - `kubectl get deployments --namespace=monitoring`
   - see Prometheus running and observing /actuator/prometheus target successfully: `http://localhost:30000/targets?search=actuator`
   - see Grafana is up and running: `http://localhost:3000/

  Wire Grafana to Prometheus:
   - open `http://localhost:3000/connections/add-new-connection`
   - point to the server url as this one `http://prometheus-service:8085`, leave the rest to defaults

## Adding Grafana Dashboard exploring app metrics
  In order to add an example dashboard reading metrics configured, simply import the Dashboard from
  `grafana/example_dashboard.json` project file.

## Running Gatling load script
  Example load kotlin script together with enclosing package folder is located in `load/` project folder.
  Simply copy-paste it to the `../user-files/simulations/` folder of your local Gatling installation,
  and chose it when running from gatling command line command.
     