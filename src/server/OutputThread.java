package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.transfer.ServerCom;

public class OutputThread extends Thread{
	private int name;
	private Socket socket;
	private ObjectOutputStream out;
	private Queue<Task> finishedTasks;
	
	public OutputThread(int n, Socket s, Queue<Task> f){
		super(Integer.toString(n));
		name = n;
		finishedTasks = f;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void run(){
		try {
			while (true){
				while (socket == null) sleep(1);
				if (!finishedTasks.isEmpty()){
					if (finishedTasks.deQueueNoRemoval().belongsTo(name)){
						Task temp = finishedTasks.deQueue();
						
						
						
						//TODO translate task temp into a servercom to give back to the client via object stream
						
						/*
						 * public static final int REGISTER_USER = 0;
							public static final int LOG_IN = 1;
							public static final int QUERY = 2;
							public static final int BOOK_FLIGHT = 3;
							public static final int CANCEL_FLIGHT = 4;
							public static final int ADD_FLIGHT = 30;
							public static final int ADD_MULTIPLE_FLIGHTS= 31;
							public static final int REMOVE_FLIGHT = 32;
							public static final int REMOVE_TICKET = 33;
							
							public static final int BAD_REQUEST = 50;
							public static final int FAILED = 51;
												 */
						
					}
				}
				sleep(1);
			}
		} catch (InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	/** Set the socket */
	public void setSocket(Socket s){
		socket = s;
	}
}
