package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import data.transfer.ServerOutputCom;

public class ClientInputThread extends Thread{
	private ObjectInputStream in;
	
	public ClientInputThread(ObjectInputStream o){
		super("Input Thread");
		in = o;
	}
	
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				 ServerOutputCom response = (ServerOutputCom) in.readObject();
				
				
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
	
}
