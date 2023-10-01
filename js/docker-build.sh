#!/bin/bash
docker image build -t app-sut-nodejs:latest --build-arg CACHEBUST=$(date +%s) .