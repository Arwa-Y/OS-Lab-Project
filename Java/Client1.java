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

//////////////////////////////////////////////////////////////////////////////		

		String str1 = "Client1";

		String request = "system.sh";

		Socket s = new Socket("192.168.1.68", 1300);

		PrintStream p = new PrintStream(s.getOutputStream());

		///////////////////////////////////////////////////////////////////////////////////
		// receiving txt file from server
		p.println(str1);


		///////////////////////////////////////////////////////////////

		// login.sh

		System.out.println("Starting login.....");
		System.out.println();

		// login.sh
		Scanner scr = new Scanner(System.in);

		System.out.println("Please enter username: ");
		String username = scr.next();

		System.out.println("Please enter password: ");
		String password = scr.next();

//		String full=username +" "+ password;

		String scriptPath2 = "/home/daisy/login.sh"; // Update the path if necessary

		// Create a ProcessBuilder to execute the shell script
		ProcessBuilder processBuilder2 = new ProcessBuilder(scriptPath2, username, password);
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
		//getting file from server
		
		p.println(request);
		System.out.println("Do you wnat to request system information? (y/n)");
		String answer = scr.next();

		if (answer.equals("y")) {

			File requestedFile = new File("system.txt");

			InputStream is = s.getInputStream();

			try (FileOutputStream fos = new FileOutputStream(requestedFile)) {

				byte[] buffer = new byte[4096];

				int readBytes;

				while ((readBytes = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readBytes);

				}

				System.out.println("File Done....... " + requestedFile.getName());

			}

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

					if (lineCount == 250) { //after reading and displaying 250 lines
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
