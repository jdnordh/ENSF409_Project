package data.transfer;

import java.io.Serializable;

public class Ticket implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private int flightId;
	private String source;
	private String destination;
	private TimeStamp departureTime;
	private TimeStamp duration;
	private Date date;
	private double price;
	
	private User user;
	
	/**
	 * Create a ticket from a user and a flight
	 * @param u User
	 * @param f Flight
	 */
	public Ticket(User u, Flight f){
		user = u;
		
		flightId = f.getId();
		source = new String(f.getSource());
		destination = new String(f.getDestination());
		departureTime = new TimeStamp(f.getDepartureTime());
		duration = new TimeStamp(f.getDuration());
		date = new Date(f.getDate());
		price = f.getPrice();
	}
	
	/**
	 * Construct a new ticket with a string of the form:
	 * ID,Flight ID, Source, Destination, Departure Time, Duration, Date, First name, Last name, Birthday, Price
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
				case 1:{	// flight id
					flightId = Integer.parseInt(temp);
					temp = "";
					break;
				}
				case 2:{	// Source
					setSource(new String(temp));
					temp = "";
					break;
				}
				case 3:{	// Destination
					setDestination(new String(temp));
					temp = "";
					break;
				}
				case 4:{	// Departure time
					setDepartureTime(new TimeStamp(temp));
					temp = "";
					break;
				}
				case 5:{	// Duration
					setDuration(new TimeStamp(temp));
					temp = "";
					break;
				}
				case 6:{	// Date
					setDate(new Date(temp));
					temp = "";
					break;
				}
				case 7:{	// First name
					user.setFirstName(new String(temp));
					temp = "";
					break;
				}
				case 8:{	// Last name
					user.setLastName(new String(temp));
					temp = "";
					break;
				}
				case 9:{	// Birthday
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
	public void setId(int l) {
		this.id = l;
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

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public TimeStamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(TimeStamp departureTime) {
		this.departureTime = departureTime;
	}

	public TimeStamp getDuration() {
		return duration;
	}
	
	public void setDuration(TimeStamp duration) {
		this.duration = duration;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
