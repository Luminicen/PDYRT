#!/bin/bash

#inicia servidor unico en background
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.ftp.grpc.App --quiet &
s_pid=$!
sleep 3

#inicia cliente
mvn -DskipTests exec:java -Dexec.mainClass=pdytr.ftp.grpc.Client -Dexec.args="f ./db/reciboFatality.mp4 119487 ./fatality.mp4"

diff -s ./db/reciboFatality.mp4 ./fatality.mp4

kill $s_pid

