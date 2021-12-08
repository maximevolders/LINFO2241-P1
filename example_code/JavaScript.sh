#!/bin/bash
clear
rm stats.csv 2> /dev/null

echo "---------- Testing with $1 Clients ----------"

for (( j=1; j<=$1; j++ ))
do
	echo "------------- Client $j started -------------"
	sleep $(( RANDOM % 3 ))
	java Main $j &
done

wait

echo "--------- All clients are finished! ---------"

echo "------------ Generating graphs.. ------------"
python3 groupData.py $1 2> /dev/null
echo "--------------- Graphs ready! ---------------"
