package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import data.transfer.ServerCom;

public class InputThread extends Thread{
	private int name;
	private Socket socket;
	private ObjectInputStream in;
	private Queue<Task> tasks;
	
	private boolean loggedIn;
	private boolean isAdmin;
	
	public InputThread(int n, Socket s, Queue<Task> t){
		super(Integer.toString(n + 1));
		name = n;
		tasks = t;
		loggedIn = false;
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void run(){
		try {
			while (true){
				while (socket == null) sleep(1);
				ServerCom temp = (ServerCom) in.readObject();
				//TODO create task from server com, add to tasks queue, use database and name
				Task t = new Task();
				tasks.enQueue(t);
				
				sleep(1);
			}
		} catch (InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ClassCastException e){
			System.out.println("Error: " + e.getMessage());
			//TODO send a operation confirm back to the client to say bad com
		}
	}
	/** Set the socket */
	public void setSocket(Socket s){
		socket = s;
	}
}
