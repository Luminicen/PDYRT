#!/bin/bash

#levanta la vm en el directorio actual
vagrant up

vagrant ssh -c "gcc -o /vagrant/client /vagrant/client.c"
gcc -o server server.c



#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo

ip=$(hostname -I | awk '{print $1}') 

port=3999
for size in 1000 10000 100000 1000000
do
	echo "--------------------------------------------" >> ./tiempoC3_3.txt
	echo "tamaño de la entrada $size" >> ./tiempoC3_3.txt
	echo "--------------------------------------------" >> ./tiempoC3_3.txt
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		echo -n $port
		./server $port $size &
		vagrant ssh -c "/vagrant/client $ip $port $size >> /vagrant/tiempoC3_3.txt" 
	done
done
