package server;

public class Task {
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
	
	/** Return from server */
	public static final int RETURN_QUERY = 10;
	public static final int BOOK_CONFIRM = 11;
	
	private int type;
	private int belongsTo;
	//TODO Add constructor with database
	
	/** Check if the task belongs to a certain thread */
	public boolean belongsTo(int x){
		return (x == belongsTo);
	}
	
	
}
