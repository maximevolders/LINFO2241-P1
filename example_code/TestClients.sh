#!/bin/bash
clear

lambda=$(echo "-0.5")

#for i in {2,5,10,25,50,75,100}
#do
i=$(echo "100")
	echo "------ Starting test with $i clients -------"
	for (( j=1; j<=$i; j++ ))
	do
		rand=$(echo "scale=8; $RANDOM/32768" | bc)
		log=$(echo "l(1-$rand)" | bc -l)
		wait=$(echo "scale=8; $log/$lambda" | bc)
		sleep $wait
		
		java Main $j &
	done
	wait

	echo "--------------- Grouping data ---------------"
	python3 groupData.py $i "mesures_$(($j-1))_clients.csv"
#done
echo "-------------- Tests finished! --------------"
