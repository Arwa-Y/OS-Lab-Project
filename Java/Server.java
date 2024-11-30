package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.io.FileInputStream;

public class Server {

	public static void main(String[] args) throws IOException {

		//Server Socket with port 1300
		ServerSocket ss = new ServerSocket(1300);
		
		//HashMap to save each client IP address with their latest system.sh request time
		HashMap<String, LocalDateTime> clientRequestTimes = new HashMap<String, LocalDateTime>();

		System.out.println("Server Running ......... ");
		ReentrantLockClass r = new ReentrantLockClass();
		
		while (true) {
		
			//Handles accepting client requests
			ClientHandler a = new ClientHandler(r,ss,clientRequestTimes);

			Thread t1  = new Thread(a);
			t1.start();
			try {
				t1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

//Handles client requests
class ClientHandler implements Runnable
{
	ReentrantLockClass r;
	ServerSocket server;
	HashMap<String, LocalDateTime> clientRequestTimes;
	
	public ClientHandler(ReentrantLockClass r, ServerSocket server, HashMap<String,LocalDateTime> clientRequestTimes) 
	{
		this.r=r;
		this.server = server;
		this.clientRequestTimes = clientRequestTimes;
	}

	@Override
	public void run() 
	{
		r.serveClient(server, clientRequestTimes);
	}

}

class ReentrantLockClass 
{
			ReentrantLock lock = new ReentrantLock();

				//Function to handle client requests
				public void serveClient(ServerSocket ss, HashMap<String,LocalDateTime> clientRequestTimes) 
				{
					try
					{
						lock.lock();
						Socket s = ss.accept();
						
						//Displays if connected with client
						System.out.println("Connected with " + s.getInetAddress() + ":" + s.getPort());
						
						//Scanner to receive input from client
						Scanner scr = new Scanner(s.getInputStream());
						String client = scr.next();
						System.out.println("Client Name: " + client);
						
						// Define the path to the shell script
						String scriptPath = "/home/aisha/Project/Phase_1/network.sh"; // Update the path if necessary
						
						//set the working directory for shell scripts so that it can access traceroute.sh
						File scriptDirectory = new File("/home/aisha/Project/Phase_1");
						
						// Create a ProcessBuilder to execute the shell script
						ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, s.getInetAddress().getHostAddress());
						processBuilder.directory(scriptDirectory);
						processBuilder.inheritIO(); // Inherit IO for console output (optional)

						try {
							// Start the process
							Process process = processBuilder.start();

							// Wait for the script to finish executing
							int exitCode = process.waitFor();
							System.out.println("network.sh Shell script exited with code: " + exitCode);

						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}

						// Get system.sh request from client
						String systemRequest = scr.next();
						
						if (systemRequest.equals("system.sh")) {
							
							String clientIP = s.getInetAddress().toString();
							
							//Check if this is the first ever request from this client ip
							boolean firstRequest = !clientRequestTimes.containsKey(clientIP);
							
							LocalDateTime currentTime = LocalDateTime.now();
							//Check if request is to be accepted by ensuring five minutes interval between requests
							boolean acceptedRequest = firstRequest || clientRequestTimes.get(clientIP).plusMinutes(5).isBefore(currentTime);
							
							//To print to client
							PrintStream p = new PrintStream(s.getOutputStream());
							
							if (acceptedRequest) {
								
								//Inform the client that request was accepted
								p.println('y');
								
								//Path to system.sh script
								scriptPath = "/home/aisha/Project/Phase_1/system.sh";
								
								// Create a ProcessBuilder to execute the shell script
								ProcessBuilder processBuilder1 = new ProcessBuilder(scriptPath);
								processBuilder1.directory(scriptDirectory);
								
								//Create a file to store the output of system.sh script
								File systemInfoFile = new File("system.txt");
								//Redirect output of processBuilder to system.txt file
								processBuilder1.redirectOutput(systemInfoFile);


								try {
									// Start the process
									Process process = processBuilder1.start();
									// Wait for the script to finish executing
									int exitCode = process.waitFor();
									System.out.println("system.sh Shell script exited with code: " + exitCode);

									//Send the system.txt file to client
									if (systemInfoFile.exists()) {
										try (FileInputStream fis = new FileInputStream(systemInfoFile);
												OutputStream os = s.getOutputStream()) {
											byte[] buffer = new byte[4096];
											int bytesRead;

											while ((bytesRead = fis.read(buffer)) != -1) {
												os.write(buffer, 0, bytesRead);
											}
											System.out.println("System File sent to " + client + " with IP address " + clientIP);
										}
									} else {
										System.out.println("File not found: " + systemInfoFile.getName());
									}

									//Update the HashMap with the latest request time
									clientRequestTimes.put(clientIP, currentTime);

								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								}
							}

							else {
								
								//Send to client that request was not accepted
								p.println("n");
								System.out.println("System Info Request denied for " + client + " with IP address " + clientIP);
								System.out.println("Five minutes not passed since last request");
							}
						}
						
						//Display clients' information
						System.out.println("Clients' Request Times: ");
						System.out.println("IP address\tRequest Time");
						
						for (String key : clientRequestTimes.keySet()) {
				            System.out.println(key + "\t" + clientRequestTimes.get(key));
				        }
						s.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					finally
					{
						lock.unlock();
					}	
				}
}
