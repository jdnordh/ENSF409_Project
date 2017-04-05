package server;

public class TaskThread extends Thread{
	private Queue<Task> tasks;
	private Queue<Task> finishedTasks;
	private Database database;
	
	/**
	 * Create a new task thread
	 * @param n Name as an int
	 * @param t Tasks to be completed
	 * @param f Finished tasks
	 * @param d Database
	 */
	public TaskThread(int n, Queue<Task> t, Queue<Task> f, Database d){
		super("Task thread " + Integer.toString(n));
		database = d;
		tasks = t;
		finishedTasks = f;
	}
	
	public void run(){
		try {
			while (true){
				if (!tasks.isEmpty()){
					Task t = tasks.deQueue();
					t.perform(database);
					finishedTasks.enQueue(t);
				}
				sleep(1);
			}
		} catch (InterruptedException e){
			System.out.println("Error: " + e.getMessage());
		}
	}
}
