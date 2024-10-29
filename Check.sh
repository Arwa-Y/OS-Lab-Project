#!/bin/bash


echo "Files with the permission 777: \n"

files = $(find -perm 777)
files | chmod 700

exec > perm_change.log 2> /dev/null