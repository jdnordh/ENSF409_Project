package data.transfer;

import java.util.Vector;

/**
 * This class is returned by the server, and should always be checked for which type it is
 * @author Jordan Nordh
 *
 */
public class ServerOuputCom extends ServerCom{
	private static final long serialVersionUID = 1L;
	/** These fields are selectively used depending on the type of com*/
	
	/** A vector of flights from a search */
	private Vector<Flight> flights;
	/** A vector of tickets from a search */
	private Vector<Ticket> tickets;
	/** A status of an operation*/
	private boolean good;
	
	public ServerOuputCom(int t) {
		super(t);
	}
	
	/**
	 * Return flights com
	 * @param f
	 */
	public ServerOuputCom(Vector<Flight> f) {
		super(RETURN_QUERY_FLIGHT);
		flights = f;
	}
	
	/**
	 * Return tickets com
	 * @param t RETURN_QUERY_TICKET only
	 * @param f Vector of tickets
	 */
	public ServerOuputCom(int t, Vector<Ticket> f) {
		super(t);
		tickets = f;
	}
	
	/**
	 * Confirm status com
	 * @param t Type, USER_CONFIRM, BOOK_CONFIRM
	 * @param b Boolean, whether or not the operation was successful
	 */
	public ServerOuputCom(int t, boolean b){
		super(t);
		good = b;
	}
	
	/**
	 * @return If the operation was successful
	 */
	public boolean wasSuccessful(){
		return good;
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
}
