#!/bin/bash
#inicia clientes
for size in 1000 10000 100000 1000000
do
echo "--------------------------------------------" >> ./tiempos_java5A.txt
echo "Muestra tamaño $size" >> ./tiempos_java5A.txt
echo "--------------------------------------------" >> ./tiempos_java5A.txt
for i in 1 2 3 4 5 6 7 8 9 10
do
java Server.java 8000 $size &
sleep 3
java Client.java localhost 8000 $size >> ./tiempos_java5A.txt
done
done

