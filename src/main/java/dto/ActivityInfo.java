package dto;
import java.text.*;
import java.util.*;
import schedulingapp.Activity;

public class ActivityInfo implements DTOinterface {
	private String name;
	private String startDate;
	private String stopDate;
	private int hoursBudgeted;
	
	public ActivityInfo(Activity activity) {
		this.name = activity.getName();
		this.startDate = Activity.cal2string(activity.getStartDate());
		this.stopDate = Activity.cal2string(activity.getStopDate());
		this.hoursBudgeted = activity.getHoursBudgeted();
	}
	
	@Override
	public String toPrint() {
		return String.format("%s [%s-%s]", this.name, this.startDate, this.stopDate);
	}
	
	public static List<ActivityInfo> list2dto(List<Activity> activityList) {
		List<ActivityInfo> list = new ArrayList<ActivityInfo>();
		for(Activity a : activityList) {
			list.add(new ActivityInfo(a));
		}
		return list;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Returns a string with the most important information regarding this activity
	 * @return
	 */
	public String getInfoString() {
		return String.format("Start: %s. Stop: %s. Hours budgeted: %d", startDate, stopDate, hoursBudgeted);
	}

}
