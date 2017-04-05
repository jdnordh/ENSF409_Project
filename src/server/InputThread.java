package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.ServerCom;

public class InputThread extends Thread{
	private int name;
	private Socket socket;
	private ObjectInputStream in;
	private Queue<Task> tasks;
	
	private boolean loggedIn;
	private boolean isAdmin;
	
	public InputThread(int n, Socket s, Queue<Task> t){
		super(Integer.toString(n));
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
				ClientRequestCom req = (ClientRequestCom) in.readObject();
				//TODO create task from server com, add to tasks queue, use database and name
				int type = req.type();
				Task t = new Task(type, name);
				if (type == ComTypes.REGISTER_USER){
					t.setUser(req.getUser());
				}
				else if (type == ComTypes.LOG_IN){
					t.setUser(req.getUser());
					isAdmin = t.getUser().isAdmin();
				}
				else if (type == ComTypes.QUERY){
					t.setQuery(req.getQuery());
					t.setSearch(req.getSearch());
					if (req.getQuery() == ClientRequestCom.TICKET || req.getQuery() == ClientRequestCom.ALL_TICKETS){
						t.setUser(req.getUser());
					}
				}
				else if (type == ComTypes.BOOK_FLIGHT){
					t.setFlight(req.getFlight());
					t.setUser(req.getUser());
					t.setSeats(req.getSeats());
				}
				else if (type == ComTypes.CANCEL_TICKET){
					t.setUser(req.getUser());
					t.setTicket(req.getTicket());
				}
				else if (type == ComTypes.ADD_FLIGHT){
					t.setFlight(req.getFlight());
					t.setUser(req.getUser());
				}
				else if (type == ComTypes.ADD_MULTIPLE_FLIGHTS){
					t.setMultiple_flights(req.getMultiple_flights());
					t.setUser(req.getUser());
				}
				else if (type == ComTypes.REMOVE_FLIGHT){
					t.setFlight(req.getFlight());
					t.setUser(req.getUser());
				}
				else if (type == ComTypes.REMOVE_TICKET){
					t.setTicket(req.getTicket());
					t.setUser(req.getUser());
				}
				else {
					Task bad = new Task(Task.BAD_REQUEST, name);
					tasks.enQueue(bad);
				}
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
			Task bad = new Task(Task.BAD_REQUEST, name);
			tasks.enQueue(bad);
		}
	}
	/** Set the socket */
	public void setSocket(Socket s){
		socket = s;
	}
}
