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



if sshpass -p "$pw" ssh "$un@$server" "exit"
then
	echo "successful login"
else
	timestamps=$(date)
	echo "Invalid login for user: $un, at: $timestamps"	
fi

echo "login Shell Script Done !"
