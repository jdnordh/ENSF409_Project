package data.transfer;

public class Ticket {
	private int id;
	private Flight flight;
	private User user;
	private double price;
	
	/**
	 * Create a ticket from a user and a flight
	 * @param u User
	 * @param f Flight
	 */
	public Ticket(User u, Flight f){
		user = u;
		flight = f;
		price = f.getPrice();
	}
	
	/**
	 * Construct a new ticket with a string of the form: "012345,John,Smith,October 8 1990,350"
	 * @param f
	 */
	public Ticket(String f){
		int commas = 0;
		user = new User();
		String temp = "";
		for (int i = 0; i < f.length(); i++){
			if (f.charAt(i) == ',') {
				switch (commas){
				case 0:{	// ID
					setId(Integer.parseInt(temp));
					temp = "";
					break;
				}
				case 1:{	// Source
					user.setFirstName(new String(temp));
					temp = "";
					break;
				}
				case 2:{	// Destination
					user.setLastName(new String(temp));
					temp = "";
					break;
				}
				case 3:{	// Departure time
					user.setBirthday(new Date(temp));
					temp = "";
					break;
				}
				}
				commas++;
			}
			else temp += Character.toString(f.charAt(i));			
		}
		setPrice(Double.parseDouble(temp));
	}
	
	
	
	// Set and get
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
