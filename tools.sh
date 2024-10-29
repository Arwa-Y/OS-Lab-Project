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

msmtp:
sudo apt-get install msmtp msmtp-mta mailutils
sudo nano /etc/msmtprc

# set default values for all following accounts
defaults

# use the mail submission port 587 instead of the SMTP port 25.
port 587

# Always use TLS
tls on

# set a list of trusted CAs for TLS. the default is to use system settings,
# but you can select your own file
tls_trust_file /etc/ssl/certs/ca-certificates.crt

# The SMTP server of your Outlook
account outlook
host smtp.office365.com
from aa2102872@qu.edu.qa
auth on 
user 12345

# Set default account to outlook 
account default: outlook

# Map local users to mail addresses
aliases /etc/aliases

sudo apt-get install bsd-mailx

