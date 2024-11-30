#!/bin/bash

saved_info="process_info.log"
server_user="client2"
server_IP="192.168.1.68"
server_path="home/client2"


pw=$1

info(){
echo "Log Date: $(date) \n" >> "$saved_info"

# Process tree of all currently running processes
echo "Process tree of all currently running processes:" >> "$saved_info"
pstree >> "$saved_info"

#List of any dead or zombie processes
echo -e "\n List of any dead or zombie processes:" >> "$saved_info"
ps aux | awk '$8 ~ /Z/ {print $0}' >> "$saved_info"

#CPU usage related to processes
echo -e "\n CPU usage related to processes:" >> "$saved_info"
ps -eo pid,ppid,cmd,%cpu --sort=-%cpu | head -n 15 >> "$saved_info"

#Memory usage of running processes
echo -e "\n Memory usage of running processes:" >> "$saved_info"
ps -eo pid,ppid,cmd,%mem --sort=-%mem | head -n 15 >> "$saved_info"

#The top 5 resource-consuming processes (based on CPU or memory usage)
echo -e "\n The top 5 resource-consuming processes:" >> "$saved_info"
ps -eo pid,ppid,cmd,%cpu,%mem --sort=-%cpu | head -n 6 >> "$saved_info"
}

while true; do
info
sshpass -p "$pw" scp "$saved_info" "${server_user}@${server_IP}:/${server_path}"
echo "$saved_info successfully copied"
sleep 3600
done &
