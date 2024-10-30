#!/bin/bash

disk_log="disk_info.log"
cpu_log="mem_cpu_info.log"

{
	echo "Log Date: $(date "+%Y-%m-%d %T")"
	echo "Disk Space of HOME directory"
	df -h $HOME
	echo "Disk Usage of HOME directory"
	du -h $HOME
} | tee $disk_log -a

{
	echo "Log Date: $(date "+%Y-%m-%d %T")"
	echo "Free Memory: $(free | grep Mem | awk '{print $4/$2 * 100}') %"
	echo "Used Memory: $(free | grep Mem | awk '{print $3/$2 * 100}') %"
	echo "CPU Model: $(lscpu | grep "Model name" | awk -F: '{print $2}' | xargs)"
	echo "No. of CPU cores: $(nproc)"
} | tee $cpu_log -a
