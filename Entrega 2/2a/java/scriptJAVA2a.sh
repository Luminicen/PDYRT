#!/bin/bash

#levanta las vms en el directorio actual (provider el file de server.c y client.c)
vagrant up


#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo

port=3999
for size in 1000 10000 100000 1000000
do
	echo "--------------------------------------------" >> ./tiempoJAVA2a.txt
	echo "tamaño de la entrada $size" >> ./tiempoJAVA2a.txt
	echo "--------------------------------------------" >> ./tiempoJAVA2a.txt
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		echo $port
		vagrant ssh vm1 -c "cd /vagrant; java Server.java $port" &
		vagrant ssh vm2 -c "cd /vagrant; java Client.java 192.168.1.45 $port $size >> /vagrant/tiempoJAVA2a.txt"
	done
	sleep 3
done
