#!/usr/bin/env bash
sbt dist
set -x
rm -rf svc
unzip -d svc target/universal/*-1.0-SNAPSHOT.zip
mv svc/*/* svc/
rm svc/bin/*.bat
rm -rf svc/share
mv svc/bin/* svc/bin/start

docker stop request-count
docker rm request-count
docker build . -t cdsx.docker.repositories.sap.ondemand.com/cdsx/kiwis/request-count:0.2

docker push  cdsx.docker.repositories.sap.ondemand.com/cdsx/kiwis/request-count:0.2
#docker run --name request-count -p 9000:9000 cdsx.docker.repositories.sap.ondemand.com/cdsx/kiwis/request-count:0.2

