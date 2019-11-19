#!/usr/bin/env bash
unzip -d svc *-1.0-SNAPSHOT.zip
mv svc/*/* svc/
rm svc/bin/*.bat
mv svc/bin/* svc/bin/start