#!/bin/bash

log1="invalid_attempts.log"
log2="client_timestamp_invalid_attempts.log"
server="192.168.10.30"
#un=client
#pw=password

count=0
max_attempts=3
if [[ $# -ne 2 ]]
then 
	echo "Format: $0 username password"
	exit 1		
fi

un=$1
pw=$2
#echo "$1, $2"


while (( count < max_attempts ))
do

if sshpass -p "$pw" ssh "$un@$server" "exit"
then
	echo "successful login"
	#exit 0
	
	cat "$log1" >> "$log2"	
	sshpass -p "$pw" sftp "$un@$server" << EOF
put "$log2"
EOF
	
	sleep 30
	pkill -KILL -u "$un"
else
	timestamps=$(date)
	echo "Invalid login for user: $un, at: $timestamps" >> "$log1"
	

	
	echo "Attempt #$((count+1)) Done !"

	((count++))
	
	if  ((count == max_attempts))
	then
		echo "Unauthorized user !"
		break
	fi
	

	echo "Please enter username and password again: "
	read -p "Username: " un
	read -p "Password: " pw
	
fi
done

echo "login Shell Script Done !"
