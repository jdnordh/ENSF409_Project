package server;
import java.util.Vector;

public class TaskThread extends Thread{
	private Queue<Task> tasks;
	private Queue<Task> finishedTasks;
	private Database database;
	private Vector<InputThread> inputThreads;
	
	/**
	 * Create a new task thread
	 * @param n Name as an int
	 * @param t Tasks to be completed
	 * @param f Finished tasks
	 * @param d Database
	 */
	public TaskThread(int n, Queue<Task> t, Queue<Task> f, Database d, Vector<InputThread> i){
		super("Task thread " + Integer.toString(n));
		database = d;
		tasks = t;
		finishedTasks = f;
		inputThreads = i;
	}
	
	public void run(){
		while (true){
			try {
				while (tasks.isEmpty()) sleep(1);
				if (!tasks.isEmpty()){
					Task t = tasks.deQueue();
					System.out.println(this.getName() + " Performing task: " + t.getType());
					t.perform(database);
					if (t.getType() == Task.LOG_IN || t.getType() == Task.REGISTER_USER) {
						inputThreads.get(t.belongsTo()).setLogin(t.isFinished());
						inputThreads.get(t.belongsTo()).setAdmin(t.getUser().isAdmin());
					}
					finishedTasks.enQueue(t);
				}
				sleep(1);
			} catch (InterruptedException e){
				System.out.println("Error in "+ this.getName() + ": " + e.getMessage());
			} catch (NullPointerException e){
				System.out.println("Error in "+ this.getName() + ": " + e.getMessage());
				//e.printStackTrace();
			}
		}
	}
}
