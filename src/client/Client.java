package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import data.transfer.Flight;

public class Client {
	private PrintWriter socketOut;
	private Socket socket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private ObjectInputStream objectIn;
	/**
	 * Construct a GameClient
	 * @param serverName Server name
	 * @param portNumber port number
	 */
	public Client(String serverName, int portNumber) {
		try {
			InetAddress a = InetAddress.getByName(serverName);
			socket = new Socket(a, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			objectIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Client will quit from either server telling it to, or the client telling it to
	 */
	public void communicate()  {
		System.out.println("Starting...");
			try {
				System.out.println("Waiting for server...");
				Vector<String> s = (Vector<String>) objectIn.readObject();
				for (int i = 0; i < s.size(); i++){
					System.out.println(s.get(i) + " ");
				}
				
			} catch (IOException e) {
				System.out.println("Disconected: " + e.getMessage());
			} catch (NullPointerException n){
				System.out.println("Disconected: Connection Reset");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassCastException e){
				System.out.println("Error: " + e.getMessage());
			}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
		System.out.println("Closing...");
	}

	public static void main(String[] args) throws IOException  {
		Client a = new Client("192.168.1.31", 6000);
		a.communicate();
	}
}
