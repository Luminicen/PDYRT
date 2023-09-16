#!/bin/bash

#levanta las vms en el directorio actual (provider el file de server.c y client.c)
vagrant up

#compila cliente y servidor
gcc -o server server.c
gcc -o client client.c

vagrant ssh vm1 -c "gcc -o /vagrant/server /vagrant/server.c"
vagrant ssh vm2 -c "gcc -o /vagrant/client /vagrant/client.c"


#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo
for port in 4000 40001 40002 40003 40004
do
	vagrant ssh vm1 -c "/vagrant/server $port 1000 &"
	vagrant ssh vm2 -c "/vagrant/client 192.168.122.45 $port 1000 >> /vagrant/tiempo2a.txt"
	
done
