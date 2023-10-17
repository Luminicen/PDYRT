#!/bin/bash

#inicia servidor unico en background
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.ftp.grpc.App --quiet &
s_pid=$!
sleep 3
a=$(cat ./ClienteA.txt)
b=$(cat ./ClienteB.txt)
#inicia cliente
mvn -DskipTests exec:java -Dexec.mainClass=pdytr.ftp.grpc.Client -Dexec.args="w ./db/experimentoB.txt 5 $a" &
ca_pid=$!
mvn -DskipTests exec:java -Dexec.mainClass=pdytr.ftp.grpc.Client -Dexec.args="w ./db/experimentoB.txt 5 $b" &
cb_pid=$!

wait $ca_pid
wait $cb_pid

kill $s_pid

