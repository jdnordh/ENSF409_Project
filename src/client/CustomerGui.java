package client;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;




public class CustomerGui extends JFrame {
	
	private CustomerGui cust;

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

	/** This is the list of clients */
	protected JList<Client> display;
	private DefaultListModel<Client> list;
	
	protected JTextArea textArea;
	
	public CustomerGui(Object o){
		cust = new CustomerGui();
	}
	
	
	public CustomerGui() {
		this.setTitle("Welcome Customer");
		this.setBounds(325, 225, 725, 600);
		this.setLayout(new GridLayout(2, 2));
		
		this.add(topLeftPanel());
		this.add(topRightPanel());
		this.add(botLeftPanel());
		this.add(botRightPanel());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel topLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		
		//row one
		JPanel rOne = new JPanel();
		myBookings = new JButton("My Bookings");
		rOne.add(myBookings);		
		panel.add(rOne);
		
		//row two
		JPanel rTwo = new JPanel();
		rTwo.add(new JLabel("Search Flights by:"));
		rTwo.add(Box.createRigidArea(new Dimension(10, 10)));
		String [] temp1 = {"Date", "Source", "Destination"};
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
		rFour.add(searchButton);
		rFour.add(Box.createRigidArea(new Dimension(10, 10)));
		clearButton = new JButton("Clear");
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
		rFive.add(bookFlight);
		rFive.add(Box.createRigidArea(new Dimension(10, 10)));
		clearFlight = new JButton("Clear");
		rFive.add(clearFlight);
		panel.add(rFive);

		
		return panel;
	}


	private JPanel botLeftPanel() {
		JPanel panel = new JPanel();
		
		list = new DefaultListModel<Client>();
		display = new JList<Client>(list);
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


	/*private class ListAction implements ListSelectionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			//client = ((JList<Client>)e.getSource()).getSelectedValue();
			//client.displayInfo(clientIDIn, firstNameIn, lastNameIn, addressIn, postalIn, phoneNumIn, comboBox);
			//dThread.setClient(client);
		}

		
	}*/

	public static void main(String[] args) {
		CustomerGui g = new CustomerGui();
	}

}
