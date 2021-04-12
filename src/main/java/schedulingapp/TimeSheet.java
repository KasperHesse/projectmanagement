package schedulingapp;

import java.util.Calendar;
import java.util.Map;

public class TimeSheet {
	private Map<Calendar, Map<Developer, Integer>> timeUsage;

	public TimeSheet() {
		
	}
		
	public void registerTime(Developer dev, int hours, Calendar date) {
		
	}
	
	public void editRegisteredTime(Developer dev, Calendar date, int change) {
		
	}
	
	public void calculateNewHours(int hours, int hoursWorked) {
		
	}
	
	public void setHours(Developer dev, int hours, Calendar date) {
		
	}
	
	//Are any hours registered at all?
	public boolean containsHours() {
		return true;
	}
	
	public void editTime(Developer dev, int hours, Calendar date) {
		
	}
	
	public boolean tooManyHours(int hours) {
		return true;
	}
	
	public void viewTime() {
		
	}
}
