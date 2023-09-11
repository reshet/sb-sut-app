#!/bin/bash
docker image build -t app-sut-dockerjar:latest --build-arg CACHEBUST=$(date +%s) .