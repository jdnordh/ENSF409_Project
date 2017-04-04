package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;

import data.transfer.Flight;

public class TicketServer {
	private PrintWriter out;
	private Socket socket;
	private ServerSocket serverSocket;
	private BufferedReader in;
	private Database database;
	
	private int connections;
	
	private boolean running;
	
	private CommandThread command;
	private Vector<InputThread> inputThreads;
	private Vector<OutputThread> outputThreads;
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
			running = true;
			database = new Database();
			tasks = new Queue<Task>();
			sockets = new Socket[20];
			command = new CommandThread();
			command.start();
		} catch (IOException e){
			System.out.println("Server error: " + e.getMessage());
		}
	}

	/** Waits for a client to connect, then starts a new thread to handle it */
	public void start(){	
		try {
			while (running){
				if (connections < 20){
					socket = serverSocket.accept();
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					Vector<String> s = new Vector<String>();
					s.add("Hello");
					s.add("there");
					out.writeObject(s);
					out.flush();
					//String name = "Client " + Integer.toString(connections++);
					//TicketThread a = new TicketThread(name, socket);
					//Threads.add(a);
					//a.start();
					System.out.println(++connections);
				}
			}
			
		} catch (IOException e){
			System.out.println("Error... " + e.getMessage());
		}
		
		try{
			out.close();
			socket.close();
			serverSocket.close();
			in.close();
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
