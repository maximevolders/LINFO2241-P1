#!/bin/bash
clear

for i in {2,5,10,25,50,75,100}
do
	echo "------ Starting test with $i clients -------"
	for (( j=1; j<=$i; j++ ))
	do
		sleep $(( RANDOM % 3 ))
		java Main $j &
	done
	wait

	echo "--------------- Grouping data ---------------"
	python3 groupData.py $i "mesures_$(($j-1))_clients.csv"
done
echo "-------------- Tests finished! --------------"
