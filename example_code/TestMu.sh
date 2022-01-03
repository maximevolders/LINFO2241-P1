#!/bin/bash
clear

lambda=$(echo "-0.5")

for i in {-0.5,-1,-2,-5}
do
	echo "------ Starting test with mu = $i -------"
	for (( j=1; j<=50; j++ ))
	do
		rand=$(echo "scale=8; $RANDOM/32768" | bc)
		log=$(echo "l(1-$rand)" | bc -l)
		wait=$(echo "scale=8; $log/$i" | bc)
		sleep $wait
		
		java Main $j &
	done
	wait

	echo "--------------- Grouping data ---------------"
	python3 groupData.py 50 "mesures_mu_$i.csv"
done
echo "-------------- Tests finished! --------------"
