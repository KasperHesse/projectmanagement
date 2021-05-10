package dto;

import java.util.ArrayList;
import java.util.List;

import schedulingapp.Developer;

/**
 * 
 * @author Kasper Hesse, s183735
 *
 */
public class DeveloperInfo implements DTOinterface {
	private String name;
	private String initials;
	private boolean canAssist;

	public DeveloperInfo(Developer dev) {
		if(dev == null) {return;}
		this.name = dev.getName();
		this.initials = dev.getInitials();
		this.canAssist = dev.isAvailable();
	}


	@Override
	public String toPrint() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return this.initials;
	}

	public String getName() {
		return this.name;
	}

	public String getInitials() {
		return this.initials;
	}
	
	public boolean getAvailableFlag() {
		return this.canAssist;
	}
	
	public static List<DeveloperInfo> list2dto(List<Developer> developerList) {
		List<DeveloperInfo> list = new ArrayList<DeveloperInfo>();
		for(Developer d : developerList) {
			list.add(new DeveloperInfo(d));
		}
		return list;
	}
	
	/**
	 * Compares this DeveloperInfo to another DeveloperInfo, and returns true if they represent the same object OR
	 * if they have the same set of initials
	 * @return true if these Developers are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else {
			DeveloperInfo otherDev = (DeveloperInfo) obj;
			return otherDev.getInitials().equals(this.initials);
		}
	}

}
