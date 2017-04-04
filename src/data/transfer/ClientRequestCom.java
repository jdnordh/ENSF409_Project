package data.transfer;

/**
 * This class is sent by the client to the server to ask for tasks to be done
 * 
 * These fields need to be filled in when creating a client request:
 * REGISTER_USER: username, password, birthday, firstname, lastname
 * LOG_IN: username, password
 * QUERY:
 * if it is a ticket search: query (use TICKET or ALL_TICKETS - see definitions), user
 * if it is a flight search: query (use FLIGHT or ALL_FLIGHTS - see definitions)
 * BOOK_FLIGHT: flight, user, seats
 * CANCEL_TICKET: user, ticket
 * 
 * @author Jordan Nordh
 *
 */
public class ClientRequestCom extends ServerCom{
	private static final long serialVersionUID = 1L;
	/** For searching through flights via a city, either destination or source */
	public static final int FLIGHT_BY_CITY = 0;
	/** For searching for flights by the source city */
	public static final int FLIGHT_BY_SOURCE = 1;
	/** For searching for flights by the destination city */
	public static final int FLIGHT_BY_DESTINATION = 2;
	/** For searching for tickets belonging to the given user */
	public static final int TICKET = 3;
	/** Get all flights */
	public static final int ALL_FLIGHTS = 4;
	/** Get all tickets, ADMIN ONLY */
	public static final int ALL_TICKETS = 5;
	/** The following fields are not always used, depending on the type of request */
	
	/** The user executing the request */
	private User user;
	/** The input password */
	private String password;
	/** The search wanting to be executed */
	private String search;
	/** What type of query is this, use only declared types at the top*/
	private int query;
	/** The flight wanting to be booked/deleted */
	private Flight flight;
	/** Used only for deleting tickets */
	private Ticket ticket;
	/** amount of seats wanting to be booked */
	private int seats;
	
	
	public ClientRequestCom(int t) {
		super(t);
	}

	//Set and Get
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
