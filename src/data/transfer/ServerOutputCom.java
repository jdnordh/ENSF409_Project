package data.transfer;

import java.util.Vector;

/**
 * This class is returned by the server, and should always be checked for which type it is
 * @author Jordan Nordh
 *
 */
public class ServerOutputCom extends ServerCom{
	private static final long serialVersionUID = 1L;
	/** These fields are selectively used depending on the type of com*/
	
	/** A vector of flights from a search */
	private Vector<Flight> flights;
	/** A vector of tickets from a search */
	private Vector<Ticket> tickets;
	/** The user associated with the task */
	private User user;
	
	public ServerOutputCom(int t) {
		super(t);
	}
	
	/**
	 * Return flights com
	 * @param f
	 */
	public ServerOutputCom(Vector<Flight> f) {
		super(RETURN_QUERY_FLIGHT);
		flights = f;
	}
	
	/**
	 * Return tickets com
	 * @param t RETURN_QUERY_TICKET only
	 * @param f Vector of tickets
	 */
	public ServerOutputCom(int t, Vector<Ticket> f) {
		super(t);
		tickets = f;
	}
	
	/**
	 * Get the flights of a com if it is of that type
	 * @return A vector of flights
	 */
	public Vector<Flight> getFlights(){
		if (type == RETURN_QUERY_FLIGHT) return flights;
		else return null;
	}
	
	/**
	 * Get the flights of a com if it is of that type
	 * @return A vector of flights
	 */
	public Vector<Ticket> getTickets(){
		if (type == RETURN_QUERY_TICKET) return tickets;
		else return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
