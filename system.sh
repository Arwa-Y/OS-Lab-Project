{
	echo "Disk Space of HOME directory"
	df -h $HOME
	echo "Disk Usage of HOME directory"
	du -h $HOME
} | tee disk_info.log
