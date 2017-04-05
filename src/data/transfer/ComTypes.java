package data.transfer;

public interface ComTypes {
	/** Client requests */
	public static final int REGISTER_USER = 0;
	public static final int LOG_IN = 1;
	public static final int QUERY = 2;
	public static final int BOOK_FLIGHT = 3;
	public static final int CANCEL_TICKET = 4;
	
	/** Admin client requests*/
	public static final int ADD_FLIGHT = 30;
	public static final int ADD_MULTIPLE_FLIGHTS= 31;
	public static final int REMOVE_FLIGHT = 32;
	public static final int REMOVE_TICKET = 33;
	
	/** Server data output */
	public static final int RETURN_QUERY_FLIGHT = 10;
	public static final int RETURN_QUERY_TICKET = 11;
	public static final int USER_CONFIRM = 12;
	public static final int BOOK_CONFIRM = 13;
	public static final int FLIGHT_DELETE_CONFIRM = 14;
	public static final int TICKET_DELETE_CONFIRM = 15;
	
	
}
