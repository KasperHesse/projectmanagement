package schedulingapp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeSheet {
	private Map<Calendar, Map<Developer, Integer>> dateTimeUsage = new HashMap<Calendar, Map<Developer, Integer>>();
	//private Map<Developer, Integer> personTimeUsage = new HashMap<Developer, Integer>();
	Developer developer;

	
	public TimeSheet() {
	}
	
	/**
	 * registers time on the given TimeSheet
	 * @param The developer to register time on
	 * @param How much time to register
	 * @param The date to register time on
	 */
	public void registerTime(Developer dev, int hours, Calendar date) {
		if(tooManyHours(hours)) {
			throw new IllegalArgumentException("You can't register more than 24 hours on one day");
		}
		
		Map<Developer, Integer> var = dateTimeUsage.get(date);
		
		if(var == null) {
			var = new HashMap<Developer, Integer>();
			dateTimeUsage.put(date, var);
		}
		var.put(dev, hours);
	}
	
	/**
	 * Edits registered time on the given activity if allowed
	 * @param The developer that wants to edit their registered time
	 * @param How many hours to add to the registered time
	 * @param On what date he wants to register said hours
	 */
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
	public boolean tooManyHours(int hours) {
		if(hours > 24) {
		return true;
		}
		return false; 
	}
}












