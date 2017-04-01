package data.transfer;

public class Flight {
	private int id;
	private String source;
	private String destination;
	private TimeStamp departureTime;
	private TimeStamp duration;
	private Date date;
	private int totalSeats;
	private int aSeats;
	private double price;
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
					
					temp = "";
				}
				case 1:{	// Source
					
					temp = "";
				}
				case 2:{	// Destination
					
					temp = "";
				}
				case 3:{	// Departure time
					
					temp = "";
				}
				case 4:{	// Duration
					
					temp = "";
				}
				case 5:{	// Date
					
					temp = "";
				}
				case 6:{	// Total seats
					
					temp = "";
				}
				case 7:{	// Available seats
					
					temp = "";
				}
				case 8:{	// Price
					
					temp = "";
				}
				}
				commas++;
			}
			temp += Character.toString(f.charAt(i));			
		}
	}
}
