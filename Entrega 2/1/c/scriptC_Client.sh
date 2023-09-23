#!/bin/bash


#----------------------client-------------------------------

gcc -o client client.c

printf "Ingrese direccion IP del Servidor:\n"

read ip

port=3999
for size in 1000 10000 100000 1000000
do
	echo "--------------------------------------------" >> ./tiempo1.txt
	echo "tamaño de la entrada $size" >> ./tiempo1.txt
	echo "--------------------------------------------" >> ./tiempo1.txt
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		./client $ip $port $size >> ./tiempoC1.txt
	done
	sleep 3
done
