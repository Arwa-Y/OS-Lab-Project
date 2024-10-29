#!/bin/bash


for j in $(seq 1 $#); do

	for i in {1..3}; do	

		if ping -c 1 -W 5 ${!j} > /dev/null
		then
			echo $(date "+%Y-%m-%d %T") 
			echo "Connectivity with ${!j} is ok"
		else
			echo "Ping Unsuccessful: ${!j} is not reachable"
		fi
		
		echo 
	
	done

done
