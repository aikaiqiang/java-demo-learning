FROM openjdk:8-jre-alpine
LABEL maintainer="lijian <lijian@hancloud.cn>,aikaiqiang <aikaiqiang@hancloud.cn>"
LABEL description=" docker build -t base image"
ENV TZ=PRC
RUN apk add --update-cache ttf-dejavu dmidecode && rm -rf /var/cache/apk/*
COPY font/msyh.ttf /usr/share/fonts/msyh.ttf