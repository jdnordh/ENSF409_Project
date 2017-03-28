package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TicketThread extends Thread{
	private Socket socket;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	
	public TicketThread(String name, Socket s){
		super(name);
		socket = s;
		try {
		objectOut = new ObjectOutputStream(socket.getOutputStream());
		objectIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e){
			System.out.println("Server error: " + e.getMessage());
		}
	}
	
	public void run(){
		
	}
	
}
