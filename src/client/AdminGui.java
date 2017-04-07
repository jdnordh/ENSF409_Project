package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class AdminGui extends JFrame{
	
	/** Frames */
	static AdminGui a;
	private JFrame browseWindow;
	private JFrame addFlightWindow;
	private JFrame addFlightFileWindow;



	/**buttons */
	private JButton browse;
	private JButton addFlight;
	private JButton addFlightFile;
	private JButton cancelBooking;
	private JButton addFlightButton;
	private JButton mainMenu;
	private JButton addFlightFileButton;

	
	/** This is the list of clients */
	protected JList<Client> display;
	private DefaultListModel<Client> list;
	
	protected JTextArea textArea;
	
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
	private JTextField fileName;
	
	public AdminGui(Object o, String user){
		a = new AdminGui(user);
	}
	
	public AdminGui(String user) {
		
		this.setTitle(user + ": Administration Mode");
		this.setBounds(325, 225, 300, 160);
		this.setLayout(new GridLayout(3, 1));
		
		this.add(top());
		this.add(center());
		this.add(bottom());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public AdminGui(boolean b) {
		return;
	}

	private JPanel top() {
		JPanel panel = new JPanel();
		browse = new JButton("Browse Bookings");
		browse.addActionListener(new ClientListener());
		panel.add(browse);
		
		return panel;
	}

	private JPanel center() {
		JPanel panel = new JPanel();
		
		addFlight = new JButton("Add Individual Flight");
		addFlight.addActionListener(new ClientListener());
		panel.add(addFlight);
		
		return panel;
	}

	private JPanel bottom() {
		JPanel panel = new JPanel();
		
		addFlightFile = new JButton("Add Flights From File");
		addFlightFile.addActionListener(new ClientListener());
		panel.add(addFlightFile);
		
		return panel;
	}
	
	public void browseWindow(){
		browseWindow = new JFrame();
		browseWindow.setTitle("Browse Bookings");
		browseWindow.setBounds(325, 225, 400, 400);

		browseWindow.add(new JLabel("Bookings:"), BorderLayout.NORTH);
		
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
		textAreaScrollPane.setPreferredSize(new Dimension(350, 300));
		browseWindow.add(textAreaScrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		cancelBooking = new JButton("Cancel Selected Booking");
		cancelBooking.addActionListener(new ClientListener());
		mainMenu = new JButton("Return to Main Menu");
		mainMenu.addActionListener(new ClientListener());
		panel.add(cancelBooking);
		panel.add(mainMenu);
		browseWindow.add(panel, BorderLayout.SOUTH);

		browseWindow.setVisible(true);
		browseWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addFlightWindow(){
		addFlightWindow = new JFrame();
		addFlightWindow.setTitle("Add Flight");
		addFlightWindow.setBounds(325, 225, 400, 400);
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
		addFlightButton.addActionListener(new ClientListener());
		rNine.add(addFlightButton);
		rNine.add(Box.createRigidArea(new Dimension(15, 10)));
		mainMenu = new JButton("Return to Main Menu");
		mainMenu.addActionListener(new ClientListener());
		rNine.add(mainMenu);
		addFlightWindow.add(rNine);
		
		
		addFlightWindow.setVisible(true);
		addFlightWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addFlightFileWindow(){
		addFlightFileWindow = new JFrame();
		addFlightFileWindow.setTitle("Add Flight File");
		addFlightFileWindow.setBounds(325, 225, 350, 100);
		
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
		mainMenu = new JButton("Return to Main Menu");
		mainMenu.addActionListener(new ClientListener());
		bottom.add(mainMenu);
		addFlightFileWindow.add(bottom, BorderLayout.SOUTH);
		
		addFlightFileWindow.setVisible(true);
		addFlightFileWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	private class ClientListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == browse) {
				a.dispose();
				browseWindow();
				
			}
			
			else if (e.getSource() == addFlight) {
				a.dispose();
				addFlightWindow();
			}
			
			else if (e.getSource() == addFlightFile) {
				a.dispose();
				addFlightFileWindow();
			}
			
			else if (e.getSource() == mainMenu) {
				if(browseWindow != null)
				{
					browseWindow.dispose();
					a = new AdminGui("user");
					//a = new AdminGui();
					//TODO fix these commented lines
				}
				else if(addFlightWindow != null)
				{
					addFlightWindow.dispose();
					a = new AdminGui("user");
					//a = new AdminGui();
				}
				else if(addFlightFileWindow != null)
				{
					addFlightFileWindow.dispose();
					a = new AdminGui("user");
					//a = new AdminGui();
				}
				
			}
		}
	}

	public static void main(String[] args) {
		a = new AdminGui("user");
	}

}
