#!/bin/bash

#inicia servidor unico en background
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.example.grpc.App --quiet &
sleep 3
average=0
for i in 1 2 3 4 5 6 7 8 9 10
do
echo "--------------------------------------------" >> ./experimento1cprom.txt
echo "Resultado llamada nro $i" >> ./experimento1cprom.txt
echo "--------------------------------------------" >> ./experimento1cprom.txt
#inicia cliente
temp=$(mvn -DskipTests exec:java -Dexec.mainClass=pdytr.example.grpc.Client  --quiet)
average=$((average + temp))
echo $temp >> ./experimento1cprom.txt
done
killall java
average=$((average / 10))
echo "--------------------------------------------" >> ./experimento1cprom.txt
echo "Promedio en milisegundos:" >> ./experimento1cprom.txt
echo "--------------------------------------------" >> ./experimento1cprom.txt
echo $average >> ./experimento1cprom.txt
