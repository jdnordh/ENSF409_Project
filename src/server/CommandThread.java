package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandThread extends Thread{
	
	public void run(){
		try {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = in.readLine();
		if (input.equalsIgnoreCase("QUIT")){
			System.out.println("Closing the server...");
			System.exit(0);
		}
		// any more server commmands can go here
		
		in.close();
		} catch (Exception e){
			System.out.println("Error: " + e.getLocalizedMessage());
		}
	}
}
