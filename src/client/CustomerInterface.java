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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Flight;
import data.transfer.ServerOutputCom;
import data.transfer.User;



public class CustomerInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private User user;

	/** all frames*/
	private JOptionPane dialogFrame;
	private JFrame myBookingsWindow;
	
	private static ObjectOutputStream objectOut;
		
	/** Combo box */
	protected JComboBox<String> comboBoxSearch;
	protected char comboBoxSelectionDay1;
	
	/** editable text fields*/
	private JTextField searchFlights;
	private JTextField flightNumField;
	private JTextField departureField;
	private JTextField destField;
	private JTextField depDate;
	private JTextField depTime;
	private JTextField durationField;
	private JTextField totSeatsField;
	private JTextField remSeatsField;
	private JTextField priceField;

	
	/**buttons */
	private JButton searchButton;
	private JButton clearButton;
	private JButton myBookings;
	private JButton bookFlight;
	private JButton clearFlight;
	private JButton printTicket;
	private JButton cancelTicket;
	private JButton returnToMain;

	/** This is the list of clients */
	protected JList<Flight> display;
	private DefaultListModel<Flight> list;
	protected JList<Flight> myDisplay;
	private DefaultListModel<Flight> myList;
	
	/** This is the list of textAreas*/
	protected JTextArea textArea;
	protected JTextArea myTextArea;
	
	/**
	 * Construct a gui for a given username
	 * @param title User's name
	 */
	public CustomerInterface(String title, ObjectOutputStream o) {
		objectOut = o;
		this.setTitle("Welcome " + title);
		this.setBounds(325, 225, 725, 600);
		this.setLayout(new GridLayout(2, 2));
		
		this.add(topLeftPanel());
		this.add(topRightPanel());
		this.add(botLeftPanel());
		this.add(botRightPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel topLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		
		//row one
		JPanel rOne = new JPanel();
		myBookings = new JButton("My Bookings");
		myBookings.addActionListener(new ClientListener());
		rOne.add(myBookings);		
		panel.add(rOne);
		
		//row two
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Search Flights by:"));
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		String [] temp1 = {"Date(\"October 5 2017\")", "Source", "Destination"};
		comboBoxSearch = new JComboBox<String>(temp1);
		comboBoxSearch.setEditable(false);
		//comboBox.addActionListener(listen);
		rTwo.add(comboBoxSearch);
		panel.add(rTwo);
		
		//row two
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Search:"));
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		searchFlights = new JTextField(20);
		rThree.add(searchFlights);
		panel.add(rThree);
		
		//row three
		JPanel rFour = new JPanel();
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ClientListener());
		rFour.add(searchButton);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClientListener());
		rFour.add(clearButton);
		panel.add(rFour);

		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		return panel;
	}

	

	private JPanel topRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		
		//row one
		JPanel rOne = new JPanel();
		rOne.add(new JLabel("Flight Number:"));
		rOne.add(Box.createRigidArea(new Dimension(5, 10)));
		flightNumField = new JTextField(20);
		flightNumField.setEditable(false);
		flightNumField.setBackground(Color.white);
		rOne.add(flightNumField);
		rOne.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rOne);
		
		//row two
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Departs From:"));
		rTwo.add(Box.createRigidArea(new Dimension(5, 10)));
		departureField = new JTextField(20);
		departureField.setEditable(false);
		departureField.setBackground(Color.white);
		rTwo.add(departureField);
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rTwo);
		
		//row three
		JPanel rThree = new JPanel();
		rThree.add(new JLabel("Destination:"));
		rThree.add(Box.createRigidArea(new Dimension(5, 10)));
		destField = new JTextField(20);
		destField.setEditable(false);
		destField.setBackground(Color.white);
		rThree.add(destField);
		rThree.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rThree);
		
		//row four
		JPanel rFour = new JPanel();
		rFour.add(new JLabel("Departure Date:"));
		rFour.add(Box.createRigidArea(new Dimension(5, 10)));
		depDate = new JTextField(20);
		depDate.setEditable(false);
		depDate.setBackground(Color.white);
		rFour.add(depDate);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rFour);
		
		//row five
		JPanel rFive = new JPanel();
		rFive.add(new JLabel("Departure Time:"));
		rFive.add(Box.createRigidArea(new Dimension(5, 10)));
		depTime = new JTextField(20);
		depTime.setEditable(false);		
		depTime.setBackground(Color.white);
		rFive.add(depTime);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rFive);
		
		return panel;
	}


	private JPanel botRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		
		//row one
		JPanel rOne = new JPanel();
		rOne.add(new JLabel("Flight Duration:"));
		rOne.add(Box.createRigidArea(new Dimension(5, 10)));
		durationField = new JTextField(20);
		durationField.setEditable(false);
		durationField.setBackground(Color.white);
		rOne.add(durationField);
		rOne.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(rOne);
		
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
		
		list = new DefaultListModel<Flight>();
		display = new JList<Flight>(list);
		display.setFont(new Font("Courier New", Font.BOLD, 12));
		//display.addListSelectionListener(new ListAction());
		display.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		display.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		display.setVisibleRowCount(-1);
		JScrollPane textAreaScrollPane = new JScrollPane();
		textArea = new JTextArea();
		textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textArea.setFont(new Font("Courier New", Font.BOLD, 12));
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(display);
		textAreaScrollPane.setPreferredSize(new Dimension(325, 260));
		panel.add(textAreaScrollPane);

		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		return panel;
	}

	private class ClientListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bookFlight) {
				if(flightNumField.getText().equals(""))
					JOptionPane.showMessageDialog(dialogFrame, "No Flight selected.");
				else
				{
					try {
						ClientRequestCom crc = new ClientRequestCom(ComTypes.BOOK_FLIGHT);
						crc.setFlight(new Flight(flightNumField.getText() + "," + departureField.getText() + "," + 
												 destField.getText() + "," + depDate.getText() + "," + 
												 depTime.getText() + ","  + durationField.getText() + "," +
												 totSeatsField.getText() + "," + remSeatsField.getText() + "," +
												 priceField.getText()));
						//TODO user should be field within each CustomerInterface window
						crc.setUser(user);
						crc.setSeats(Integer.parseInt(remSeatsField.getText()));
						objectOut.writeObject(crc);
						objectOut.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if (e.getSource() == clearFlight) 
			{
				flightNumField.setText("");;
				departureField.setText("");;
				destField.setText("");
				depDate.setText("");;
				depTime.setText("");;
				durationField.setText("");;
				totSeatsField.setText("");;
				remSeatsField.setText("");;
				priceField.setText("");;
			}
			else if (e.getSource() == myBookings)
			{
				//TODO cust == null here but idk why
				//cust.dispose();
				myBookingsWindow();
			}
			else if (e.getSource() == searchButton){
				if(comboBoxSearch.getSelectedItem().equals("Date(\"October 5 2017\")"))
				{
					//TODO search database and display flights on searched day
				}
				else if(comboBoxSearch.getSelectedItem().equals("Source"))
				{
					//TODO search database and display flights from source
				}
				else if(comboBoxSearch.getSelectedItem().equals("Destination"))
				{
					//TODO search database and display flights to dest
				}
			}
			else if (e.getSource() == clearButton){
				searchFlights.setText("");
			}
			
			//TODO program buttons for myBookings window
			
		}

		private void myBookingsWindow() {
			myBookingsWindow = new JFrame();
			myBookingsWindow.setTitle("New User");
			myBookingsWindow.setBounds(325, 225, 450, 400);
			
			//top
			JPanel top = new JPanel();
			top.add(new JLabel("My tickets:"));
			myBookingsWindow.add(top, BorderLayout.NORTH);

			//center
			JPanel center = new JPanel();
			myList = new DefaultListModel<Flight>();
			myDisplay = new JList<Flight>(myList);
			myDisplay.setFont(new Font("Courier New", Font.BOLD, 12));
			//display.addListSelectionListener(new ListAction());
			myDisplay.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			myDisplay.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			myDisplay.setVisibleRowCount(-1);
			JScrollPane textAreaScrollPane = new JScrollPane();
			myTextArea = new JTextArea();
			textAreaScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			myTextArea.setFont(new Font("Courier New", Font.BOLD, 12));
			myTextArea.setEditable(false);
			textAreaScrollPane = new JScrollPane(myDisplay);
			textAreaScrollPane.setPreferredSize(new Dimension(325, 260));
			center.add(textAreaScrollPane);
			myBookingsWindow.add(center, BorderLayout.CENTER);
			//TODO fill myticketlist with that users tickets 

			//bot
			JPanel bot = new JPanel();
			printTicket = new JButton("Print Ticket");
			bot.add(printTicket);
			bot.add(Box.createRigidArea(new Dimension(5 ,10)));
			cancelTicket = new JButton("Cancel Ticket");
			bot.add(cancelTicket);
			bot.add(Box.createRigidArea(new Dimension(5 ,10)));
			returnToMain = new JButton("Return to main");
			bot.add(returnToMain);
			myBookingsWindow.add(bot, BorderLayout.SOUTH);
			
			myBookingsWindow.setVisible(true);
			myBookingsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	}


	private class ListAction implements ListSelectionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			//client = ((JList<Client>)e.getSource()).getSelectedValue();
			//client.displayInfo(clientIDIn, firstNameIn, lastNameIn, addressIn, postalIn, phoneNumIn, comboBox);
			//dThread.setClient(client);
		}

		
	}

	public static void main(String[] args) {
		CustomerInterface cust = new CustomerInterface("Test", null);
	}

}