package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Flight;
import data.transfer.ServerOutputCom;
import data.transfer.User;

public class Client {
	private Socket socket;
	private ObjectInputStream objectIn;
	protected ObjectOutputStream objectOut;
	private ClientInputThread in;
	private ClientGui Gui;
	
	/**
	 * Construct a Client
	 * @param serverName Server name
	 * @param portNumber port number
	 */
	public Client(String serverName, int portNumber) {
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
		Gui = new ClientGui(null, objectOut, objectIn);
		System.out.println("Got connection: " + socket.toString());
		in = new ClientInputThread(objectIn);
		in.start();
		System.out.println("ClientInputThread running...");

			
			/*response = (ServerOutputCom) objectIn.readObject();
			if (response.type() == ComTypes.RETURN_QUERY_FLIGHT){
				for (int i = 0; i < response.getFlights().size(); i++){
					System.out.println("Destination: " + response.getFlights().get(i).getDestination());
				}
			}
			else {
				System.out.println("Type: " + response.type());
			}*/
			
		try {
			in.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Closing...");
		try {
			objectIn.close();
			objectOut.close();
			socket.close();
		} catch (IOException e3) {
			System.out.println("Closing error: " + e3.getMessage());
		}
		System.out.println("BYE");
	}

	public static void main(String[] args) throws IOException  {
		Client a = new Client("70.77.96.98", 6000);
		a.communicate();
	}
}
