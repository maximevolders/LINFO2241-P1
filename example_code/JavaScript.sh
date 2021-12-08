#!/bin/bash
clear
rm Main.class FileManagement.class CryptoUtils.class 2> /dev/null
rm stats.csv 2> /dev/null

echo "-------------- Compiling files --------------"

#javac Main.java CryptoUtils.java FileManagement.java
javac Main.java FileManagement.java CryptoUtils.java
echo "ClientID,time\n" > stats.csv

echo "---------- Testing with $1 Clients ----------"

for (( j=1; j<=$1; j++ ))
do
	echo "------------- Client $j started -------------"
	sleep $(( RANDOM % 3 ))
	java Main $j &
done

wait

echo "--------- All clients are finished! ---------"
rm Main.class FileManagement.class CryptoUtils.class 2> /dev/null

echo "------------ Generating graphs.. ------------"
python3 graph.py $1 2> /dev/null
echo "--------------- Graphs ready! ---------------"
