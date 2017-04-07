package data.transfer;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Holds an hour and minutes
 * @author Jordan Nordh
 *
 */
public class TimeStamp implements Serializable{
	private static final long serialVersionUID = 1L;
	private int hours;
	private int minutes;
	
	/** Construct a new time stamp */
	public TimeStamp(int h, int m){
		hours = h;
		minutes = m;
	}
	
	/** Copy constructor */
	public TimeStamp(TimeStamp t){
		hours = t.getHours();
		minutes = t.getMinutes();
	}
	
	/**
	 * Construct a time stamp using the syntax 00:00, where hours are first and minutes are second
	 * @param t
	 */
	public TimeStamp(String t){
		try {
			boolean min = false;
			String temp = "";
			for (int i = 0; i < t.length(); i++){
				if (min){
					temp += t.charAt(i);
				}
				else {
					if (t.charAt(i) == ':'){
						min = true;
						hours = Integer.parseInt(temp);
						hours = hours % 24;
						temp = "";
					}
					else {
						temp += t.charAt(i);
					}
				}
			}
			minutes = Integer.parseInt(temp);
			minutes = minutes % 60;
			if (!min) {
				hours = minutes = 0;
			}
		} catch (Exception e){
			hours = minutes = 0;
		}
	}
	
	/** Send this class to a string */
	public String toString(){
		String r = "";
		r += Integer.toString(hours);
		r += ":";
		DecimalFormat f = new DecimalFormat("00"); 
		r += f.format(minutes);
		return r;
	}
	
	// Get and set
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
}
