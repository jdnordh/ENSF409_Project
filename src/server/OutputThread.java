package server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import data.transfer.*;

public class OutputThread extends Thread{
	private int name;
	private ObjectOutputStream out;
	private Queue<Task> finishedTasks;
	
	public OutputThread(int n, Queue<Task> f){
		super(Integer.toString(n));
		name = n;
		finishedTasks = f;
		out = null;
	}
	
	public void run(){
		boolean running = true;
		while (running){
			try {
				while (true){
					while (out == null) sleep(1);
					if (!finishedTasks.isEmpty()){
						if (finishedTasks.deQueueNoRemoval().belongsTo(name)){
							Task temp = finishedTasks.deQueue();
							System.out.println("Output Thread "+ this.getName() + " doing task type: " + temp.getType());
							ServerOutputCom response = null;
							if (temp.getType() == ComTypes.REGISTER_USER){
								if (temp.isFinished()) response = new ServerOutputCom(ComTypes.REGISTER_CONFIRM);
								else response = new ServerOutputCom(ComTypes.FAILED_REGISTER);
							}
							else if (temp.getType() == ComTypes.LOG_IN){
								if (temp.isFinished()) response = new ServerOutputCom(ComTypes.USER_CONFIRM);
								else response = new ServerOutputCom(ComTypes.FAILED_LOGIN);
							}
							else if (temp.getType() == ComTypes.QUERY){
								if (temp.getQuery() <= ClientRequestCom.ALL_FLIGHTS){
									response = new ServerOutputCom(temp.getMultiple_flights());
								}
								else if (temp.getQuery() >= ClientRequestCom.TICKET){
									response = new ServerOutputCom(ClientRequestCom.RETURN_QUERY_TICKET, temp.getMultiple_tickets());
								}
							}
							else if (temp.getType() == ComTypes.BOOK_FLIGHT){
								response = new ServerOutputCom(ClientRequestCom.BOOK_CONFIRM);
							}
							else if (temp.getType() == ComTypes.CANCEL_TICKET){
								response = new ServerOutputCom(ClientRequestCom.TICKET_DELETE_CONFIRM);
							}
							else if (temp.getType() == ComTypes.ADD_FLIGHT){
								response = new ServerOutputCom(ClientRequestCom.FLIGHT_ADD_CONFIRM);
							}
							else if (temp.getType() == ComTypes.ADD_MULTIPLE_FLIGHTS){
								response = new ServerOutputCom(ClientRequestCom.FLIGHT_ADD_CONFIRM);
							}
							else if (temp.getType() == ComTypes.REMOVE_FLIGHT){
								response = new ServerOutputCom(ClientRequestCom.FLIGHT_DELETE_CONFIRM);
							}
							else if (temp.getType() == ComTypes.REMOVE_TICKET){
								response = new ServerOutputCom(ClientRequestCom.TICKET_DELETE_CONFIRM);
							}
							else if (temp.getType() == ComTypes.BAD_REQUEST){
								response = new ServerOutputCom(ClientRequestCom.BAD_REQUEST);
							}
							else if (temp.getType() == ComTypes.FAILED){
								response = new ServerOutputCom(ClientRequestCom.FAILED);
							}
							out.writeObject(response);
							out.flush();
							System.out.println("Output Thread "+ this.getName() + " wrote server response " + response.type() + " to stream");
						}
						sleep(1);
					}
					sleep(1);
				}
			} catch (InterruptedException e){
				System.out.println("Error in Output Thread "+ this.getName() + ": " + e.getMessage());
			} catch (IOException e){
				System.out.println("Error in Output Thread "+ this.getName() + ": " + e.getMessage());
				out = null;
			}catch (NullPointerException e) {
				System.out.println("Error in Output Thread "+ this.getName() + ": " + e.getMessage());
				out = null;
			}
		}
	}
	/** Set the stream */
	public void setStream(ObjectOutputStream s){
		out = s;
	}
}
