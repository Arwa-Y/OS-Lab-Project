package client1;

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

public class Client1 {

	public static void main(String[] args) throws UnknownHostException, IOException {

		// Define the path to the shell script
		String scriptPath = "/home/daisy/Check.sh"; // Update the path if necessary

		// Create a ProcessBuilder to execute the shell script
		ProcessBuilder processBuilder = new ProcessBuilder(scriptPath);
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

		//////////////////////////////////////////////////////////////////////		

		String str1 = "Client1";

		Socket s = new Socket("192.168.1.68", 1300);

		PrintStream p = new PrintStream(s.getOutputStream()); //printing to server

		///////////////////////////////////////////////////////////////////////////////////
		p.println(str1); //just a test


		///////////////////////////////////////////////////////////////

		// login.sh

		System.out.println("Starting login.....");
		System.out.println();

		Scanner scr = new Scanner(System.in);

		System.out.println("Please enter username: ");
		String username = scr.next();

		System.out.println("Please enter password: ");
		String password = scr.next();


		String scriptPath2 = "/home/daisy/login.sh"; // Update the path if necessary

		// Create a ProcessBuilder to execute the shell script
		ProcessBuilder processBuilder2 = new ProcessBuilder(scriptPath2, username, password); //e.g. login.sh client1 1224
		processBuilder2.inheritIO(); // Inherit IO for console output (optional)

		try {
			// Start the process
			Process process2 = processBuilder2.start();

			// Wait for the script to finish executing
			int exitCode = process2.waitFor();
			System.out.println("Shell script exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		//////////////////////////////////////////
		// receiving txt file from server
		
		String request = "system.sh"; //special request
		
		p.println(request);
		System.out.println("Do you want to request system information? (y/n)"); //just for organization purposes
		String answer = scr.next();

		if (answer.equals("y")) {

			File requestedFile = new File("system.txt"); //creating file

			InputStream is = s.getInputStream(); //getting chunks of file from server 

			try (FileOutputStream fos = new FileOutputStream(requestedFile)) {

				byte[] buffer = new byte[4096];

				int readBytes;

				while ((readBytes = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readBytes); //writing to the file, overwrites

				}

				System.out.println("File Writing Done....... " + requestedFile.getName()); //checking

			}

			try (BufferedReader br = new BufferedReader(new FileReader(requestedFile))) {

				String systemInfo; //line form file
				System.out.println(requestedFile.getName() + "'s Content: ");

				System.out.println("Display Info? (y)"); //just for organization purposes
				String continueY = scr.next();

				int lineCount = 0;
				while ((systemInfo = br.readLine()) != null && continueY.equals("y")) {

					lineCount++;
					System.out.println();
					System.out.println(systemInfo); //printing the line

					if (lineCount == 250) { //after reading and displaying 250 lines ask again
						System.out.println("Continue? (y)"); //for organization purposes
						continueY = scr.next();
						lineCount = 0; //restart count
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println();
		System.out.println("End of code, thank you.");
		
		s.close();
	}

}
