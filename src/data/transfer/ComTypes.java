package data.transfer;

public interface ComTypes {
	/** Client requests */
	public static final int GET_FLIGHTS = 0;
	public static final int ACCESS_FLIGHT = 1;
	public static final int REQUEST_SEAT = 2;
	public static final int BOOK_SEAT = 3;
	public static final int RELEASE_SEAT = 4;
	
	/** Admin client requests*/
	public static final int ADD_FLIGHT = 30;
	public static final int ADD_MULTIPLE_FLIGHTS= 31;
	public static final int REMOVE_FLIGHT = 32;
	public static final int REMOVE_TICKET = 33;
	
	/** Server data output */
	public static final int GIVE_FLIGHTS = 10;
	public static final int GIVE_SPECIFIC_FLIGHT = 11;
	public static final int GIVE_SEAT_LOCK = 12;
	public static final int BOOK_CONFIRM = 13;
	
	
	
}
