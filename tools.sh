Validate that SSH is installed and enabled:
sudo apt update
sudo apt install openssh-server (y)
sudo systemctl status ssh
sudo ufw allow ssh

install an SFTP client:
sudo adduser sftpuser
sudo gedit /etc/ssh/sshd_config #you can do nano or gedit
#Override default SFTP configuration
Match User sftpuser
	ForceCommand internal-sftp
	PasswordAuthentication yes
	ChrootDirectory /sftp/%u
	PermitTunnel no
	AllowAgentForwarding no
	AllowTcpForwarding no
	X11Forwarding no
sudo mkdir -p /sftp/sftpuser
sudo chown root:root /sftp
sudo chmod 755 /sftp
sudo chown sftpuser:sftpuser /sftp/sftpuser
sudo systemctl restart ssh
sftp sftpuser@10.0.2.15

msmtp client side:
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


