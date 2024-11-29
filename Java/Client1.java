package client1;

import java.io.IOException;
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

		String str1="Client1";
		
		Socket s=new Socket("192.168.1.68",1300);

		
		PrintStream p=new PrintStream(s.getOutputStream());

//		
//		Scanner scr2=new Scanner(s.getInputStream());
//		sum=scr2.nextInt();
		

		
		System.out.println();
//		str1=scr1.next();
		
		p.println(str1);
		
		System.out.println("Starting login.....");
		System.out.println();
		
		//login.sh
		Scanner scr= new Scanner(System.in);
		
		System.out.println("Please enter username: ");
		String username=scr.next();
		
		
		System.out.println("Please enter password: ");
		String password=scr.next();
		
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
		
		
		s.close();
	}
	

}
