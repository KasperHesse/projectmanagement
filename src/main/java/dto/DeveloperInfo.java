package dto;

import schedulingapp.Developer;

public class DeveloperInfo implements DTOinterface {
	private String name;
	private String initials;

	public DeveloperInfo(Developer dev) {
		this.name = dev.getName();
		this.initials = dev.getInitials();
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

}
