package schedulingapp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeSheet {
	private Map<Calendar, Map<Developer, Double>> dateTimeUsage = new HashMap<Calendar, Map<Developer, Double>>();
	//private Map<Developer, Integer> personTimeUsage = new HashMap<Developer, Integer>();
	Developer developer;

	
	public TimeSheet() {
		
		
	}
	
	public void registerTime(Developer dev, double hours, Calendar date) {
		if(tooManyHours(hours)) {
			throw new IllegalArgumentException("You can't register more than 24 hours on one day");
		}
		
		Map<Developer, Double> var = dateTimeUsage.get(date);
		
		if(var == null) {
			var = new HashMap<Developer, Double>();
			dateTimeUsage.put(date, var);
		}
		var.put(dev, hours);
	}
	
public void editTime(Developer dev, Calendar date, int change) {
		
		
		Map<Developer, Integer> var = dateTimeUsage.get(date);
		
		
		if(tooManyHours(dateTimeUsage.get(date).get(dev)+change)) {
			throw new IllegalArgumentException("You can't register more than 24 hours on one day"); 							//4
		}
		
		if(var == null) {																										//5
			throw new IllegalArgumentException("You can't edit time if none is registered");
		}
		
		dateTimeUsage.get(date).compute(dev, (key, val) -> (val == null) ? 1 : val + change);
	}

public int viewTime(Calendar date, Developer dev) {
	
	Map<Developer, Integer> var = dateTimeUsage.get(date);
	
	if(var == null) {
		throw new IllegalArgumentException("No time has been registered on the activity this given day");
	}
	
	return dateTimeUsage.get(date).get(dev);
}
	
	public void calculateNewHours(int hours, int hoursWorked) {
		
	}
	
	public void setHours(Developer dev, int hours, Calendar date) {
		
	}
	
	//Are any hours registered at all?
	public boolean containsHours() {
		return true;
	}
	
	
	/**
	 * Boolean to detect if person is registering more than 24 hours on one day
	 * @param The amount of hours you want to check
	 * @return
	 */
	public boolean tooManyHours(double hours) {
		if(hours > 24) {
		return true;
		}
		return false; 
	}
	
	
	/**
	 * Returns the number of hours registered on this activity for a given date and developer
	 * @param date The date for get the hours on
	 * @param dev The developer to get the hours for
	 * @return The number of hours registered
	 */
	public double viewTime(Calendar date, Developer dev) {
		Map<Developer, Double> tu = dateTimeUsage.get(date);
		double hours;
		if(tu == null) {
			return 0;
		} else {
			try {
				hours = tu.get(dev);
			} catch (NullPointerException e) {
				hours = 0;
			}
		}
		return hours;
//		return dateTimeUsage.get(date).get(dev);
	}
}












