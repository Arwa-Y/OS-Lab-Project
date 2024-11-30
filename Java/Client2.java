import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		//server ip and socket port number
		Socket s = new Socket("192.168.1.68", 1300);
		
		// Printing on the server 
		PrintStream p = new PrintStream(s.getOutputStream());		
		p.println("Client2");

		//ask user for password to connect to the server
		Scanner scr = new Scanner(System.in);
		System.out.println("Please enter password");
		String password = scr.next();

		/////////////////////////////////////////////////////////////////////////
		// Running Search.sh
		
		// Define the path to the shell script
		String scriptPath2 = "/home/arwa/Search.sh"; 

		// Create a ProcessBuilder to execute the shell script
		ProcessBuilder processBuilder2 = new ProcessBuilder(scriptPath2);
		processBuilder2.inheritIO(); // Inherit IO for console output (optional)

		try {
			// Start the process
			Process process2 = processBuilder2.start();

			// Wait for the script to finish executing
			int exitCode2 = process2.waitFor();
			System.out.println("Shell script exited with code: " + exitCode2);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		//////////////////////////////////////////////////////////////////////////
		// Running Clientinfo.sh
		// Define the path to the shell script
		String scriptPath = "/home/arwa/Clientinfo.sh"; // Update the path if necessary

		// Create a ProcessBuilder to execute the shell script
		ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, password);
		processBuilder.inheritIO(); // Inherit IO for console output (optional)
		
		
		try {
			// Start the process
			Process process = processBuilder.start();

			// Wait for the script to finish executing
			int exitCode = process.waitFor();
			System.out.println("Shell script exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		/////////////////////////////////////////////////////////////////////////
		// Requesting from the server system info
		String request="system.sh";
		p.println(request);
		
		System.out.println("Do you want to request system information? (y/n)");
		String answer = scr.next();

		if (answer.equals("y")) {
			//creat txt file
			File requestedFile = new File("system.txt");
			//read from the server and save in the file we created
			InputStream is = s.getInputStream();
			try (FileOutputStream fos = new FileOutputStream(requestedFile)) {
				byte[] buffer = new byte[4096];
				int readBytes;
				while ((readBytes = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readBytes);
				}
				System.out.println("File Done....... " + requestedFile.getName());
			}
			//read from the txt file we created and print in the console
			try (BufferedReader br = new BufferedReader(new FileReader(requestedFile))) {
				String systemInfo;
				System.out.println(requestedFile.getName() + "'s Content: ");
				System.out.println("Display Info? (y)");
				String continueY = scr.next();
				int lineCount = 0;
				while ((systemInfo = br.readLine()) != null && continueY.equals("y")) {
					lineCount++;
					System.out.println();
					System.out.println(systemInfo);
					if (lineCount == 250) {
						System.out.println("Continue? (y)");
						continueY = scr.next();
						lineCount = 0;
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		/////////////////////////////////////////////////////////////////
		System.out.println();
		System.out.println();

		s.close();
	}
}
