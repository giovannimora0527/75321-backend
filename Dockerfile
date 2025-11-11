FROM ubuntu:latest
LABEL authors="Samuel"

ENTRYPOINT ["top", "-b"]