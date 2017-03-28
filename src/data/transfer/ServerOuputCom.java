package data.transfer;

import javax.swing.JOptionPane;

public class ServerOuputCom extends ServerCom{

	public ServerOuputCom(int t) {
		super(t);
		if (type < 10){
			JOptionPane.showMessageDialog(null, "Bad server push");
		}
	}

}
