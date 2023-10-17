#!/bin/bash

#inicia servidor unico en background
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.example.grpc.App --quiet &
s_pid=$!

sleep 3
for i in 1 2 3 4 5 6 7 8 9 10
do
echo "--------------------------------------------" >> ./experimento1c.txt
echo "Resultado llamada nro $i" >> ./experimento1c.txt
echo "--------------------------------------------" >> ./experimento1c.txt
#inicia cliente
mvn -DskipTests exec:java -Dexec.mainClass=pdytr.example.grpc.Client  --quiet >> ./experimento1c.txt
done


kill $s_pid
