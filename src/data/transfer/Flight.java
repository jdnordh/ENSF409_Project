package data.transfer;

import java.io.Serializable;

public class Flight implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String source;
	private String destination;
	private TimeStamp departureTime;
	private TimeStamp duration;
	private Date date;
	private int totalSeats;
	private int availableSeats;
	private double price;
	
	public Flight(){};
	
	/**
	 * String constructor
	 * @param f get a line from the cvs to create a flight
	 */
	public Flight(String f){
		int commas = 0;
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
					setSource(new String(temp));
					temp = "";
					break;
				}
				case 2:{	// Destination
					setDestination(new String(temp));
					temp = "";
					break;
				}
				case 3:{	// Departure time
					setDepartureTime(new TimeStamp(temp));
					temp = "";
					break;
				}
				case 4:{	// Duration
					setDuration(new TimeStamp(temp));
					temp = "";
					break;
				}
				case 5:{	// Date
					setDate(new Date(temp));
					temp = "";
					break;
				}
				case 6:{	// Total seats
					setTotalSeats(Integer.parseInt(temp));
					temp = "";
					break;
				}
				case 7:{	// Available seats
					setavailableSeats(Integer.parseInt(temp));
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
	
	/**
	 * Decrease the available seats on a flight by one
	 */
	public void seatDecrement(){
		this.availableSeats--;
	}
	
	/**
	 * Increase the available seats on a flight by one
	 */
	public void seatIncrement(){
		this.availableSeats++;
	}
	// Get and Set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getavailableSeats() {
		return availableSeats;
	}

	public void setavailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
