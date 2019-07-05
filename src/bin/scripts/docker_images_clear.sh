#!/bin/sh
NUM=`docker images -q -f dangling=true | wc -l`
if [ $NUM -gt 0 ];then
 docker rmi $(docker images -q -f dangling=true)
fi

NUM2=`docker images | grep magic-.*-center | wc -l`
if [ $NUM2 -gt 0 ];then
 docker images | grep magic-.*-center | awk '{print $1":"$2}' | xargs docker rmi
fi

NUM3=`docker images | grep magic-.*-admin | wc -l`
if [ $NUM3 -gt 0 ];then
 docker images | grep magic-.*-admin | awk '{print $1":"$2}' | xargs docker rmi
fi

NUM4=`docker images | grep magic-.*-server | wc -l`
if [ $NUM4 -gt 0 ];then
 docker images | grep magic-.*-server | awk '{print $1":"$2}' | xargs docker rmi
fi
