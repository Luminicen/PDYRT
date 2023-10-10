#!/bin/bash

#levanta las vms en el directorio actual (provider el file de server.c y client.c)
vagrant up

ip=$(vagrant ssh vm1 -c "hostname -I" | awk '{print $2}') 

#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo

port=6050
for size in 1000 10000 100000 1000000
do
echo "--------------------------------------------" >> ./tiempoJAVA3_4.txt
echo "tamaño de la entrada $size" >> ./tiempoJAVA3_4.txt
echo "--------------------------------------------" >> ./tiempoJAVA3_4.txt
for i in 1 2 3 4 5 6 7 8 9 10
do
port=$((port + 1))
printf "\n"
echo $port
printf "\n"
echo $1
printf "\n"
vagrant ssh vm1 -c "cd /vagrant; java Server.java $port" &
sleep 1
vagrant ssh vm2 -c "cd /vagrant; java Client.java $ip $port $size >> /vagrant/tiempoJAVA3_4.txt"
done
done
