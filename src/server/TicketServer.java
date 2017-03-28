package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class TicketServer {
	private PrintWriter out;
	private Socket socket;
	private ServerSocket serverSocket;
	private BufferedReader in;
	
	private int connections;
	
	private boolean running;
	
	private QuitThread quit;
	
	private LinkedList<TicketThread> Threads;
	/** Construct a server */
	public TicketServer(String s, int port){
		try{
			InetAddress a = InetAddress.getByName("localhost");
			serverSocket = new ServerSocket(port, 50, a);
			System.out.println("Server is now running....");
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//out = new PrintWriter((socket.getOutputStream()), true);
			connections = 0;
			running = true;
			
			quit = new QuitThread();
			quit.start();
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
					String name = "Client " + Integer.toString(connections++);
					TicketThread a = new TicketThread(name, socket);
					Threads.add(a);
					a.start();
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
		ser = new TicketServer("localhost", 9090);
		ser.start();
	}
}
