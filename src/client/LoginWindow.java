package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;
import data.transfer.Date;
import data.transfer.ServerOutputCom;
import data.transfer.User;


public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private ObjectOutputStream objectOut;
	
	/** Frames */
	protected static User user;
	private JFrame loginWindow;
	private JFrame newUserWindow;
	private JOptionPane dialogFrame;

	/** Combo box */
	protected JComboBox<String> comboBoxDay;
	protected char comboBoxSelectionDay;
	protected JComboBox<String> comboBoxMonth;
	protected char comboBoxSelectionMonth;
	protected JComboBox<String> comboBoxYear;
	protected char comboBoxSelectionYear;
	
	/** Buttons */
	private JButton chooseLogin = new JButton("Login with Username");
	private JButton newUser = new JButton("Create new login");
	private JButton login;
	private JButton cancelLogin;
	private JButton createAccount;
	private JButton cancelCreation;

	/** editable text fields*/
	private JTextField username;
	private JTextField newUserFN;
	private JTextField newUserLN;
	private JTextField newUserUN;
	
	/**password fields*/
	private JPasswordField password;
	private JPasswordField newUserPassword;
	private JPasswordField confirmUserPassword;

	public AdminGui head;

	public LoginWindow(ObjectOutputStream obOut){
		objectOut = obOut;
		this.setTitle("Login");
		this.setLayout(new GridLayout(2, 1));
		this.setBounds(325, 225, 300, 115);
		
		this.add(createUpperPanel());
		this.add(createCenterPanel());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel createUpperPanel() {
		JPanel upperPanel = new JPanel();
		chooseLogin.addActionListener(new ClientListener(this));
		upperPanel.add(chooseLogin);
		return upperPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel();
		newUser.addActionListener(new ClientListener(this));
		centerPanel.add(newUser);
		return centerPanel;
	}
	
	private void loginWindow()
	{
		loginWindow = new JFrame();
		loginWindow.setLayout(new GridLayout(3, 1));
		loginWindow.setTitle("Login");
		loginWindow.setBounds(325, 225, 500, 150);
		
		//north
		JPanel upperPanel = new JPanel();
		JLabel label1 = new JLabel("Username:");
		username = new JTextField(30);
		upperPanel.add(label1);
		upperPanel.add(username);
		loginWindow.add(upperPanel);
		
		//center
		JPanel centerPanel = new JPanel();
		JLabel label2 = new JLabel("Password:");
		password = new JPasswordField(30);
		centerPanel.add(label2);
		centerPanel.add(password);
		loginWindow.add(centerPanel);
		
		//south
		JPanel lowerPanel = new JPanel();
		login = new JButton("Login");
		login.addActionListener(new ClientListener(this));
		lowerPanel.add(login);
		cancelLogin = new JButton("Cancel");
		cancelLogin.addActionListener(new ClientListener(this));
		lowerPanel.add(cancelLogin);
		loginWindow.add(lowerPanel);

		loginWindow.setVisible(true);
		loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void newUserWindow() {
		newUserWindow = new JFrame();
		newUserWindow.setLayout(new GridLayout(7, 1));
		newUserWindow.setTitle("New User");
		newUserWindow.setBounds(325, 225, 450, 400);
		
		//row one
		JPanel rOne = new JPanel();
		newUserFN = new JTextField(30);
		rOne.add(new JLabel("First Name:"));
		rOne.add(newUserFN);
		//one.add(Box.createRigidArea(new Dimension(450 ,10)));
		newUserWindow.add(rOne);

		//row two
		JPanel rTwo = new JPanel();
		newUserLN = new JTextField(30);
		rTwo.add(new JLabel("Last Name:"));
		rTwo.add(newUserLN);
		//rTwo.add(Box.createRigidArea(new Dimension(450 ,10)));
		newUserWindow.add(rTwo);

		//row three
		JPanel rThree = new JPanel();
		newUserUN = new JTextField(30);
		rThree.add(new JLabel("Username:"));
		rThree.add(newUserUN);
		//rThree.add(Box.createRigidArea(new Dimension(450 ,10)));
		newUserWindow.add(rThree);

		//row four
		JPanel rFour = new JPanel();
		newUserPassword = new JPasswordField(30);
		rFour.add(new JLabel("Password:"));
		rFour.add(newUserPassword);
		rFour.add(Box.createRigidArea(new Dimension(450 ,10)));
		newUserWindow.add(rFour);
		
		//row five
		JPanel rFive = new JPanel();
		confirmUserPassword = new JPasswordField(25);
		rFive.add(new JLabel("Confirm Password:"));
		rFive.add(confirmUserPassword);
		rFive.add(Box.createRigidArea(new Dimension(450 ,10)));
		newUserWindow.add(rFive);
		
		//row six
		JPanel rSix = new JPanel();
		rSix.add(new JLabel("Date of Birth:"));
		rSix.add(Box.createRigidArea(new Dimension(10 ,10)));
		rSix.add(new JLabel("Day:"));
		String [] temp1 = new String[31];
		for(int i = 0, j = 1; i < temp1.length; i++, j++)
			temp1[i] = Integer.toString(j);
		comboBoxDay = new JComboBox<String>(temp1);
		comboBoxDay.setEditable(false);
		//comboBox.addActionListener(listen);
		rSix.add(comboBoxDay);
		rSix.add(Box.createRigidArea(new Dimension(10 ,10)));

		rSix.add(new JLabel("Month:"));
		String [] temp2 = new String[12];
		for(int i = 0, j = 1; i < temp2.length; i++, j++)
			temp2[i] = Integer.toString(j);
		comboBoxMonth = new JComboBox<String>(temp2);
		comboBoxMonth.setEditable(false);
		//comboBox.addActionListener(listen);
		rSix.add(comboBoxMonth);
		rSix.add(Box.createRigidArea(new Dimension(10 ,10)));

		rSix.add(new JLabel("Year:"));			
		String [] temp3 = new String[83];
		for(int i = 0, j = 1935; i < temp3.length; i++, j++)
			temp3[i] = Integer.toString(j);
		comboBoxYear = new JComboBox<String>(temp3);
		comboBoxYear.setEditable(false);
		//comboBox.addActionListener(listen);
		rSix.add(comboBoxYear);
		rSix.add(Box.createRigidArea(new Dimension(10 ,10)));
		newUserWindow.add(rSix);

		//row seven
		JPanel rSeven = new JPanel();
		createAccount = new JButton("Create Account");
		createAccount.addActionListener(new ClientListener(this));
		rSeven.add(createAccount);
		cancelCreation = new JButton("Cancel");
		cancelCreation.addActionListener(new ClientListener(this));
		rSeven.add(cancelCreation);
		newUserWindow.add(rSeven);

		newUserWindow.setVisible(true);
		newUserWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	private class ClientListener implements ActionListener
	{
		private LoginWindow pointer;
		
		public ClientListener(LoginWindow w){
			super();
			pointer = w;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == chooseLogin) {
				pointer.setVisible(false);
				loginWindow();
				System.out.println("Displaying login window...");
			}
			
			else if (e.getSource() == newUser) {
				pointer.setVisible(false);
				newUserWindow();
				System.out.println("Displaying create a new user window...");
			}
			
			else if(e.getSource() == login) {
				String pass = new String(password.getPassword());
				
				try {
					ClientRequestCom crc = new ClientRequestCom(ComTypes.LOG_IN);
					User u = new User();
					u.setUsername(username.getText());
					u.setPassword(pass);
					crc.setUser(u);
					objectOut.writeObject(crc);
					objectOut.flush();
					

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			
			else if(e.getSource() == cancelLogin) {
				loginWindow.dispose();
				pointer.setVisible(true);
			}
			
			else if(e.getSource() == createAccount) {
				System.out.println("Attempting to creating a new account");
				
				String pass = new String(newUserPassword.getPassword());
				String confirmPass = new String(confirmUserPassword.getPassword());

				/**error checking of new user strings begins*/
				if(newUserFN.getText().equals("") || newUserLN.getText().equals("") ||
						newUserUN.getText().equals("") || pass.equals("") || 
						confirmPass.equals(""))
				{
					JOptionPane.showMessageDialog(dialogFrame, "One or More of the Required Fields are Blank.");
				}
				else if(!pass.equals(confirmPass))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Password fields are not the same.");
					newUserPassword.setText("");
					confirmUserPassword.setText("");
				}
				else if(comboBoxMonth.getSelectedItem().toString().equals("2")&& 
						(comboBoxDay.getSelectedItem().toString().equals("29") ||
						 comboBoxDay.getSelectedItem().toString().equals("30")  ||
						 comboBoxDay.getSelectedItem().toString().equals("31")))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
				}
				else if(comboBoxMonth.getSelectedItem().toString().equals("4")&& 
						comboBoxDay.getSelectedItem().toString().equals("31"))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
				}
				else if(comboBoxMonth.getSelectedItem().toString().equals("6")&& 
						comboBoxDay.getSelectedItem().toString().equals("31"))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
				}
				else if(comboBoxMonth.getSelectedItem().toString().equals("9")&& 
						comboBoxDay.getSelectedItem().toString().equals("31"))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
				}
				else if(comboBoxMonth.getSelectedItem().toString().equals("11")&& 
						comboBoxDay.getSelectedItem().toString().equals("31"))
				{
					JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
				}
				/**error checking of new user strings ends*/
				else	//passed all error checking
				{
					
					
					try {
						ClientRequestCom crc = new ClientRequestCom(ComTypes.REGISTER_USER);
						crc.setUser(new User(newUserFN.getText(), newUserLN.getText(), newUserUN.getText(),
									pass, new Date(Integer.parseInt(comboBoxDay.getSelectedItem().toString()),
													Integer.parseInt(comboBoxMonth.getSelectedItem().toString()),
													Integer.parseInt(comboBoxYear.getSelectedItem().toString()))));
						user = crc.getUser();
						objectOut.writeObject(crc);						
						objectOut.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e1){
						JOptionPane.showMessageDialog(dialogFrame, "Invalid Date entered.");
					}
					
					
					//TODO Jordan, do you have a fix????
					/*
					Trying to make it so that if the register is confirm the newUserWindow is disposed
					and it returns to the main menu where they can then log in. If the username is already
					taken it should leave all their fields in tact and leave the newUserWindow visible.
					*/
					/*try {
						ServerOutputCom response = (ServerOutputCom) objectIn.readObject();
						if (response.type() == ComTypes.REGISTER_CONFIRM){
							System.out.println("Account created...");
							newUserWindow.dispose();
							gui = new LoginWindow(objectOut, objectIn);
						}
					} catch (ClassNotFoundException e1) {
						System.out.println("123");
						e1.printStackTrace();
					} catch (IOException e1) {
						System.out.println("456");
						e1.printStackTrace();
					}*/
				
				}
			}
			
			else if(e.getSource() == cancelCreation) 
			{
				newUserWindow.dispose();
				pointer.setVisible(true);
			}
		}
	}
	
	/**
	 * Closes all associated windows
	 */
	public void close(){
		if (newUserWindow != null) newUserWindow.dispose();
		if (loginWindow != null) loginWindow.dispose();
		this.dispose();
	}
	
	public ObjectOutputStream getOut() {
		return objectOut;
	}

	public void setOut(ObjectOutputStream out) {
		this.objectOut = out;
	}
	public User getUser(){
		return user;
	}
	
	public static void main(String [] args){
		LoginWindow win = new LoginWindow(null);
	}
	
}