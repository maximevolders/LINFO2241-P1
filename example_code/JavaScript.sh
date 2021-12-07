#!/bin/bash
clear
rm *.class 2> /dev/null
rm stats.csv 2> /dev/null

echo "-------------- Compiling files --------------"

#javac Main.java CryptoUtils.java FileManagement.java
javac *.java
echo "ClientID,time\n" > stats.csv

echo "-------------- Starting server --------------"
java ServerMain &
sleep 5

echo "---------- Testing with $1 Clients ----------"

for (( j=1; j<=$1; j++ ))
do
	echo "------------- Client $j started -------------"
	sleep $(( RANDOM % 3 ))
	java Main $j &
done

wait

echo "--------- All clients are finished! ---------"
rm *.class 2> /dev/null

echo "------------ Generating graphs.. ------------"
python3 graph.py $1 2> /dev/null
echo "--------------- Graphs ready! ---------------"
