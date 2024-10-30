#!/bin/bash

saved_info="process_info.log"
server_user=""
server_IP="192.168.10.15"
server_path=""

collect_info(){
echo "Log Date: $(date)\n" >> saved_info
# Process tree of all currently running processes
echo "Process tree of all currently running processes:" >> saved_info
pstree >> saved_info
#List of any dead or zombie processes
echo "\n List of any dead or zombie processes:" >> saved_info
ps aux | awk '$8 ~ /Z/ {print $0}' >> saved_info
#CPU usage related to processes
echo >> saved_info
>> saved_info
#Memory usage of running processes
echo >> saved_info
>> saved_info
#The top 5 resource-consuming processes (based on CPU or memory usage)
echo >> saved_info
>> saved_info
