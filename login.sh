#!/bin/bash

sudo grep 'Failed password' /var/log/auth.log | awk '{print $1, $2, $3, $9}'


echo "login Shell Script Done !"
