package server;

import java.util.Vector;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Date;
import data.transfer.Flight;
import data.transfer.Ticket;
import data.transfer.User;

public class Task {
	// -------------- Types ----------------- 
	/** Client tasks */
	public static final int REGISTER_USER = 0;
	public static final int LOG_IN = 1;
	public static final int QUERY = 2;
	public static final int BOOK_FLIGHT = 3;
	public static final int CANCEL_FLIGHT = 4;
	
	/** Admin tasks */
	public static final int ADD_FLIGHT = 30;
	public static final int ADD_MULTIPLE_FLIGHTS= 31;
	public static final int REMOVE_FLIGHT = 32;
	public static final int REMOVE_TICKET = 33;
	
	/** This task sends a bad request back to the client */
	public static final int BAD_REQUEST = 50;
	/** Previous task failed */
	public static final int FAILED = 51;
	
	/** Only the task thread should create these types to tell the input thread that the user is logged in */
	public static final int LOGIN_CONFIRM = 63;
	
	/** Type of task */
	private int type;
	/** belongs to certain thread */
	private int belongsTo;
	/** Whether or not the task is finished */
	private boolean finished;
	/** The user executing the request */
	private User user;
	/** The input password */
	private String password;
	/** The search wanting to be executed */
	private String search;
	/** What type of query is this, use only declared types at the top*/
	private int query;
	/** The flight wanting to be booked/deleted/added */
	private Flight flight;
	/** The flights that need to be added to the database or return flights */
	private Vector<Flight> multiple_flights;
	/** Used only for deleting tickets */
	private Ticket ticket;
	/** used to return ticket */
	private Vector<Ticket> multiple_tickets;
	/** amount of seats wanting to be booked */
	private int seats;
	private Date date;
	
	/**
	 * Create a task
	 * @param t Task type
	 * @param c Client that the task belongs to
	 *  */
	
	public Task(int t, int c){
		type = t;
		belongsTo = c;
		finished = false;
	}
	
	/** Check if the task belongs to a certain thread */
	public boolean belongsTo(int x){
		return (x == belongsTo);
	}
	
	/**
	 * Get the int of which socket this task belongs to
	 * @return Integer name
	 */
	public int belongsTo(){
		return belongsTo;
	}
	
	/** Perform this task */
	public boolean perform(Database database){
		//TODO finish this method and stuff
		try {
			if (type == ComTypes.REGISTER_USER){
				finished = database.register(user);
			}
			else if (type == ComTypes.LOG_IN){
				finished = database.login(user);
			}
			else if (type == ComTypes.QUERY){
				if (query == ClientRequestCom.FLIGHT_BY_CITY){
					multiple_flights = database.searchCity(search);
					finished = true;
				}
				else if (query == ClientRequestCom.FLIGHT_BY_SOURCE){
					multiple_flights = database.searchSourceCity(search);
					finished = true;
				}
				else if (query == ClientRequestCom.FLIGHT_BY_DESTINATION){
					multiple_flights = database.searchDestinationCity(search);
					finished = true;
				}
				else if (query == ClientRequestCom.FLIGHT_BY_DATE){
					multiple_flights = database.searchDate(date);
					finished = true;
				}
				else if (query == ClientRequestCom.ALL_FLIGHTS){
					multiple_flights = database.browse();
					finished = true;
				}
				else if (query == ClientRequestCom.ALL_TICKETS){
					multiple_tickets = database.getTickets(user);
					finished = true;
				}
				else if (query == ClientRequestCom.TICKET){
					multiple_tickets = database.getTickets(user);
					finished = true;
				}
			}
			else if (type == ComTypes.BOOK_FLIGHT){
				database.bookTicket(user, flight, seats);
				finished = true;
			}
			else if (type == ComTypes.CANCEL_TICKET){
				finished = database.removeTicket(user, ticket);
			}
			else if (type == ComTypes.REMOVE_TICKET){
				finished = database.removeTicket(user, ticket);
			}
			else if (type == ComTypes.ADD_FLIGHT){
				finished = database.addFlight(user, flight);
			}
			else if (type == ComTypes.ADD_MULTIPLE_FLIGHTS){
				finished = database.addMultipleFlights(user, multiple_flights);
			}
			else if (type == ComTypes.REMOVE_FLIGHT){
				finished = database.removeFlight(user, flight);
			}
			else if (type == ComTypes.REMOVE_TICKET){
				finished = database.removeTicket(user, ticket);
			}
		} catch (NullPointerException e){
			return false;
		}
		return finished;
	}

	
	//Set and get
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Vector<Flight> getMultiple_flights() {
		return multiple_flights;
	}

	public void setMultiple_flights(Vector<Flight> multiple_flights) {
		this.multiple_flights = multiple_flights;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public Vector<Ticket> getMultiple_tickets() {
		return multiple_tickets;
	}

	public void setMultiple_tickets(Vector<Ticket> multiple_tickets) {
		this.multiple_tickets = multiple_tickets;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
