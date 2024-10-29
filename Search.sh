#!/bin/bash

Big_file="bigfile"
search_date=$(date)
system_administrator="aa2102872@qu.edu.qa"


#find the files in your account that are larger than 1M
find ~ -type f -size +1M > "$Big_file"

#count number of files found
Number_Of_Files=$(wc -l < "$Big_file")

#Store search date and the numberof files found with 1M in bigfile
echo "Search Date: $search_date" >> "$Big_file"
echo "Number of files larger than 1M: $Number_Of_Files" >> "$Big_file"

#Email the system administrator a message about the contents of the bigfile
if [ "$Number_Of_Files" -gt 0 ]; then
	mail -s "Larger than 1M Files Report" "$system_administrator" <<EOF
	Dear system administrator,
	No. of files larger than 1M as of $search_date :
	$(cat "Number_Of_Files")
	For more details check bigfile.
	Best regards,
	system
EOF
fi

