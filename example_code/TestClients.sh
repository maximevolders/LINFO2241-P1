#!/bin/bash
clear

lambda=$(echo "-0.5")

echo "------ Starting test with 50 clients -------"
for (( j=1; j<=50; j++ ))
do
	rand=$(echo "scale=8; $RANDOM/32768" | bc)
	log=$(echo "l(1-$rand)" | bc -l)
	wait=$(echo "scale=8; $log/$lambda" | bc)
	sleep $wait
	
	java Main $j &
done
wait

echo "--------------- Grouping data ---------------"
python3 groupData.py 50 "mesures_$(($j-1))_clients.csv"
echo "-------------- Tests finished! --------------"
