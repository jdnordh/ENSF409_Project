package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import data.transfer.Flight;
import data.transfer.Ticket;
import data.transfer.Date;
/**
 * This class is used by the server to connect to the database
 * @author Jordan Nordh & Jeremy Phillips
 *
 */
public class Database {
	private Connection connection;
	private Statement statement;
	//TODO add time of departure to flights
	/**
	 * Construct a new medium that connects to the database
	 */
	public Database(){
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf409_project", "client", "This is a client!");
			statement = (Statement) connection.createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Get all current available flights 
	 * @return and array of flights 
	 */
	public Flight [] browse(){
		Flight [] res = null;
		try {
			ResultSet length = statement.executeQuery("select count(*) from flights");
			int l = 1;
			while (length.next()) l = Integer.parseInt(length.getString(1));
			res = new Flight[l];
			ResultSet result = statement.executeQuery("select * from flights");
			
			int j = 0;
			while (result.next()){
				String f = "";
				for (int i = 1; i < 10; i++){
					f += (result.getString(i) + ",");
				}
				res[j] = new Flight(f);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	
	
	public Ticket [] getTickets(String firstname, String lastname, Date birth){
		Ticket [] result = null;
		
		return result;
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
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Testing purposes only
	 * @param args
	 */
	public static void main(String [] args){
		Database d = new Database();
		//d.test();
		d.browse();
		
	}
}
