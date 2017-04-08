package server;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.time.LocalDateTime;
import java.text.DecimalFormat;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import data.transfer.Flight;
import data.transfer.Ticket;
import data.transfer.TimeStamp;
import data.transfer.User;
import data.transfer.Date;
/**
 * This class is used by the server to connect to the database
 * @author Jordan Nordh & Jeremy Phillips
 *
 */
public class Database {
	private Connection connection;
	private Statement statement;
	
	private int Table_Tickets;
	private int amount_users;
	/**
	 * Construct a new medium that connects to the database
	 */
	public Database(){
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf409_project", "client", "This is a client!");
			statement = (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery("select count(*) from tickets");
			while (result.next()) Table_Tickets = result.getInt(1);
			result = statement.executeQuery("select count(*) from users");
			while (result.next()) amount_users = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempt to log in with user credentials
	 * @param user User
	 * @return True if log in was successful
	 */
	public boolean login(User user){
		try {
			System.out.println("Logging in: " + user.getUsername());
			ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username = '" + user.getUsername() + "'");
			while (result.next()){
				String p = result.getString(3);
				user.setFirstName(result.getString(4));
				user.setLastName(result.getString(5));
				user.setBirthday(new Date(result.getString(6)));
				int admin = result.getInt(7);
				if (admin == 1 && p.equals(user.getPassword())) {
					user.setAdmin(true);
					System.out.println(user.getUsername() + " is admin");
				}
				else {
					System.out.println(user.getUsername() + " is not admin");
					user.setAdmin(false);
				}
				return p.equals(user.getPassword());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * Register a new user, cannot register a new admin, that is only done server side
	 * @param user
	 * @return True if register was successful
	 */
	public boolean register(User user){
		try {
			ResultSet result = statement.executeQuery("SELECT username FROM users WHERE username = '" + user.getUsername() + "'");
			while (result.next()){
				return false;
			}
			
			String query = "INSERT users VALUES(?,?,?,?,?,?,?)";
			PreparedStatement state = connection.clientPrepareStatement(query);
			state.setString(1, Integer.toString(++amount_users));
			state.setString(2, user.getUsername());
			state.setString(3, user.getPassword());
			state.setString(4, user.getFirstName());
			state.setString(5, user.getLastName());
			state.setString(6, user.getBirthday().toString());
			state.setString(7, Integer.toString(0));
			state.executeUpdate();
			user.setAdmin(false);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Get all current available flights 
	 * @return A vector of flights 
	 */
	public Vector<Flight> browse(){
		Vector<Flight> res = new Vector<Flight>();
		try {
			ResultSet result = statement.executeQuery("select * from flights");
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i));
					if (i < 9) f += ",";
				}
				res.add(new Flight(f));
			}
			int i = 0;
			while (i < res.size()){
				if (res.get(i).getavailableSeats() > 0) {
					i++;
				}
				else {
					res.remove(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Search for flights by city, will return flights that have the city as a source or destination
	 * @param city City to search for
	 * @return A vector of flights with the results of the search
	 */
	public Vector<Flight> searchCity(String city){
		Vector<Flight> res = new Vector<Flight>();
		try {
			ResultSet result = statement.executeQuery("select * from flights");
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i));
					if (i < 9) f += ",";
				}
				res.add(new Flight(f));
			}
			int i = 0;
			while (i < res.size()){
				if (res.get(i).getSource().equalsIgnoreCase(city)) {
					i++;
				}
				else if (res.get(i).getDestination().equalsIgnoreCase(city)){
					i++;
				}
				else {
					res.remove(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Search for flights by city, will return flights that have the city as a source
	 * @param city City to search for
	 * @return A vector of flights with the results of the search
	 */
	public Vector<Flight> searchSourceCity(String city){
		Vector<Flight> res = new Vector<Flight>();
		try {
			ResultSet result = statement.executeQuery("select * from flights where source = '" + city + "'");
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i));
					if (i < 9) f += ",";
				}
				res.add(new Flight(f));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Search for flights by city, will return flights that have the city as a destination
	 * @param city City to search for
	 * @return A vector of flights with the results of the search
	 */
	public Vector<Flight> searchDestinationCity(String city){
		Vector<Flight> res = new Vector<Flight>();
		try {
			ResultSet result = statement.executeQuery("select * from flights where destination = '" + city + "'");
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i));
					if (i < 9) f += ",";
				}
				res.add(new Flight(f));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Search for flights by date of departure
	 * @param date Date
	 * @return A vector of flights
	 */
	public Vector<Flight> searchDate(Date date){
		Vector<Flight> res = new Vector<Flight>();
		try {
			ResultSet result = statement.executeQuery("select * from flights where date = '" + date.toString() + "'");
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i));
					if (i < 9) f += ",";
				}
				res.add(new Flight(f));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Get the tickets associated with a certain user, if admin, return all tickets
	 * @param user User
	 * @return An array of tickets that are associated to the user, null if no tickets found
	 */
	public Vector<Ticket> getTickets(User user){
		Vector<Ticket> res = new Vector<Ticket>();
		try {
			if (user.isAdmin()){
				ResultSet result = statement.executeQuery("select * from tickets");
				System.out.println("Getting tickets for admin...");
				while (result.next()){
					String temp = "";
					for (int i = 1; i < 13; i++){
						temp += result.getString(i);
						if (i < 12) temp += ",";
					}
					res.add(new Ticket(temp));
				}
			}
			else {
				System.out.println("Getting tickets for user...");
				ResultSet result = statement.executeQuery("select * from tickets where username = '" + user.getUsername() + "'");
				while (result.next()){
					String f = "";
					for (int i = 1; i < 13; i++){
						f += (result.getString(i));
						if (i < 12) f += ",";
					}
					res.add(new Ticket(f));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < res.size(); i++){
			 System.out.println(res.get(i).toString());
		 }
		return res;
	}
	
	// The following methods will be for adding/removing to/from the database
	
	/**
	 * Book a ticket for a given user
	 * @param user User
	 * @param flight Flight
	 * @param tickets Amount of tickets
	 */
	synchronized public boolean bookTicket(User user, Flight flight, int tickets){
		try {
			if (tickets > flight.getavailableSeats()) return false;
			// Add the ticket to the ticket table
			while (tickets > 0){
				String query = "INSERT tickets VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement state = connection.clientPrepareStatement(query);
				String temp = "";
				DecimalFormat f = new DecimalFormat("000000");
				temp += f.format(Table_Tickets++);
				state.setString(1, temp);
				state.setString(2, Integer.toString(flight.getId()));
				state.setString(3, flight.getSource());
				state.setString(4, flight.getDestination());
				state.setString(5, flight.getDepartureTime().toString());
				state.setString(6, flight.getDuration().toString());
				state.setString(7, flight.getDate().toString());
				state.setString(8, user.getUsername());
				state.setString(9, user.getFirstName());
				state.setString(10, user.getLastName());
				state.setString(11, user.getBirthday().toString());
				state.setString(12, Double.toString(flight.getPrice()));
				state.executeUpdate();
				flight.seatDecrement();
				tickets--;
			}
			// Update flight table with remaining seats
			String query = "UPDATE flights SET seatsleft = ? WHERE id = ?";
			PreparedStatement state = connection.clientPrepareStatement(query);
			state.setString(1, Integer.toString(flight.getavailableSeats()));
			state.setString(2, Integer.toString(flight.getId()));
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Add a flight into the system if the user is an admin
	 * @param user User
	 * @param flight Flight to be added
	 * @return True if the flight was added successfully
	 */
	public boolean addFlight(User user, Flight flight){
		if (!user.isAdmin()) {
			System.out.println("User is not admin");
			return false;
		}
		// check if the flight time is a future value
		
		LocalDateTime now = LocalDateTime.now();
		Date currentDate = new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
		System.out.println("Comparing now: " + currentDate.toString() + " to flight time: " + flight.getDate().toString());
		TimeStamp currentTime = new TimeStamp(now.getHour(), now.getMinute());
		if (currentDate.compareTo(flight.getDate()) > 0) {
			System.out.println("Date invlaid");
			return false;
		}
		if (currentDate.compareTo(flight.getDate()) == 0){
			if (currentTime.compareTo(flight.getDepartureTime()) > 0) {
				System.out.println("Date invlaid");
				return false;
			}
		}
		System.out.println("Current date is: " + currentDate.toString());
		
		try{
			String query = "INSERT flights VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement state = connection.clientPrepareStatement(query);
			state.setString(1, Integer.toString(flight.getId()));
			state.setString(2, flight.getSource());
			state.setString(3, flight.getDestination());
			state.setString(4, flight.getDepartureTime().toString());
			state.setString(5, flight.getDuration().toString());
			state.setString(6, flight.getDate().toString());
			state.setString(7, Integer.toString(flight.getTotalSeats()));
			state.setString(8, Integer.toString(flight.getTotalSeats()));
			state.setString(9, Double.toString(flight.getPrice()));
			state.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Admin tool: remove given flight, and all tickets booked for the flight
	 * @param user User 
	 * @param flight Flight to be removed
	 * @return True if removed successfully
	 */
	public boolean removeFlight(User user, Flight flight){
		if (!user.isAdmin()) return false;
		try{
			Statement s = (Statement) connection.createStatement();
			String sql = "DELETE FROM flights WHERE id = '" + Integer.toString(flight.getId()) + "'";
			int delete = s.executeUpdate(sql);
			
			s = (Statement) connection.createStatement();
			sql = "DELETE FROM tickets WHERE flightid = '" + Integer.toString(flight.getId()) + "'";
			delete += s.executeUpdate(sql);
			
			if(delete > 1){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Add multiple flights via a vector of flights
	 * @param user User
	 * @param flights Vector of flights
	 * @return
	 */
	public boolean addMultipleFlights(User user, Vector<Flight> flights){
		for (int i = 0; i < flights.size(); i++){
			this.addFlight(user, flights.get(i));
		}
		return true;
	}
	
	/**
	 *  Remove given ticket
	 * @param user User
	 * @param ticket ticket
	 * @return True if removed
	 */
	public boolean removeTicket(User user, Ticket ticket){
		if (!user.isAdmin()){
			if (!user.getFirstName().equals(ticket.getUser().getFirstName())) return false;
			if (!user.getLastName().equals(ticket.getUser().getLastName())) return false;		// Check to make sure a user is deleting their own tickets
		}
		try{
			Statement s = (Statement) connection.createStatement();
			String id = Integer.toString(ticket.getId());
			String sql = "DELETE FROM tickets WHERE id = '" + id + "'";
			int delete = s.executeUpdate(sql);
			updateSeats(ticket.getFlightId(), -1);
			if(delete == 1){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Update the amount of seats on a flight by x
	 * @param x 
	 */
	private void updateSeats(int flightID, int x){
		try {
			
			ResultSet result = statement.executeQuery("select seatsleft from flights where id = '" + Integer.toString(flightID) + "'");
			int seats = 0;
			while (result.next()) seats = result.getInt(1);
			
			seats += x;
			
			String query = "UPDATE flights SET seatsleft = ? WHERE id = ?";
			PreparedStatement state = connection.clientPrepareStatement(query);
			state.setString(1, Integer.toString(seats));
			state.setString(2, Integer.toString(flightID));
			state.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Test the connection by printing all flights to the console
	 */
	public void test(){
		try {
			ResultSet result = statement.executeQuery("select * from flights");
			while (result.next()){
				for (int i = 1; i < 10; i++){
					System.out.print(result.getString(i) + ",");
				}
				System.out.println();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Testing purposes only, DO NOT RUN 
	 * @param args
	 */
	public static void main(String [] args){
		Database d = new Database();
		User user = new User("Bob", "Sagit", new Date(1, 1, 2017));
		user.setAdmin(true);
		user.setUsername("admin");
		user.setPassword("password");
		if (d.login(user)) System.out.println("Logged in");
		else System.out.println("Log in failed");
		
		
		//user = new User("Bob", "Sagit", new Date(1, 1, 2017));
		//user.setUsername("bobsagit");
		//user.setPassword("123456789");
		//if (d.register(user)) System.out.println("Registered");
		//else System.out.println("Register failed");
	}
}
