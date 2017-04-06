package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TicketServer {
	private Socket socket;
	private ServerSocket serverSocket;
	private Database database;
	
	private int connections;
	private int connectionIndex;
	
	private boolean running;
	
	private CommandThread command;
	private Vector<InputThread> inputThreads;
	private Vector<OutputThread> outputThreads;
	private Vector<TaskThread> taskThreads;
	private Queue<Task> tasks;
	private Queue<Task> finishedTasks;
	private Socket [] sockets;
	
	
	/** Construct a server */
	public TicketServer(String s, int port){
		try{
			InetAddress a = InetAddress.getByName(s);
			serverSocket = new ServerSocket(port, 50, a);
			System.out.println("Server is now running on " + s.toString());
			connections = 0;
			connectionIndex = 0;
			running = true;
			database = new Database();
			tasks = new Queue<Task>();
			finishedTasks = new Queue<Task>();
			sockets = new Socket[20];
			for (int i = 0; i < 20; i++){
				sockets[i] = null;
			}
			
			// Make and start the input and output thread pools
			inputThreads = new Vector<InputThread>();
			outputThreads = new Vector<OutputThread>();
			for (int i = 0; i < 20; i++){
				InputThread temp1 = new InputThread(i, tasks);
				OutputThread temp2 = new OutputThread(i, finishedTasks);
				temp1.start();
				temp2.start();
				inputThreads.add(temp1);
				outputThreads.add(temp2);
			}
			taskThreads = new Vector<TaskThread>();
			for (int i = 0; i < 5; i++){
				TaskThread thread = new TaskThread(i, tasks, finishedTasks, database, inputThreads);
				thread.start();
				taskThreads.add(thread);
			}
			
			command = new CommandThread();
			command.start();
		} catch (IOException e){
			System.out.println("Server error: " + e.getMessage());
		}
	}

	/** Waits for a client to connect, then starts a new thread to handle it */
	public void start(){	
		while (running){
			try {
				if (connections < 20){
					while (sockets[connectionIndex] != null) {
						connectionIndex++;
						connectionIndex = connectionIndex % 20;
					}
					
					sockets[connectionIndex] = serverSocket.accept();
					System.out.println("Got connection: " + sockets[connectionIndex].toString());
					ObjectOutputStream out = new ObjectOutputStream(sockets[connectionIndex].getOutputStream());
					ObjectInputStream in = new ObjectInputStream(sockets[connectionIndex].getInputStream());
					
					
					outputThreads.get(connectionIndex).setStream(out);
					inputThreads.get(connectionIndex).setStream(in);
					
					
					connections = 0;
					for (int i = 0; i < 20; i++){
						if (sockets[i] != null && sockets[i].isClosed()) sockets[i] = null;
						if (sockets[i] != null) connections++;
					}
					
					System.out.println("Connections: " + connections);
					//TODO fix the amount of connections allowed, as it is currently wrong
				}
				
			} catch (IOException e){
				System.out.println("Error... " + e.getMessage());
				e.printStackTrace();
			}
		}
		try{
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			System.exit(1);
			e.printStackTrace();
		}
		System.exit(0);
	}

	
	public static void main(String [] args){
		TicketServer ser;
		ser = new TicketServer("192.168.1.31", 6000);
		ser.start();
	}
}
