package client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientGui extends JFrame{
	/** Panels */
	private JPanel left;
	private JPanel right;
	private JPanel display;
	
	/** Buttons */
	private JButton bookSeat;
	private JButton cancelSeat;
	private JButton search;
	private JButton browse;
	private JButton myTickets;
	
	/** editable text fields */
	private JTextField searchText;
	
	/** uneditable text fields */
	private JTextField messages;
	private JTextField departsFrom;
	private JTextField destination;
	private JTextField date;
	private JTextField duration;
	private JTextField row;
	private JTextField isle;
	private JTextField price;
	
	
	
	public ClientGui(String user){
		super(user);
		this.makeLeftSide();
		this.makeRightSide();
		this.setVisible(true);
	}
	
	private void makeLeftSide(){
		left = new JPanel(new GridBagLayout());
		ClientListener event = new ClientListener();
		JPanel top = new JPanel();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
	}
	
	private void makeRightSide(){
		right = new JPanel();
		ClientListener event = new ClientListener();
		
	}
	
	private class ClientListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(){
		ClientGui g = new ClientGui("john68");
	}
}
