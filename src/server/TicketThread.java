package server;

import java.net.Socket;

public class TicketThread extends Thread{
	private Socket socket;
	
	public TicketThread(String name, Socket s){
		super(name);
		socket = s;
	}
	
	public void run(){
		
	}
	
}
