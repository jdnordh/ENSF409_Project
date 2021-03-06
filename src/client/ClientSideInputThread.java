package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import data.transfer.ComTypes;
import data.transfer.Flight;
import data.transfer.ServerOutputCom;
import data.transfer.Ticket;
import data.transfer.User;

public class ClientSideInputThread extends Thread{
	private ObjectInputStream in;
	/** This is only used to add to the guis */
	private ObjectOutputStream out;
	private LoginWindow loginWindow;
	private CustomerInterface customerGui;
	private AdminInterface adminGui;
	private User user;
	
	
	public ClientSideInputThread(ObjectInputStream i, ObjectOutputStream o){
		super("Input Thread");
		in = i;
		out = o;
	}
	
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				 ServerOutputCom response = (ServerOutputCom) in.readObject();
				 System.out.println("Type is: " + response.type());
				 if (response.type() == ComTypes.USER_CONFIRM){
					 user = response.getUser();
					 CustomerInterface c = new CustomerInterface(user, out);
					 c.setVisible(true);
					 setCustomerGui(c); 
					 setAdminGui(null);
					 loginWindow.close();
				 }
				 else if (response.type() == ComTypes.REGISTER_CONFIRM){
					 user = response.getUser();
					 CustomerInterface c = new CustomerInterface(user, out);
					 c.setVisible(true);
					 setCustomerGui(c);  
					 setAdminGui(null);
					 loginWindow.close();
				 }
				 else if (response.type() == ComTypes.USER_CONFIRM_ADMIN){
					 user = response.getUser();
					 AdminInterface c = new AdminInterface(user, out);
					 setCustomerGui(null); 
					 setAdminGui(c);
					 loginWindow.close();
				 }
				 else if (response.type() == ComTypes.FAILED_LOGIN){
					JOptionPane.showMessageDialog(null, "Username/Password Incorrect.");
				 }
				 else if (response.type() == ComTypes.FAILED_REGISTER){
					JOptionPane.showMessageDialog(null, "Username is already taken");
				 }
				 else if (response.type() == ComTypes.BAD_REQUEST){
					 JOptionPane.showMessageDialog(null, "Bad client request");
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 Vector<Flight> f = response.getFlights();
					 try {
						 for (int i = 0; i < f.size(); i++){
							 if (adminGui != null){
								 if (adminGui.getFlightList() != null)
									 adminGui.getFlightList().addElement(f.get(i));
							 }
							 else if (customerGui != null){
								 if (customerGui.getFlightList() != null)
									 customerGui.getFlightList().addElement(f.get(i));
							 }
						 }
					 } catch (NullPointerException e){}
					 catch (ArrayIndexOutOfBoundsException e){}
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_TICKET){
					 Vector<Ticket> t = response.getTickets();
					 for (int i = 0; i < t.size(); i++){
						 System.out.println(t.get(i).toString());
					 }
					 try {
						 for (int i = 0; i < t.size(); i++){
							 if (adminGui != null){
								 if (adminGui.getTicketList() != null)
									 adminGui.getTicketList().addElement(t.get(i));
							 }
							 else if (customerGui != null){
								 if (customerGui.getTicketList() != null)
									 customerGui.getTicketList().addElement(t.get(i));
							 }
						 }
					 }catch (NullPointerException e){
						 
					 }
				 }
				 else if (response.type() == ComTypes.BOOK_CONFIRM){
					 JOptionPane.showMessageDialog(null, "Booking confirmed");
				 }
				 else if (response.type() == ComTypes.FLIGHT_DELETE_CONFIRM){
					 JOptionPane.showMessageDialog(null, "Flight deleted");
				 }
				 else if (response.type() == ComTypes.TICKET_DELETE_CONFIRM){
					 JOptionPane.showMessageDialog(null, "Ticket deleted");
				 }
				 else if (response.type() == ComTypes.FLIGHT_ADD_CONFIRM){
					 JOptionPane.showMessageDialog(null, "Flight(s) added");
				 }
				 else if (response.type() == ComTypes.FLIGHT_DELETE_FAIL){
					 JOptionPane.showMessageDialog(null, "Flight not deleted");
				 }
				 else if (response.type() == ComTypes.TICKET_DELETE_FAIL){
					 JOptionPane.showMessageDialog(null, "Ticket not deleted");
				 }
				 else if (response.type() == ComTypes.FLIGHT_ADD_FAIL){
					 JOptionPane.showMessageDialog(null, "Flight(s) not added");
				 }
				 else if (response.type() == ComTypes.BOOK_FAILED){
					 JOptionPane.showMessageDialog(null, "Tickets not booked");
				 }
				 else if (response.type() == ComTypes.FAILED){
					 JOptionPane.showMessageDialog(null, "Operation failed");
				 }
				
				sleep(1);
			} catch (IOException e) {
				System.out.println("Disconected: " + e.getMessage());
				System.exit(1);
			} catch (NullPointerException n){
				n.printStackTrace();
				System.out.println("Disconected: Connection Reset");
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			} catch (ClassCastException e){
				System.out.println("Error: " + e.getMessage());
			} catch (InterruptedException e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	//Set and Get
	
	public void setLoginWindow(LoginWindow w){
		loginWindow = w;
	}

	public CustomerInterface getCustomerGui() {
		return customerGui;
	}

	public void setCustomerGui(CustomerInterface customerGui) {
		this.customerGui = customerGui;
	}

	public AdminInterface getAdminGui() {
		return adminGui;
	}

	public void setAdminGui(AdminInterface adminGui) {
		this.adminGui = adminGui;
	}
	
	public void setUser(User e){ 
		user = e;
	}
}
