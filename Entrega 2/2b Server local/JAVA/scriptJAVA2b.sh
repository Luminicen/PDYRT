#!/bin/bash

#levanta la vm en el directorio actual (provider el file de server.c y client.c)
vagrant up
ip=$(hostname -I | awk '{print $1}') 


port=3999
for size in 1000 10000 100000 1000000
do
echo "--------------------------------------------" >> ./tiempoJAVA2b.txt
echo "tamaño de la entrada $size" >> ./tiempoJAVA2b.txt
echo "--------------------------------------------" >> ./tiempoJAVA2b.txt
for i in 1 2 3 4 5 6 7 8 9 10
do
port=$((port + 1))
printf "\n"
echo $port
printf "\n"
java Server.java $port &
vagrant ssh -c "cd /vagrant; java Client.java $ip $port $size >> /vagrant/tiempoJAVA2b.txt" 
done
done
