#!/bin/bash

exec > >(tee -a perm_change.log) 2>&1

files=$(find /home \( -path /home/*/snap -prune \) -o -perm 777 ! -xtype l 2>/dev/null)

echo "Files with the permission 777: "

echo "$files"

for file in $files
do
chmod 700 "$file" 2>/dev/null || echo "chmod failed for: $file"
done


echo "Check Shell Script Done !"