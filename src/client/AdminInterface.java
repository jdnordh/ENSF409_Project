package client;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.*;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Date;
import data.transfer.Flight;
import data.transfer.Ticket;
import data.transfer.TimeStamp;
import data.transfer.User;


public class AdminInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	/** Frames */
	private ObjectOutputStream out;
	private JFrame ticketBrowseWindow;
	private JFrame flightBrowseWindow;
	private JFrame addFlightWindow;
	private JFrame addFlightFileWindow;

	

	/**buttons */
	private JButton browse;
	private JButton addFlight;
	private JButton addFlightFile;
	private JButton cancelBooking;
	private JButton addFlightButton;
	private JButton cancelFlightsButton;
	private JButton deleteFlightButton;
	private JButton mainMenu1;
	private JButton mainMenu2;
	private JButton mainMenu3;
	private JButton mainMenu4;
	private JButton addFlightFileButton;
	private JButton clearAllFields;
	
	/** This is the list of clients */
	protected JList<Flight> flightList;
	protected DefaultListModel<Flight> flightModel;
	
	protected JList<Ticket> ticketList;
	protected DefaultListModel<Ticket> ticketModel;
	
	protected JTextArea textAreat;
	protected JTextArea textAreaf;
	
	/** editable text fields*/
	private JTextField flightNumField;
	private JTextField departureField;
	private JTextField destField;
	private JTextField depDate;
	private JTextField depTime;
	private JTextField durationField;
	private JTextField totSeatsField;
	private JTextField priceField;
	private JTextField fileName;
	private ClientListener listen;
	
	private User user;
	
	public AdminInterface(User u, ObjectOutputStream o) {
		out = o;
		user = u;
		this.setTitle(user.getFirstName() + ": Administration Mode");
		this.setBounds(325, 225, 300, 160);
		this.setLayout(new GridLayout(4, 1));
		
		mainMenu1 = new JButton("Return to Main Menu");
		mainMenu2 = new JButton("Return to Main Menu");
		mainMenu3 = new JButton("Return to Main Menu");
		mainMenu4 = new JButton("Return to Main Menu");
		
		
		flightModel = new DefaultListModel<Flight>();
		flightList = new JList<Flight>(flightModel);
		
		ticketModel = new DefaultListModel<Ticket>();
		ticketList = new JList<Ticket>(ticketModel);
		
		addFlightButton = new JButton("");
		browse = new JButton("");
		addFlight = new JButton("");
		addFlightFile = new JButton("");
		cancelBooking = new JButton("");
		cancelFlightsButton = new JButton("");
		deleteFlightButton = new JButton("");
		addFlightFileButton = new JButton("");
		clearAllFields = new JButton("");
		
		addFlightWindow = new JFrame();
		addFlightWindow();
		addFlightFileWindow = new JFrame();
		addFlightFileWindow();
		ticketBrowseWindow = new JFrame();
		ticketBrowseWindow();
		flightBrowseWindow = new JFrame();
		flightBrowseWindow();
		listen = new ClientListener(this, flightBrowseWindow, ticketBrowseWindow, addFlightWindow, addFlightFileWindow);
		mainMenu1.addActionListener(listen);
		mainMenu2.addActionListener(listen);
		mainMenu3.addActionListener(listen);
		mainMenu4.addActionListener(listen);
		
		
		addFlightButton.addActionListener(listen);
		browse.addActionListener(listen);
		addFlight.addActionListener(listen);
		addFlightFile.addActionListener(listen);
		cancelBooking.addActionListener(listen);
		cancelFlightsButton.addActionListener(listen);
		deleteFlightButton.addActionListener(listen);
		addFlightFileButton.addActionListener(listen);
		clearAllFields.addActionListener(listen);

		this.add(top());
		this.add(basement());
		this.add(center());
		this.add(bottom());
		
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public AdminInterface(boolean b) {
		return;
	}

	private JPanel top() {
		JPanel panel = new JPanel();
		browse = new JButton("Browse Bookings");
		browse.addActionListener(listen);
		panel.add(browse);
		return panel;
	}

	private JPanel center() {
		JPanel panel = new JPanel();
		
		addFlight = new JButton("Add Individual Flight");
		addFlight.addActionListener(listen);
		panel.add(addFlight);
		
		return panel;
	}

	private JPanel bottom() {
		JPanel panel = new JPanel();
		
		addFlightFile = new JButton("Add Flights From File");
		addFlightFile.addActionListener(listen);
		panel.add(addFlightFile);
		
		return panel;
	}
	
	private JPanel basement() {
		JPanel panel = new JPanel();
		
		cancelFlightsButton = new JButton("Cancel flights");
		cancelFlightsButton.addActionListener(listen);
		panel.add(cancelFlightsButton);
		
		return panel;
	}
	
	public void ticketBrowseWindow(){
		ticketBrowseWindow.setTitle("Browse Bookings");
		ticketBrowseWindow.setBounds(325, 225, 500, 400);

		ticketBrowseWindow.add(new JLabel("Bookings:"), BorderLayout.NORTH);
		
		ticketModel = new DefaultListModel<Ticket>();
		ticketList = new JList<Ticket>(ticketModel);
		ticketList.setFont(new Font("Courier New", Font.BOLD, 12));
		//display.addListSelectionListener(new ListAction());
		ticketList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ticketList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		ticketList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textAreat = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textAreat.setFont(new Font("Courier New", Font.BOLD, 12));
		textAreat.setEditable(false);
		textAreaScrollPane = new JScrollPane(ticketList);
		textAreaScrollPane.setPreferredSize(new Dimension(400, 300));
		ticketBrowseWindow.add(textAreaScrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		cancelBooking = new JButton("Cancel Selected Booking");
		cancelBooking.addActionListener(listen);
		panel.add(cancelBooking);
		panel.add(mainMenu1);
		ticketBrowseWindow.add(panel, BorderLayout.SOUTH);
		ticketBrowseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void flightBrowseWindow(){
		flightBrowseWindow.setTitle("Browse Flights");
		flightBrowseWindow.setBounds(325, 225, 500, 400);
		
		flightBrowseWindow.add(new JLabel("Flights:"), BorderLayout.NORTH);
		
		flightModel = new DefaultListModel<Flight>();
		flightList = new JList<Flight>(flightModel);
		flightList.setFont(new Font("Courier New", Font.BOLD, 12));
		//display.addListSelectionListener(new ListAction());
		flightList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		flightList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		flightList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textAreaf = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textAreaf.setFont(new Font("Courier New", Font.BOLD, 12));
		textAreaf.setEditable(false);
		textAreaScrollPane = new JScrollPane(flightList);
		textAreaScrollPane.setPreferredSize(new Dimension(400, 300));
		flightBrowseWindow.add(textAreaScrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		deleteFlightButton = new JButton("Delete Selected Flight");
		deleteFlightButton.addActionListener(listen);
		panel.add(deleteFlightButton);
		panel.add(mainMenu2);
		flightBrowseWindow.add(panel, BorderLayout.SOUTH);
		flightBrowseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addFlightWindow(){
		addFlightWindow.setTitle("Add Flight");
		addFlightWindow.setBounds(325, 225, 460, 400);
		addFlightWindow.setLayout(new GridLayout(9, 1));
		
		//row one
		JPanel rOne = new JPanel();
		rOne.add(new JLabel("Flight Number:"));
		rOne.add(Box.createRigidArea(new Dimension(5, 10)));
		flightNumField = new JTextField(20);;
		rOne.add(flightNumField);
		rOne.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rOne);
		
		//row two
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Departs From:"));
		rTwo.add(Box.createRigidArea(new Dimension(5, 10)));
		departureField = new JTextField(20);
		rTwo.add(departureField);
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rTwo);
		
		//row three
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Destination:"));
		rThree.add(Box.createRigidArea(new Dimension(5, 10)));
		destField = new JTextField(20);
		rThree.add(destField);
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rThree);
		
		//row four
		JPanel rFour = new JPanel();
		rFour.add(new JLabel("Departure Date:"));
		rFour.add(Box.createRigidArea(new Dimension(5, 10)));
		depDate = new JTextField(20);
		rFour.add(depDate);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rFour);
		
		//row five
		JPanel rFive = new JPanel();
		rFive.add(new JLabel("Departure Time:"));
		rFive.add(Box.createRigidArea(new Dimension(5, 10)));
		depTime = new JTextField(20);
		rFive.add(depTime);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rFive);
		
		//row six
		JPanel rSix = new JPanel();
		rSix.add(new JLabel("Flight Duration:"));
		rSix.add(Box.createRigidArea(new Dimension(5, 10)));
		durationField = new JTextField(20);
		rSix.add(durationField);
		rSix.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rSix);
		
		//row seven
		JPanel rSeven = new JPanel();
		rSeven.add(new JLabel("Total Seats:"));
		rSeven.add(Box.createRigidArea(new Dimension(5, 10)));
		totSeatsField = new JTextField(20);
		rSeven.add(totSeatsField);
		rSeven.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rSeven);
		
		//row eight
		JPanel rEight = new JPanel();
		rEight.add(new JLabel("Price:"));
		rEight.add(Box.createRigidArea(new Dimension(5, 10)));
		priceField = new JTextField(20);
		rEight.add(priceField);
		rEight.add(Box.createRigidArea(new Dimension(10, 10)));
		addFlightWindow.add(rEight);
		
		//row nine
		JPanel rNine = new JPanel();
		addFlightButton = new JButton("Add Flight");
		rNine.add(addFlightButton);
		rNine.add(Box.createRigidArea(new Dimension(15, 10)));
		rNine.add(mainMenu3);
		clearAllFields.setText("Clear all fields");
		rNine.add(clearAllFields);
		addFlightWindow.add(rNine);
		
		addFlightWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addFlightFileWindow(){
		addFlightFileWindow.setTitle("Add Flight File");
		addFlightFileWindow.setBounds(325, 225, 400, 100);
		
		JPanel center = new JPanel();
		center.add(new JLabel("File Name:"));
		center.add(Box.createRigidArea(new Dimension(5, 10)));
		fileName = new JTextField(20);
		center.add(fileName);
		addFlightFileWindow.add(center, BorderLayout.CENTER);

		
		JPanel bottom = new JPanel();
		addFlightFileButton = new JButton("Add Flights From File");
		bottom.add(addFlightFileButton);
		bottom.add(Box.createRigidArea(new Dimension(5, 10)));
		bottom.add(mainMenu4);
		addFlightFileWindow.add(bottom, BorderLayout.SOUTH);

		addFlightFileWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	private class ClientListener implements ActionListener
	{
		private AdminInterface start;
		private JFrame addFlightWindow;
		private JFrame addFlightFileWindow;
		private JFrame ticketBrowseWindow;
		private JFrame flightBrowseWindow;
		
		/**
		 * Make a new client listener
		 * @param a
		 * @param flightBrowseWindow Flight browse
		 * @param ticketBrowseWindow Ticket browse
		 * @param addFlightWindow Add flight
		 * @param addFlightFileWindow Add flight file
		 */
		public ClientListener(AdminInterface a, JFrame flightBrowseWindow, 
				JFrame ticketBrowseWindow, JFrame addFlightWindow, JFrame addFlightFileWindow){
			super();
			start = a;
			this.flightBrowseWindow = flightBrowseWindow;
			this.ticketBrowseWindow = ticketBrowseWindow;
			this.addFlightWindow = addFlightWindow;
			this.addFlightFileWindow = addFlightFileWindow;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == browse) {
				start.setVisible(false);
				ticketBrowseWindow.setVisible(true);
				ClientRequestCom req = new ClientRequestCom(ComTypes.QUERY);
				req.setQuery(ClientRequestCom.ALL_TICKETS);
				req.setUser(user);
				try {
					out.writeObject(req);
					out.flush();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			
			else if (e.getSource() == addFlight) {
				start.setVisible(false);
				addFlightWindow.setVisible(true);
			}
			
			else if (e.getSource() == addFlightFile) {
				start.setVisible(false);
				addFlightFileWindow.setVisible(true);
			}
			else if (e.getSource() == cancelFlightsButton) {
				start.setVisible(false);
				flightModel.removeAllElements();
				flightBrowseWindow.setVisible(true);
				ClientRequestCom req = new ClientRequestCom(ComTypes.QUERY);
				req.setQuery(ClientRequestCom.ALL_FLIGHTS);
				req.setUser(user);
				try {
					out.writeObject(req);
					out.flush();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			
			else if (e.getSource() == mainMenu1 || e.getSource() == mainMenu2
					|| e.getSource() == mainMenu3 || e.getSource() == mainMenu4) {
				if(ticketBrowseWindow.isVisible())
				{
					ticketBrowseWindow.setVisible(false);
					start.setVisible(true);
				}
				else if(addFlightWindow.isVisible())
				{
					addFlightWindow.setVisible(false);
					start.setVisible(true);
				}
				else if(flightBrowseWindow.isVisible())
				{
					flightBrowseWindow.setVisible(false);
					start.setVisible(true);
				}
				else if(addFlightFileWindow.isVisible())
				{
					addFlightFileWindow.setVisible(false);;
					start.setVisible(true);
				}
				
			}
			else if (e.getSource() == addFlightFileButton){
				System.out.println("Getting flights from file");
				String file = fileName.getText();
				String input;
				BufferedReader in;
				Vector<Flight> flights = new Vector<Flight>();
				try {
					int dates = 0;
					in = new BufferedReader(new FileReader(file));
					while ((input = in.readLine()) != null){
						Flight temp = new Flight(input);
						try {
							LocalDateTime now = LocalDateTime.now();
							Date currentDate = new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
							TimeStamp currentTime = new TimeStamp(now.getHour(), now.getMinute());
							if (currentDate.compareTo(temp.getDate()) > 0) {
								System.out.println("Date invlaid");
								throw new ClassNotFoundException();
							}
							if (currentDate.compareTo(temp.getDate()) == 0){
								if (currentTime.compareTo(temp.getDepartureTime()) > 0) {
									System.out.println("Date invlaid");
									throw new ClassNotFoundException();
								}
							}
						flights.add(temp);
						} catch (ClassNotFoundException e1){
							dates++;
						}
					}
					if (dates > 0) JOptionPane.showMessageDialog(null, "Error: " + dates + " dates were invalid");
					in.close();
					ClientRequestCom req = new ClientRequestCom(ComTypes.ADD_MULTIPLE_FLIGHTS);
					req.setMultiple_flights(flights);
					req.setUser(user);
					try {
						out.writeObject(req);
						out.flush();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
					}
					
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == cancelBooking){
				ClientRequestCom req = new ClientRequestCom(ComTypes.CANCEL_TICKET);
				Ticket t = ticketList.getSelectedValue();
				req.setTicket(t);
				req.setUser(user);
				try {
					out.writeObject(req);
					out.flush();
					ticketModel.removeElement(t);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == deleteFlightButton){
				ClientRequestCom req = new ClientRequestCom(ComTypes.REMOVE_FLIGHT);
				Flight f = flightList.getSelectedValue();
				req.setFlight(f);
				req.setUser(user);
				try {
					out.writeObject(req);
					out.flush();
					flightModel.removeElement(f);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == clearAllFields){
				flightNumField.setText("");
				departureField.setText("");
				destField.setText("");
				depTime.setText("");
				depDate.setText("");
				durationField.setText("");
				totSeatsField.setText("");
				priceField.setText("");
			}
			else if (e.getSource() == addFlightButton){
				try {
					// error checking
					if (departureField.getText().equals("") || destField.getText().equals("") ||
							depTime.getText().equals("") || depDate.getText().equals("") || 
							durationField.getText().equals("") || totSeatsField.getText().equals("") ||
							priceField.getText().equals("") || flightNumField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Error: One or more of the required field are empty");
					}
					else if (Integer.parseInt(totSeatsField.getText()) < 0 || 
							Double.parseDouble(priceField.getText()) < 0){
						JOptionPane.showMessageDialog(null, "Error: Numbers can't be negative");
					}
					else {
						// error checking done
						ClientRequestCom req = new ClientRequestCom(ComTypes.ADD_MULTIPLE_FLIGHTS);
						Vector<Flight> flights = new Vector<Flight>();
						String temp = "";
						temp += flightNumField.getText();
						temp += ",";
						temp += departureField.getText();
						temp += ",";
						temp += destField.getText();
						temp += ",";
						temp += depTime.getText();
						temp += ",";
						temp += durationField.getText();
						temp += ",";
						temp += depDate.getText();
						temp += ",";
						temp += totSeatsField.getText();
						temp += ",";
						temp += totSeatsField.getText();
						temp += ",";
						temp += priceField.getText();
						Flight a = new Flight(temp);
						System.out.println(temp);
						flights.add(a);
						flights.add(a);
						System.out.println(flights.size() + " | " + flights.get(0).toString());
						try {
							out.writeObject(req);
							out.flush();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
						} catch (NumberFormatException e1){
							JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
						}
					}
				} catch (NullPointerException e2){
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage());
				} catch (NumberFormatException e2){
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e2.getMessage());
				}
			}
			
		}
	}

	public DefaultListModel<Flight> getFlightList(){
		return this.flightModel;
	}
	
	public DefaultListModel<Ticket> getTicketList(){
		return this.ticketModel;
	}
}
