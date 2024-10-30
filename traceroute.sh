#!/bin/bash

if [ "$#" -eq 0 ]
then
	echo "Please provide an IP address when running traceroute.sh"
	exit
fi

{
echo "Routing Table"
route
echo

echo "Hostname: $(hostname)"
echo 

local_server=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2}')

echo "Testing local DNS server ($local_server)"
nslookup google.com $local_server

echo "Tracing route to google.com"
traceroute google.com
echo

echo "Pinging google.com"
ping -c 4 -W 5 google.com
echo

if traceroute $1 
then
	echo "Traceroute to $1 succeeded"
else
	echo "Traceroute to $1 failed"	
	echo "Rebooting Machine"
	sudo reboot
fi
} 
