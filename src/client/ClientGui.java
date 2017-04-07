package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.*;

import data.transfer.ClientRequestCom;
import data.transfer.ComTypes;

//TODO create ClientRequestCom
//TODO fill all required fields
//TODO put into ObjectOutputStream
//TODO flush stream

public class ClientGui extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private ObjectOutputStream out;

	/** Frames */
	static ClientGui g;
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

	
	public ClientGui(){
		this.setTitle("Setup");
		this.setLayout(new GridLayout(2, 1));
		this.setBounds(325, 225, 300, 115);
		
		this.add(createUpperPanel());
		this.add(createCenterPanel());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel createUpperPanel() {
		JPanel upperPanel = new JPanel();
		chooseLogin.addActionListener(new ClientListener());
		upperPanel.add(chooseLogin);
		return upperPanel;
	}
	
	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel();
		newUser.addActionListener(new ClientListener());
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
		login.addActionListener(new ClientListener());
		lowerPanel.add(login);
		cancelLogin = new JButton("Cancel");
		cancelLogin.addActionListener(new ClientListener());
		lowerPanel.add(cancelLogin);
		loginWindow.add(lowerPanel);

		loginWindow.setVisible(true);
	}

	private void newUserWindow() {
		newUserWindow = new JFrame();
		newUserWindow.setLayout(new GridLayout(7, 1));
		newUserWindow.setTitle("New User");
		newUserWindow.setBounds(325, 225, 450, 400);
		newUserWindow.setVisible(true);
		
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
		createAccount.addActionListener(new ClientListener());
		rSeven.add(createAccount);
		cancelCreation = new JButton("Cancel");
		cancelCreation.addActionListener(new ClientListener());
		rSeven.add(cancelCreation);
		newUserWindow.add(rSeven);

		}
		
	private class ClientListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == chooseLogin) {
				g.dispose();
				loginWindow();
			}
			
			else if (e.getSource() == newUser) {
				g.dispose();
				newUserWindow();
			}
			
			else if(e.getSource() == login) {
				// TODO create client request so server can handle logins
				
				String pass = new String(password.getPassword());  //Use this string for password
				
				/*if(username.getText().equals("Admin1") || username.getText().equals("Admin2"))
				{
					if(pass.equals("pass"))
					{
						loginWindow.dispose();
						new AdminGui(null);
					}
					else
					{
						JOptionPane.showMessageDialog(dialogFrame, "Username/Password Incorrect.");
						password.setText("");
					}
				}
				else
				{
					new CustomerGui();
				}
				*/
								
			}
			
			else if(e.getSource() == cancelLogin) {
				loginWindow.dispose();
				g = new ClientGui();
			}
			
			else if(e.getSource() == createAccount) {
				//TODO fix
				System.out.println("Creating new account");
				String pass = new String(newUserPassword.getPassword());  //Use this string for password
				String confirmPass = new String(confirmUserPassword.getPassword());  //Use this string for password

			
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
				else
				{
					newUserWindow.dispose();
					ClientRequestCom crc = new ClientRequestCom(ComTypes.REGISTER_USER);
					//crc.setUser(newUserFN.getText(), newUserLN.getText(), );
					
					//g = new ClientGui();
				}
			}
			
			else if(e.getSource() == cancelCreation) {
				newUserWindow.dispose();
				g = new ClientGui();
			}
			
		}

		
	}
	
	public static void main(String[] args){
		g = new ClientGui();
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
}
