### SpringBoot Web Application with Micrometer Prometheus metrics exposure example

## Prerequisites

 - ensure Java 19 installed locally
 - have a Docker Desktop with a DockerHub logged in and a Kubernetes cluster enabled

## Get up and running instruction

 - Build project with a `./gradlew build` command
 - Build and push a docker image of the just built app with `./docker-build.sh`
 - Run `kubectl apply -f kube/app.yaml` for spinning up the app Kubernetes service
 - Check that the app is running on localhost having `http://localhost:3080/read` endpoint responding

## App Prometheus metrics endpoint
 - Should be accessible by `http://localhost:3080/actuator/prometheus`
 - Metrics directly written by app logic, among others:
   - `api_read_get_total`
   - `method_timed_seconds` with labels