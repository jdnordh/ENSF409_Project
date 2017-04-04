package data.transfer;

import java.io.Serializable;

public abstract class ServerCom implements ComTypes, Serializable{
	protected static final long serialVersionUID = 1L;
	protected int type;
	
	public ServerCom(int t){
		type = t;
	}
	
	public int type(){
		return type;
	}
}
