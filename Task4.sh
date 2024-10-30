#Set up SSH key to avoid entering a password for each transfer

#add corn job on the client to run the script every hour
crontab -e
#add the following line to schedual it 
0 * * * * /home/arwa/Clientinfo.sh
