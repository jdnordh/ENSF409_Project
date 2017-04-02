package data.transfer;
/**
 * Used to store a date with day, month, year
 * 
 * @author Jordan Nordh & Jeremy Phillips
 *
 */
public class Date {
	private int month;
	private int day;
	private int year;
	
	/**
	 * Construct a date
	 * @param d Day
	 * @param m Month
	 * @param y Year
	 */
	public Date(int d, int m, int y){
		month = m;
		day = d;
		year = y;
	}
	
	/** Construct a date from a string, default year is 2017 
	 * Uses the form : "October 8" */
	public Date(String d){
		boolean space = false;
		String temp = "";
		for (int i = 0; i < d.length(); i ++){
			if (space){
				temp += Character.toString(d.charAt(i));
			}
			else {
				if (d.charAt(i) == ' ') {
					space = true;
					month = getMonth(temp);
					temp = "";
				}
				else temp += Character.toString(d.charAt(i));
			}
		}
		day = Integer.parseInt(temp);
		year = 2017;
	}
	
	/** get which month a string is */
	private int getMonth(String m){
		if (m.equalsIgnoreCase("january")) return 1;
		if (m.equalsIgnoreCase("february")) return 2;
		if (m.equalsIgnoreCase("march")) return 3;
		if (m.equalsIgnoreCase("april")) return 4;
		if (m.equalsIgnoreCase("may")) return 5;
		if (m.equalsIgnoreCase("june")) return 6;
		if (m.equalsIgnoreCase("july")) return 7;
		if (m.equalsIgnoreCase("august")) return 8;
		if (m.equalsIgnoreCase("september")) return 9;
		if (m.equalsIgnoreCase("october")) return 10;
		if (m.equalsIgnoreCase("november")) return 11;
		if (m.equalsIgnoreCase("december")) return 12;
		return 0;
	}
	
	/** Send the date to a string  */
	public String toString(){
		String temp = "";
		if (month == 1) temp += "January";
		else if (month == 2) temp += "February";
		else if (month == 3) temp += "March";
		else if (month == 4) temp += "April";
		else if (month == 5) temp += "May";
		else if (month == 6) temp += "June";
		else if (month == 7) temp += "July";
		else if (month == 8) temp += "August";
		else if (month == 9) temp += "September";
		else if (month == 10) temp += "October";
		else if (month == 11) temp += "November";
		else if (month == 12) temp += "December";
		temp += " ";
		temp += Integer.toString(day);
		temp += ", ";
		temp += Integer.toString(year);
		return temp;
	}
}
