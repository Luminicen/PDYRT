#!/bin/bash


#----------------------server-------------------------------
#inicia 5 conexiones server desde vm1
#podria poner otro for mas afuera con el tamaño de buffer pero dsp pruebo
javac Server.java
port=3999
for size in 1000 10000 100000 1000000
do
	for i in 1 2 3 4 5 6 7 8 9 10
	do
		port=$((port + 1))
		echo $port
		java Server $port &
	done
done
