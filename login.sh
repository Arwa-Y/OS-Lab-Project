#!/bin/bash

log1="invalid_attempts.log"
server="192.168.10.30"
#un=client
#pw=password

if [[ $# -ne 2 ]]
then 
	echo "Format: $0 username password"
	exit 1		
fi

un=$1
pw=$2
#echo "$1, $2"

ssh_command=sshpass -p "$pw" ssh "$un@$server"
echo "$ssh_command"
"$ssh_command""exit"
echo "login Shell Script Done !"
