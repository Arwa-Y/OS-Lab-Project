package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		int x1, x2, sum = 0;
		String str1, str2 = "String2";
		ServerSocket ss = new ServerSocket(1300);
		System.out.println("Server Running ......... ");
		while (true) {
			Socket s = ss.accept();
			System.out.println("Connected with " + s.getInetAddress() + ":" + s.getPort());
			Scanner scr = new Scanner(s.getInputStream());
			// Define the path to the shell script
	        String scriptPath = "/home/aisha/Project/Phase_1/network.sh"; // Update the path if necessary
	        File scriptDirectory = new File("/home/aisha/Project/Phase_1");
	        ProcessBuilder processBuilder = new ProcessBuilder(scriptPath,s.getInetAddress().getHostAddress());
	        processBuilder.directory(scriptDirectory);
	        processBuilder.inheritIO(); // Inherit IO for console output (optional)
	        
	        // Create a ProcessBuilder to execute the shell script
//			PrintStream p = new PrintStream(s.getOutputStream());
//			p.println(sum);

			
	        try {
	            // Start the process
	            Process process = processBuilder.start();
//	            OutputStream os = process.getOutputStream();
//	            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter())
//	            PrintStream p = new PrintStream(processBuilder.getOutputStream());

	            // Wait for the script to finish executing
	            int exitCode = process.waitFor();
	            System.out.println("Shell script exited with code: " + exitCode);

	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
//			x1 = scr.nextInt();
//			x2 = scr.nextInt();
//			for (int i = x1; i <= x2; i++) {
//				sum = sum + i;
//			}
//			PrintStream p = new PrintStream(s.getOutputStream());
//			p.println(sum);
			str1 = scr.next();
			System.out.println(str1);
//			if (str1.equals(str2)) {
//				System.out.println(str1 + " = " + str2);
//			} else {
//				System.out.println(str1 + " != " + str2);
//			}

			s.close();
		}
	}

}
