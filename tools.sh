#Validate that SSH is installed and enabled:
sudo apt update
sudo apt install openssh-server -y
sudo systemctl status ssh
sudo ufw allow ssh

#Create local accounts for client1 and client2 in server:
sudo useradd -m client1
sudo passwd client1
sudo useradd -m client2
sudo passwd client2

#install SFTP client 1:
sudo adduser client1
sudo gedit /etc/ssh/sshd_config #you can do nano or gedit
#Override default SFTP configuration
Match User client1
	ForceCommand internal-sftp
	PasswordAuthentication yes
	ChrootDirectory /sftp/%u
	PermitTunnel no
	AllowAgentForwarding no
	AllowTcpForwarding no
	X11Forwarding no
sudo mkdir -p /sftp/client1
sudo chown root:root /sftp
sudo chmod 755 /sftp
sudo chown client1:client1 /sftp/client1
sudo systemctl restart ssh
#Replace [server_ip with the actual server ip
sftp client1@[server_ip]

#install SFTP client 2:
sudo adduser client2
sudo gedit /etc/ssh/sshd_config #you can do nano or gedit
#Override default SFTP configuration
Match User client2
	ForceCommand internal-sftp
	PasswordAuthentication yes
	ChrootDirectory /sftp/%u
	PermitTunnel no
	AllowAgentForwarding no
	AllowTcpForwarding no
	X11Forwarding no
sudo mkdir -p /sftp/client2
sudo chown root:root /sftp
sudo chmod 755 /sftp
sudo chown client2:client2 /sftp/client2
sudo systemctl restart ssh
#Replace [server_ip with the actual server ip
sftp client2@[server_ip]

#msmtp client side:
sudo apt update
sudo apt install msmtp msmtp-mta
gedit ~/.msmtprc

# Outlook SMTP configuration 
defaults
account outlook
host smtp.office365.com 
port 587 

#Authentication settings 
auth on 
user aa2102872@qu.edu.qa 
password (here i add my password)
from aa2102872@qu.edu.qa

#Encryption settings
tls on
tls_starttls on
tls_trust_file /etc/ssl/certs/ca-certificates.crt

# log sent messages for tracking
logfile ~/.msmtp.log

account default :outlook

chmod 600 ~/.msmtprc

#Installing net-tools and traceroute
sudo apt install net-tools
sudo apt install traceroute

