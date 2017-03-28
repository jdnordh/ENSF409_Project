package data.transfer;

public abstract class ServerCom implements ComTypes{
	protected int type;
	
	public ServerCom(int t){
		type = t;
	}
	
	public int type(){
		return type;
	}
}
