import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		String str1="Client2";
		Socket s=new Socket("192.168.1.68",1300);
		
		PrintStream p=new PrintStream(s.getOutputStream());
		
		Scanner scr2=new Scanner(s.getInputStream());
		System.out.println("Testing.....");
		p.println(str1);
		s.close();
		
		Scanner scr = new Scanner(System.in);
		System.out.println("Please enter password");
		
		String password=scr.next();
		
		// Define the path to the shell script
        String scriptPath2 = "/home/arwa/Search.sh"; // Update the path if necessary

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
     
	}
}
