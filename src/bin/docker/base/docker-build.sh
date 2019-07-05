#!/bin/sh
version_no=$1
echo "==== starting to build base image ===="
docker build --no-cache -t 192.168.0.24:1180/magic/base:$version_no .
docker tag 192.168.0.24:1180/magic/base:$version_no 192.168.0.24:1180/magic/base:latest
docker login 192.168.0.24:1180 -u admin -p hancloud12345
docker push 192.168.0.24:1180/magic/base:$version_no
docker push 192.168.0.24:1180/magic/base:latest
echo "==== end to build base image ===="