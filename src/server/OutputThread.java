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
	private Database database;
	
	public OutputThread(int n, Socket s, Queue<Task> f, Database d){
		super(Integer.toString(n));
		name = n;
		database = d;
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
				if (!finishedTasks.isEmpty()){
					if (finishedTasks.deQueueNoRemoval().belongsTo(name)){
						Task temp = finishedTasks.deQueue();
						
						//TODO translate task temp into a servercom to give back to the client via object stream
					}
				}
				sleep(1);
			}
		} catch (InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		}
	}
}
