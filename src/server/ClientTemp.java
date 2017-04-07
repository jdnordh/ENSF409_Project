package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.ServerOutputCom;
import data.transfer.User;

/**
 * This is a temp class, will not be used
 * 
 * 
 * @author Jordan
 *
 */
public class ClientTemp {
	private Socket socket;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	
	/**
	 * Construct a GameClient
	 * @param serverName Server name
	 * @param portNumber port number
	 */
	public ClientTemp(String serverName, int portNumber) {
		try {
			InetAddress a = InetAddress.getByName(serverName);
			socket = new Socket(a, portNumber);
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			socket.getOutputStream().flush();
			objectIn = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Client will quit from either server telling it to, or the client telling it to
	 */
	public void communicate()  {
		System.out.println("Starting...");
		System.out.println("Got connection: " + socket.toString());
			
		try {
			User u = new User();
			u.setUsername("user");
			u.setPassword("password");
			ClientRequestCom req = new ClientRequestCom(ComTypes.LOG_IN);
			req.setUser(u);
			System.out.println("Writting to stream...");
			objectOut.writeObject(req);
			objectOut.flush();
			System.out.println("Waiting for server...");
			
			ServerOutputCom response = (ServerOutputCom) objectIn.readObject();
			if (response.type() == ComTypes.USER_CONFIRM){
				System.out.println("Logged in");
			}
			else {
				System.out.println("Type: " + response.type());
			}
			
			req = new ClientRequestCom(ComTypes.QUERY);
			req.setQuery(ClientRequestCom.ALL_FLIGHTS);
			objectOut.writeObject(req);
			objectOut.flush();
			
			response = (ServerOutputCom) objectIn.readObject();
			if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
				for (int i = 0; i < response.getFlights().size(); i++){
					System.out.println("Destination: " + response.getFlights().get(i).getDestination());
				}
			}
			else {
				System.out.println("Type: " + response.type());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Closing...");
		try {
			objectIn.close();
			objectOut.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
		System.out.println("BYE");
	}

	public static void main(String[] args) throws IOException  {
		ClientTemp a = new ClientTemp("192.168.1.31", 6000);
		a.communicate();
	}
}
