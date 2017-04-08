package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Date;
import data.transfer.Flight;
import data.transfer.Ticket;
import data.transfer.User;



public class CustomerInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Flight selectedFlight;
	/** all frames*/
	private JOptionPane dialogFrame;
	private JFrame myBookingsWindow;
	
	private static ObjectOutputStream objectOut;
		
	/** Combo box */
	protected JComboBox<String> comboBoxSearch;
	protected char comboBoxSelectionDay1;
	
	/** editable text fields*/
	private JTextField searchFlights;
	private JTextField departureField;
	private JTextField destField;
	private JTextField depDate;
	private JTextField depTime;
	private JTextField durationField;
	private JTextField totSeatsField;
	private JTextField remSeatsField;
	private JTextField priceField;
	private JTextField seatsToBook;
	
	/**buttons */
	private JButton searchButton;
	private JButton clearButton;
	private JButton myBookings;
	private JButton bookFlight;
	private JButton clearFlight;
	private JButton printTicket;
	private JButton cancelTicket;
	private JButton returnToMain;
	private JButton browseFlights;
	
	/** This is the list of clients */
	protected JList<Flight> flightList;
	private DefaultListModel<Flight> flightModel;
	protected JList<Ticket> ticketList;
	private DefaultListModel<Ticket> ticketModel;
	
	/** This is the list of textAreas*/
	protected JTextArea textArea;
	protected JTextArea myTextArea;
	
	/**
	 * Construct a gui for a given username
	 * @param u User
	 */
	public CustomerInterface(User u, ObjectOutputStream o) {
		objectOut = o;
		user = u;
		this.setTitle("Welcome " + user.getFirstName());
		this.setBounds(325, 225, 800, 600);
		this.setLayout(new GridLayout(2, 2));
		
		searchButton = new JButton("Needs to be initalized");
		clearButton = new JButton("Needs to be initalized");
		myBookings = new JButton("Needs to be initalized");
		bookFlight = new JButton("Needs to be initalized");
		clearFlight = new JButton("Needs to be initalized");
		printTicket = new JButton("Needs to be initalized");
		cancelTicket = new JButton("Needs to be initalized");
		returnToMain = new JButton("Needs to be initalized");
		browseFlights = new JButton("Needs to be initalized");
		
		flightModel = new DefaultListModel<Flight>();
		ticketModel = new DefaultListModel<Ticket>();
		
		flightList = new JList<Flight>(flightModel);
		ticketList = new JList<Ticket>(ticketModel);

		myBookingsWindow();
		
		this.add(topLeftPanel());
		this.add(topRightPanel());
		this.add(botLeftPanel());
		this.add(botRightPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel topLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		
		//row one
		JPanel rOne = new JPanel();
		myBookings = new JButton("My Bookings");
		myBookings.addActionListener(new ClientListener());
		rOne.add(myBookings);		
		panel.add(rOne);
		
		//row two
		JPanel rTwo = new JPanel();
		browseFlights = new JButton("Browse all flights");
		browseFlights.addActionListener(new ClientListener());
		rTwo.add(browseFlights);		
		panel.add(rTwo);
		
		//row three
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Search Flights by:"));
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		String [] temp1 = {"Date(\"October 5 2017\")", "Source", "Destination"};
		comboBoxSearch = new JComboBox<String>(temp1);
		comboBoxSearch.setEditable(false);
		//comboBox.addActionListener(listen);
		rThree.add(comboBoxSearch);
		panel.add(rThree);
		
		//row four
		JPanel rFour = new JPanel();
		rFour.add(new JLabel("Search:"));
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		searchFlights = new JTextField(20);
		rFour.add(searchFlights);
		panel.add(rFour);
		
		//row five
		JPanel rFive = new JPanel();
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ClientListener());
		rFive.add(searchButton);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClientListener());
		rFive.add(clearButton);
		panel.add(rFive);

		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		return panel;
	}

	

	private JPanel topRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		
		//row one
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Departs From:"));
		rTwo.add(Box.createRigidArea(new Dimension(5, 10)));
		departureField = new JTextField(20);
		departureField.setEditable(false);
		departureField.setBackground(Color.white);
		rTwo.add(departureField);
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rTwo);
		
		//row two
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Destination:"));
		rThree.add(Box.createRigidArea(new Dimension(5, 10)));
		destField = new JTextField(20);
		destField.setEditable(false);
		destField.setBackground(Color.white);
		rThree.add(destField);
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rThree);
		
		//row three
		JPanel rFour = new JPanel();
		rFour.add(new JLabel("Departure Date:"));
		rFour.add(Box.createRigidArea(new Dimension(5, 10)));
		depDate = new JTextField(20);
		depDate.setEditable(false);
		depDate.setBackground(Color.white);
		rFour.add(depDate);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rFour);
		
		//row four
		JPanel rFive = new JPanel();
		rFive.add(new JLabel("Departure Time:"));
		rFive.add(Box.createRigidArea(new Dimension(5, 10)));
		depTime = new JTextField(20);
		depTime.setEditable(false);		
		depTime.setBackground(Color.white);
		rFive.add(depTime);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rFive);
		
		//row five
		JPanel rOne = new JPanel();
		rOne.add(new JLabel("Flight Duration:"));
		rOne.add(Box.createRigidArea(new Dimension(5, 10)));
		durationField = new JTextField(20);
		durationField.setEditable(false);
		durationField.setBackground(Color.white);
		rOne.add(durationField);
		rOne.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rOne);
		
		return panel;
	}


	private JPanel botRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		
		//row two
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Total Seats:"));
		rTwo.add(Box.createRigidArea(new Dimension(5, 10)));
		totSeatsField = new JTextField(20);
		totSeatsField.setEditable(false);
		totSeatsField.setBackground(Color.white);
		rTwo.add(totSeatsField);
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rTwo);
		
		//row three
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Remaining Seats:"));
		rThree.add(Box.createRigidArea(new Dimension(5, 10)));
		remSeatsField = new JTextField(20);
		remSeatsField.setEditable(false);
		remSeatsField.setBackground(Color.white);
		rThree.add(remSeatsField);
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rThree);

		//row four
		JPanel rFour = new JPanel();
		rFour.add(new JLabel("Price:"));
		rFour.add(Box.createRigidArea(new Dimension(5, 10)));
		priceField = new JTextField(20);
		priceField.setEditable(false);
		priceField.setBackground(Color.white);
		rFour.add(priceField);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rFour);
		
		//row 6
		
		JPanel rSix = new JPanel();
		rSix.add(new JLabel("Seats to Book:"));
		rSix.add(Box.createRigidArea(new Dimension(5, 10)));
		seatsToBook = new JTextField(20);
		seatsToBook.setEditable(true);
		seatsToBook.setBackground(Color.white);
		seatsToBook.setText("");
		seatsToBook.setToolTipText("You can only book one seat at the moment");
		rSix.add(seatsToBook);
		rSix.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rSix);
		
		//row five
		JPanel rFive = new JPanel();
		bookFlight = new JButton("Book");
		bookFlight.addActionListener(new ClientListener());
		rFive.add(bookFlight);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		clearFlight = new JButton("Clear");
		clearFlight.addActionListener(new ClientListener());
		rFive.add(clearFlight);
		panel.add(rFive);
		
		
		
		return panel;
	}


	private JPanel botLeftPanel() {
		JPanel panel = new JPanel();
		
		flightModel = new DefaultListModel<Flight>();
		flightList = new JList<Flight>(flightModel);
		flightList.addListSelectionListener(new ListAction());
		flightList.setFont(new Font("Courier New", Font.BOLD, 12));
		//flightList.addListSelectionListener(new ListAction());
		flightList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		flightList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		flightList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(flightList);
		textAreaScrollPane.setPreferredSize(new Dimension(400, 260));
		panel.add(textAreaScrollPane);

		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		return panel;
	}

	private class ClientListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bookFlight) {
				try {
					System.out.println(selectedFlight.toString());
					if(departureField.getText().equals(""))
						JOptionPane.showMessageDialog(dialogFrame, "No Flight selected.");
					else if (seatsToBook.getText().equals("")) 
						JOptionPane.showMessageDialog(dialogFrame, "Error: Seats to book not filled");
					else
					{
						try {
							ClientRequestCom crc = new ClientRequestCom(ComTypes.BOOK_FLIGHT);
							crc.setFlight(selectedFlight);
							crc.setUser(user);
							crc.setSeats(Integer.parseInt(seatsToBook.getText()));
							objectOut.writeObject(crc);
							objectOut.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (Exception e1){
							JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
						}
					}
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == clearFlight) 
			{
				departureField.setText("");
				destField.setText("");
				depDate.setText("");
				depTime.setText("");
				durationField.setText("");
				totSeatsField.setText("");
				remSeatsField.setText("");
				priceField.setText("");
			}
			else if (e.getSource() == myBookings)
			{
				myBookingsWindow.setVisible(true);
				if (!ticketModel.isEmpty()) ticketModel.removeAllElements();
				try {
					ClientRequestCom crc = new ClientRequestCom(ComTypes.QUERY);
					crc.setUser(user);
					crc.setQuery(ClientRequestCom.TICKET);
					objectOut.writeObject(crc);
					objectOut.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1){
					e1.printStackTrace();
				}
			}
			else if (e.getSource() == searchButton){
				flightModel.clear();
				if (searchFlights.getText().equals("")) JOptionPane.showMessageDialog(null, "Error: Empty search field");
				if(comboBoxSearch.getSelectedItem().equals("Date(\"October 5 2017\")"))
				{
					try {
						ClientRequestCom crc = new ClientRequestCom(ComTypes.QUERY);
						crc.setUser(user);
						crc.setQuery(ClientRequestCom.FLIGHT_BY_DATE);
						crc.setDate(new Date(searchFlights.getText()));
						objectOut.writeObject(crc);
						objectOut.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(comboBoxSearch.getSelectedItem().equals("Source"))
				{
					try {
						ClientRequestCom crc = new ClientRequestCom(ComTypes.QUERY);
						crc.setUser(user);
						crc.setSearch(searchFlights.getText());
						crc.setQuery(ClientRequestCom.FLIGHT_BY_SOURCE);
						objectOut.writeObject(crc);
						objectOut.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (Exception e1){
						e1.printStackTrace();
					}
				}
				else if(comboBoxSearch.getSelectedItem().equals("Destination"))
				{
					try {
						ClientRequestCom crc = new ClientRequestCom(ComTypes.QUERY);
						crc.setUser(user);
						crc.setSearch(searchFlights.getText());
						crc.setQuery(ClientRequestCom.FLIGHT_BY_DESTINATION);
						objectOut.writeObject(crc);
						objectOut.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (Exception e1){
						e1.printStackTrace();
					}
				}
			}
			else if (e.getSource() == clearButton){
				searchFlights.setText("");
				flightModel.clear();
			}
			else if (e.getSource() == printTicket){
				String t = "ticket_";
				t += Integer.toString(ticketList.getSelectedValue().getId());
				try{
				    PrintWriter writer = new PrintWriter(t, "UTF-8");
				    writer.println(ticketList.getSelectedValue().toString());
				    writer.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
			else if (e.getSource() == cancelTicket){
				try {
					ClientRequestCom req = new ClientRequestCom(ComTypes.CANCEL_TICKET);
					req.setTicket(ticketList.getSelectedValue());
					req.setUser(user);
					objectOut.writeObject(req);						
					objectOut.flush();
					ticketModel.removeElement(ticketList.getSelectedValue());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (e.getSource() == returnToMain){
				myBookingsWindow.setVisible(false);
			}
			else if (e.getSource() == browseFlights){
				if (!flightModel.isEmpty()) flightModel.removeAllElements();
				try {
					ClientRequestCom req = new ClientRequestCom(ComTypes.QUERY);
					req.setQuery(ClientRequestCom.ALL_FLIGHTS);
					objectOut.writeObject(req);						
					objectOut.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			
			
		}
	}
	
	private void myBookingsWindow() {
		myBookingsWindow = new JFrame();
		myBookingsWindow.setTitle(user.getUsername() + "'s tickets");
		myBookingsWindow.setBounds(325, 225, 600, 400);
		
		//top
		JPanel top = new JPanel();
		top.add(new JLabel("My tickets:"));
		myBookingsWindow.add(top, BorderLayout.NORTH);

		//center
		JPanel center = new JPanel();
		ticketModel = new DefaultListModel<Ticket>();
		ticketList = new JList<Ticket>(ticketModel);
		ticketList.setFont(new Font("Courier New", Font.BOLD, 12));
		//flightList.addListSelectionListener(new ListAction());
		ticketList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ticketList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		ticketList.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		myTextArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		myTextArea.setFont(new Font("Courier New", Font.BOLD, 12));
		myTextArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(ticketList);
		textAreaScrollPane.setPreferredSize(new Dimension(550, 260));
		center.add(textAreaScrollPane);
		myBookingsWindow.add(center, BorderLayout.CENTER);

		//bot
		JPanel bot = new JPanel();
		printTicket = new JButton("Print Ticket");
		printTicket.addActionListener(new ClientListener());
		bot.add(printTicket);
		bot.add(Box.createRigidArea(new Dimension(5 ,10)));
		cancelTicket = new JButton("Cancel Ticket");
		cancelTicket.addActionListener(new ClientListener());
		bot.add(cancelTicket);
		bot.add(Box.createRigidArea(new Dimension(5 ,10)));
		returnToMain = new JButton("Return to main");
		returnToMain.addActionListener(new ClientListener());
		bot.add(returnToMain);
		myBookingsWindow.add(bot, BorderLayout.SOUTH);
		
	}

	private class ListAction implements ListSelectionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Object o = ((JList<Flight>)e.getSource()).getSelectedValue();
			if (o instanceof Flight){
				Flight f = (Flight) o;
				departureField.setText(f.getSource());
				destField.setText(f.getDestination());
				durationField.setText(f.getDuration().toString());
				depTime.setText(f.getDepartureTime().toString());
				depDate.setText(f.getDate().toString());
				totSeatsField.setText(Integer.toString(f.getTotalSeats()));
				remSeatsField.setText(Integer.toString(f.getavailableSeats()));
				String temp = "$";
				DecimalFormat p = new DecimalFormat("###00.00");
				temp += p.format(f.getPrice());
				priceField.setText(temp);
				selectedFlight = f;
			}
		}

		
	}

	public DefaultListModel<Ticket> getTicketList() {
		return ticketModel;
	}

	public void setTicketModel(DefaultListModel<Ticket> ticketModel) {
		this.ticketModel = ticketModel;
	}

	public DefaultListModel<Flight> getFlightList() {
		return flightModel;
	}

	public void setFlightModel(DefaultListModel<Flight> flightModel) {
		this.flightModel = flightModel;
	}

}
