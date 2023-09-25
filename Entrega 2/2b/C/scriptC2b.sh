#!/bin/bash

#levanta la vm en el directorio actual
vagrant up

vagrant ssh vm -c "gcc -o /vagrant/server /vagrant/server.c"
gcc -o client client.c


#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo

printf "Ingrese direccion IP del Servidor:\n"

read ip

port=4999
for size in 1000 10000 100000 1000000
do
	echo "--------------------------------------------" >> ./tiempoC2b.txt
	echo "tamaño de la entrada $size" >> ./tiempoC2b.txt
	echo "--------------------------------------------" >> ./tiempoC2b.txt
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		echo $port
		vagrant ssh vm -c "/vagrant/server $port $size" &
		./client $ip $port $size >> tiempoC2b.txt
	done
	sleep 3
done
