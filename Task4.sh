#Set up SSH key to avoid entering a password for each transfer
ssh-keygen -t rsa -b -C "Client2Password"
ssh-copy-id Client2@192.168.10.15

#add corn job on the client to run the script every hour
crontab -e
#add the following line to schedual it at top of every hour
0 * * * * /home/arwa/Clientinfo.sh
