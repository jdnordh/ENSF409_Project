package server;

import java.io.IOException;
import java.io.ObjectInputStream;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;

public class InputThread extends Thread{
	private int name;
	private ObjectInputStream in;
	private Queue<Task> tasks;
	
	private boolean loggedIn;
	private boolean isAdmin;
	
	public InputThread(int n, Queue<Task> t){
		super(Integer.toString(n));
		name = n;
		tasks = t;
		loggedIn = false;
		in = null;
	}
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				while (in == null) sleep(1);
				ClientRequestCom req = (ClientRequestCom) in.readObject();
				int type = req.type();
				Task t = new Task(type, name);
				if (type == ComTypes.REGISTER_USER){
					t.setUser(req.getUser());
				}
				else if (type == ComTypes.LOG_IN){
					t.setUser(req.getUser());
				}
				if (loggedIn){
					if (type == ComTypes.QUERY){
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
					else if (type == ComTypes.ADD_FLIGHT && isAdmin){
						t.setFlight(req.getFlight());
						t.setUser(req.getUser());
					}
					else if (type == ComTypes.ADD_MULTIPLE_FLIGHTS && isAdmin){
						t.setMultiple_flights(req.getMultiple_flights());
						t.setUser(req.getUser());
					}
					else if (type == ComTypes.REMOVE_FLIGHT && isAdmin){
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
				}
				tasks.enQueue(t);
				
				sleep(1);
			} catch (InterruptedException e){
				System.out.println("Error: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
				in = null;
			} catch (NullPointerException e) {
				System.out.println("Error: " + e.getMessage());
				in = null;
			}
			catch (ClassCastException e){
				System.out.println("Error: " + e.getMessage());
				Task bad = new Task(Task.BAD_REQUEST, name);
				tasks.enQueue(bad);
			}
		}
	}
	
	/** Set the stream */
	public void setStream(ObjectInputStream s){
		in = s;
	}
	
	/** Set whether this connection has been logged in yet */
	protected void setLogin(boolean b){
		loggedIn = b;
		System.out.println("Logged in: " + loggedIn);
	}
	
	/** Set whether this connection has been logged in yet */
	protected void setAdmin(boolean b){
		isAdmin = b;
		System.out.println("Admin: " + loggedIn);
	}
}
