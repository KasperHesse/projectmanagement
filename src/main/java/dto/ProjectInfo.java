package dto;

import schedulingapp.Project;
import java.util.*;
import java.util.stream.*;

public class ProjectInfo implements DTOinterface {
	private String projectName;
	private String projectNumber;

	
	@Override
	public String toPrint() {
		return this.toString();
	}
	
	public ProjectInfo(Project project) {
		this.projectName = project.getName();
		this.projectNumber = project.getProjectNumber();
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
}
