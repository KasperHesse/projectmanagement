package dto;

import schedulingapp.Activity;
import schedulingapp.Developer;
import schedulingapp.Project;
import java.util.*;
import java.util.stream.*;

public class ProjectInfo implements DTOinterface {
	private String projectName;
	private String projectNumber;
	private String startDateString;
	private String stopDateString;
	private String projManInitials;

	
	@Override
	public String toPrint() {
		return this.toString();
	}
	
	public ProjectInfo(Project project) {
		this.projectName = project.getName();
		this.projectNumber = project.getProjectNumber();
		this.startDateString = Activity.cal2string(project.getStartDate());
		this.stopDateString = Activity.cal2string(project.getStopDate());
		Developer pm = project.getProjectManager();
		
		this.projManInitials = "No project manager set";
		if(pm != null) {
			this.projManInitials = pm.getInitials();
		}
	}
	
	public static List<ProjectInfo> list2dto(List<Project> projectList) {
		return projectList.stream().map(p -> new ProjectInfo(p)).collect(Collectors.toList());
//		List<ProjectInfo> list = new ArrayList<ProjectInfo>();
//		for(Project p : projectList) {
//			list.add(new ProjectInfo(p));
//		}
//		return list;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%s)", this.projectName, this.projectNumber);
	}
	
	public String getName() {
		return this.projectName;
	}
	
	public String getProjectNumber() {
		return this.projectNumber;
	}

	/**
	 * Returns a string with the most important information regarding this project
	 * @return
	 */
	public String getInfoString() {
		return String.format("Start: %s, Stop: %s, Project Manager: %s", startDateString, stopDateString, projManInitials);
	}
}
