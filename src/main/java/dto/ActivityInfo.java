package dto;
import java.util.*;
import schedulingapp.Activity;

public class ActivityInfo implements DTOinterface {
	private String name;
	
	public ActivityInfo(Activity activity) {
		this.name = activity.getName();
	}
	
	@Override
	public String toPrint() {
		return this.toString();
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

}
