package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Run this class to start the client
 * 
 * 
 * @author Jordan Nordh and Jeremy Phillips
 *
 */
public class ClientTemp {
	private Socket socket;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	private ClientSideInputThread in;
	
	/**
	 * Construct a GameClient
	 * @param serverName Server name
	 * @param portNumber port number
	 */
	public ClientTemp(String serverName, int portNumber) {
		try {
			InetAddress a = InetAddress.getByName(serverName);
			System.out.println("Connecting to server...");
			socket = new Socket(a, portNumber);
			System.out.println("Connected to server");
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			socket.getOutputStream().flush();
			objectIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Client will quit from either server telling it to, or the client telling it to
	 */
	public void communicate()  {
		System.out.println("Starting...");		
		LoginWindow win = new LoginWindow(objectOut);
		System.out.println("Got connection: " + socket.toString());
		in = new ClientSideInputThread(objectIn, objectOut);
		in.setLoginWindow(win);
		in.start();
		System.out.println("ClientInputThread running...");

			
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
		//ClientTemp a = new ClientTemp("70.77.96.98", 6000);
		ClientTemp a = new ClientTemp("192.168.1.31", 6000);
		a.communicate();
	}
}
