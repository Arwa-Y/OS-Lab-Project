#!/bin/bash


echo "Files with the permission 777: "

find -perm 777 -exec chmod 700 {} \;

exec > perm_change.log 2> /dev/null

echo "done"