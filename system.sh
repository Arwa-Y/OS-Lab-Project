{
	echo "Disk Space of HOME directory"
	df -h $HOME
	echo "Disk Usage of HOME directory"
	du -h $HOME
} | tee disk_info.log

{
	echo "Free Memory: $(free | grep Mem | awk '{print $4/$2 * 100}') %"
	echo "Used Memory: $(free | grep Mem | awk '{print $3/$2 * 100}') %"
	echo "CPU Model: $(lscpu | grep "Model name" | awk -F: '{print $2}' | xargs)"
	echo "NO. of CPU cores: $(nproc)"
} | tee mem_cpu_info.log
