#!/bin/bash
MACHINE_NAME=$1
ID_ISTANZA=`sudo docker ps | grep -i $MACHINE_NAME | awk '{print $1}'`
sudo docker exec -it $ID_ISTANZA /bin/bash
