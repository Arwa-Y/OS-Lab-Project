#!/bin/bash

exec > perm_change.log 2>&1

files=$(find / -perm 777 ! -xtype l)

echo "Files with the permission 777: "

echo "$files"

for file in $files
do
chmod 700 "$file"
done


echo "Check Shell Script Done !"