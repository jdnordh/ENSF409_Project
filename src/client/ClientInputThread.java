package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JOptionPane;

import data.transfer.ComTypes;
import data.transfer.Flight;
import data.transfer.ServerOutputCom;
import data.transfer.User;

public class ClientInputThread extends Thread{
	private ObjectInputStream in;
	private ClientGui clientGui;
	private CustomerGui customerGui;
	private AdminGui adminGui;
	private User user;
	
	public ClientInputThread(ObjectInputStream o){
		super("Input Thread");
		in = o;
	}
	
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				 ServerOutputCom response = (ServerOutputCom) in.readObject();
				
				 if (response.type() == ComTypes.USER_CONFIRM){
					 user = response.getUser();
					 customerGui = new CustomerGui(null, user.getFirstName()); 
				 }
				 else if (response.type() == ComTypes.REGISTER_CONFIRM){
					 user = response.getUser();
					 customerGui = new CustomerGui(null, user.getFirstName());
					 clientGui.dispose();
				 }
				 else if (response.type() == ComTypes.USER_CONFIRM_ADMIN){
					 user = response.getUser();
					 adminGui = new AdminGui(null, user.getFirstName());
					 clientGui.dispose();
				 }
				 else if (response.type() == ComTypes.FAILED_LOGIN){
					JOptionPane.showMessageDialog(null, "Username/Password Incorrect.");
				 }
				 else if (response.type() == ComTypes.FAILED_REGISTER){
					JOptionPane.showMessageDialog(null, "Username is already taken");
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){ //TODO
					Vector<Flight> f = response.getFlights();
					for (int i = 0; i < f.size(); i++){
						//TODO 
					}
					
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				 else if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
					 
				 }
				
				sleep(1);
			} catch (IOException e) {
				System.out.println("Disconected: " + e.getMessage());
			} catch (NullPointerException n){
				System.out.println("Disconected: Connection Reset");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassCastException e){
				System.out.println("Error: " + e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//Set and Get
	public ClientGui getClientGui() {
		return clientGui;
	}


	public void setClientGui(ClientGui clientGui) {
		this.clientGui = clientGui;
	}
	
}
