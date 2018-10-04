#!/usr/bin/env bash
cp ../target/scala-2.11/cds-postgress-test.jar .
docker build . -t ajitchahal/postgress-test:v1
docker push ajitchahal/postgress-test:v1