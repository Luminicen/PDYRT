#!/bin/bash

#levanta las vms en el directorio actual (provider el file de server.c y client.c)
vagrant up

vagrant ssh vm1 -c "gcc -o /vagrant/server /vagrant/server.c"
vagrant ssh vm2 -c "gcc -o /vagrant/client /vagrant/client.c"


#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo

port=3999
for size in 1000 10000 100000 1000000
do
	echo "--------------------------------------------" >> ./tiempo2a.txt
	echo "tamaño de la entrada $size" >> ./tiempo2a.txt
	echo "--------------------------------------------" >> ./tiempo2a.txt
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		echo $port
		vagrant ssh vm1 -c "/vagrant/server $port $size" &
		vagrant ssh vm2 -c "/vagrant/client 192.168.1.45 $port $size >> /vagrant/tiempo2a.txt"
	done
	sleep 3
done
