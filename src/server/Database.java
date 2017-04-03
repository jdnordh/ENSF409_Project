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
	/**
	 * Construct a new medium that connects to the database
	 */
	public Database(){
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf409_project", "client", "This is a client!");
			statement = (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery("select count(*) from tickets");
			while (result.next()) Table_Tickets = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				if (!res.get(i).getSource().equalsIgnoreCase(city)) {
					res.remove(i);
				}
				else {
					i++;
				}
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
				if (!res.get(i).getDestination().equalsIgnoreCase(city)) {
					res.remove(i);
				}
				else {
					i++;
				}
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
			ResultSet result = statement.executeQuery("select * from tickets");
			if (user.isAdmin()){
				while (result.next()){
					String temp = "";
					for (int i = 1; i < 12; i++){
						temp += result.getString(i);
						if (i < 11) temp += ",";
					}
					res.add(new Ticket(temp));
				}
			}
			else {
				while (result.next()){
					String f = "";
					for (int i = 1; i < 12; i++){
						f += (result.getString(i));
						if (i < 11) f += ",";
					}
					res.add(new Ticket(f));
				}
				int i = 0;
				while (i < res.size()){
					if (!res.get(i).equals(user)) {
						res.remove(i);
					}
					else {
						i++;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	synchronized public void bookTicket(User user, Flight flight, int tickets){
		try {			
			// Add the ticket to the ticket table
			while (tickets > 0){
				String query = "INSERT tickets VALUES(?,?,?,?,?,?)";
				PreparedStatement state = connection.clientPrepareStatement(query);
				String temp = "";
				DecimalFormat f = new DecimalFormat("000000");
				temp += f.format(Table_Tickets++);
				state.setString(1, temp);
				state.setString(2, Integer.toString(flight.getId()));
				state.setString(3, user.getFirstName());
				state.setString(4, user.getLastName());
				state.setString(5, user.getBirthday().toString());
				state.setString(6, Double.toString(flight.getPrice()));
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a flight into the system if the user is an admin
	 * @param user User
	 * @param flight Flight to be added
	 * @return True if the flight was added successfully
	 */
	public boolean addFlight(User user, Flight flight){
		if (!user.isAdmin()) return false;
		// check if the flight time is a future value
		LocalDateTime now = LocalDateTime.now();
		Date date = flight.getDate();
		if (date.getYear() < now.getYear()) return false;
		if (date.getYear() == now.getYear()){
			if (date.getMonth() < now.getMonthValue()) return false;
			if (date.getMonth() == now.getMonthValue()){
				if (date.getDay() < now.getDayOfMonth()) return false;
				if (date.getDay() == now.getDayOfMonth()){
					if (flight.getDepartureTime().getHours() < now.getHour()) return false;
					if (flight.getDepartureTime().getHours() == now.getHour()){
						if (flight.getDepartureTime().getMinutes() < now.getMinute()) return false;
					}
				}
			}
		}
		
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
	 * Admin tool: Remove given ticket
	 * @param user User
	 * @param ticket ticket
	 * @return True if removed
	 */
	public boolean removeTicket(User user, Ticket ticket){
		if (!user.isAdmin()) return false;
		//TODO fix this
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
	 * Testing purposes only
	 * @param args
	 */
	public static void main(String [] args){
		Database d = new Database();
		User user = new User("Bob", "Sagit", new Date(1, 1, 2017));
		user.setAdmin(true);
		
		//d.bookTicket(user, new Flight("770168,Amarillo,Port St. Lucie,7:27,4:27,July 21,165,165,597"), 2);
		//if (d.removeTicket(user, new Flight("100100,Calgary,Edmonton,13:38,0:33,August 31 2017,123,123,145"))) System.out.println("Done");
		Ticket bob = new Ticket("00000,770168,Bob,Sagit,January 1 2017,597");
		d.removeTicket(user, bob);
		//d.test();
		//Flight [] f = d.browse();
	}
}
