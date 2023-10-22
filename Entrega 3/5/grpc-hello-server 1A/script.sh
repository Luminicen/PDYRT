#!/bin/bash

#inicia servidor unico en background
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.example.grpc.App --quiet &
s_pid=$!
sleep 3
#inicia clientes
for size in 1000 10000 100000 1000000
do
echo "--------------------------------------------" >> ./tiempos_grpc5A.txt
echo "Muestra tamaño $size" >> ./tiempos_grpc5A.txt
echo "--------------------------------------------" >> ./tiempos_grpc5A.txt
for i in 1 2 3 4 5 6 7 8 9 10
do
mvn -DskipTests exec:java -Dexec.mainClass=pdytr.example.grpc.Client -Dexec.args="$size" --quiet >> ./tiempos_grpc5A.txt
done
done
kill $s_pid

