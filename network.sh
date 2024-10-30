#!/bin/bash

no_of_targets=$#
log_file="network.log"

if [ $no_of_targets -eq 0 ]
then
	echo "Please provide at least one IP address when running network.sh"
	exit
fi

for j in $(seq 1 $no_of_targets); do
	{
	echo "Log Date: $(date "+%Y-%m-%d %T")"
	echo "Pinging  ${!j}"
	
	if ping -c 4 -W 5 ${!j} > /dev/null 2>&1
	then
		echo "Ping Successful" 
		echo "Connectivity with ${!j} is ok"
	else
		echo "Ping Unsuccessful: ${!j} is not reachable"
		echo
		echo "Running traceroute.sh"
		echo
		./traceroute.sh ${!j}
	fi
	} | tee $log_file -a
		 
done
