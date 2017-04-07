package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.User;

public class InputThread extends Thread{
	private int name;
	private ObjectInputStream in;
	private Queue<Task> tasks;
	
	private boolean loggedIn;
	private boolean isAdmin;
	
	private User user;
	
	private Socket [] sockets;
	
	public InputThread(int n, Queue<Task> t, Socket [] so){
		super(Integer.toString(n));
		name = n;
		tasks = t;
		loggedIn = false;
		in = null;
		user = null;
		sockets = so;
	}
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				while (in == null) sleep(1);
				ClientRequestCom req = (ClientRequestCom) in.readObject();
				int type = req.type();
				Task t = new Task(type, name);
				System.out.println("Input Thread "+ this.getName() + " doing task type: " + type);
				if (type == ComTypes.REGISTER_USER){
					t.setUser(req.getUser());
					user = req.getUser();
				}
				else if (type == ComTypes.LOG_IN){
					t.setUser(req.getUser());
					user = req.getUser();
				}
				if (loggedIn && user != null){
					if (type == ComTypes.QUERY){
						t.setQuery(req.getQuery());
						t.setSearch(req.getSearch());
						if (req.getQuery() == ClientRequestCom.TICKET || req.getQuery() == ClientRequestCom.ALL_TICKETS){
							t.setUser(req.getUser());
						}
						else if (req.getQuery() == ClientRequestCom.FLIGHT_BY_DATE){
							t.setDate(req.getDate());
						}
					}
					else if (type == ComTypes.BOOK_FLIGHT){
						t.setFlight(req.getFlight());
						t.setUser(user);
						t.setSeats(req.getSeats());
					}
					else if (type == ComTypes.CANCEL_TICKET){
						t.setUser(user);
						t.setTicket(req.getTicket());
					}
					else if (type == ComTypes.ADD_FLIGHT && isAdmin){
						t.setFlight(req.getFlight());
						t.setUser(user);
					}
					else if (type == ComTypes.ADD_MULTIPLE_FLIGHTS && isAdmin){
						t.setMultiple_flights(req.getMultiple_flights());
						t.setUser(user);
					}
					else if (type == ComTypes.REMOVE_FLIGHT && isAdmin){
						t.setFlight(req.getFlight());
						t.setUser(user);
					}
					else if (type == ComTypes.REMOVE_TICKET){
						t.setTicket(req.getTicket());
						t.setUser(user);
					}
					else {
						Task bad = new Task(Task.BAD_REQUEST, name);
						tasks.enQueue(bad);
					}
				}
				tasks.enQueue(t);
				
				sleep(1);
			} catch (InterruptedException e){
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
				in = null;
				user = null;
				loggedIn = false;
				isAdmin = false;
				sockets[name] = null;
			} catch (NullPointerException e) {
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
				in = null;
				user = null;
				loggedIn = false;
				isAdmin = false;
				sockets[name] = null;
			} catch (ClassCastException e){
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
				Task bad = new Task(Task.BAD_REQUEST, name);
				tasks.enQueue(bad);
			} catch (Exception e) {
				System.out.println("Error in Input Thread "+ this.getName() + ": " + e.getMessage());
				in = null;
				user = null;
				loggedIn = false;
				isAdmin = false;
				sockets[name] = null;
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
	}
	
	/** Set whether this connection has been logged in yet */
	protected void setAdmin(boolean b){
		isAdmin = b;
	}
	
	/** Set the user */
	protected void setUser(User u){
		user = u;
	}
}
