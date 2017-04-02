package server;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
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
	
	/**
	 * Construct a new medium that connects to the database
	 */
	public Database(){
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf409_project", "client", "This is a client!");
			statement = (Statement) connection.createStatement();
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
					for (int i = 1; i < 6; i++){
						temp += result.getString(i);
					}
					res.add(new Ticket(temp));
				}
			}
			else {
				while (result.next()){
					String f = "";
					for (int i = 1; i < 6; i++){
						f += (result.getString(i));
						if (i < 5) f += ",";
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
				temp += Integer.toString(flight.getId());
				DecimalFormat f = new DecimalFormat("000");
				temp += f.format(flight.getavailableSeats() - flight.getTotalSeats());
				
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
	 * Admin tool: Remove given ticket
	 * @param user User
	 * @param ticket ticket
	 * @return True if removed
	 */
	public boolean removeTicket(User user, Ticket ticket){
		if (!user.isAdmin()) return false;
		try{
			Statement s = (Statement) connection.createStatement();
			String id = Integer.toString(ticket.getId());
			System.out.println(id);
			String sql = "DELETE FROM tickets WHERE id = '" + id + "'";
			int delete = s.executeUpdate(sql);
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
		
		//d.bookTicket(user, new Flight("100100,Calgary,Edmonton,13:38,0:33,August 31 2017,123,123,145"), 3);
		if (d.removeFlight(user, new Flight("100100,Calgary,Edmonton,13:38,0:33,August 31 2017,123,123,145"))) System.out.println("Done");;
		//d.test();
		//Flight [] f = d.browse();
	}
}
