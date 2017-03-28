package data.transfer;

import javax.swing.JOptionPane;

public class ClientRequestCom extends ServerCom{

	public ClientRequestCom(int t) {
		super(t);
		if (type > 10){
			JOptionPane.showMessageDialog(null, "Bad client request");
		}
	}

}
