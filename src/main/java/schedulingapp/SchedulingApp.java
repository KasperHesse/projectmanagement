package schedulingapp;

import java.util.*;
import java.util.stream.Collectors;

public class SchedulingApp {
	private Developer currentUser;
	private List<Developer> developerList;
	private List<Project> projectList;
	public Object getCurrentUser;
//	private Calendar currentDate;

	public SchedulingApp() {
		this.developerList = new ArrayList<Developer>();
		this.projectList = new ArrayList<Project>();
//		this.currentDate = new GregorianCalendar();
	}
	
	public void setCurrentUser(Developer currentUser) {
		this.currentUser = currentUser;
	}

	public void createProject(String name) {
		Project p = new Project(name, this);
//		p.setApp(this);
		projectList.add(p);
	}
	
	public List<Developer> getDevelopers() {
		return this.developerList;
	}

	/**
	 * Checks whether the scheduling app contains any project with the given name.
	 * @param name The name of the project being checked against
	 * @return True if a project with exactly name {@code name} exists, otherwise false
	 */
	public boolean hasProjectNamed(String name) {
		return projectList.stream().anyMatch(p -> p.getName().equals(name));
	}
	
	public boolean hasProjectWithNumber(String projectNumber) {
		return projectList.stream().anyMatch(p -> p.getProjectNumber().equals(projectNumber));
	}

	/**
	 * Returns a handle to the project with a given name 
	 * @param name The name of the project to be found
	 * @return A handle to the project if it exists, otherwise throws an exception
	 * @throws NoSuchElementException if the project was not found
	 */
	public Project getProjectByName(String name) throws NoSuchElementException {
		List<Project> matches =  projectList.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		}
		throw new NoSuchElementException("This project does not exist");
	}

	/**
	 * Creates a developer with the given data and adds them to the list of develoeprs
	 * @param initials The initials of the developer
	 * @param name The full name of the developer
	 */
	public void createDeveloper(String initials, String name) {
		addDeveloper(new Developer(initials, name));
		
	}

	/**
	 * Returns a handle to the developer with the given set of initials
	 * @param initials The initials of the developer to be found
	 * @return A handle to the developer if any developer with those initials exists, null otherwisee
	 */
	public Developer getDeveloperByInitials(String initials) {
		List<Developer> matches = developerList.stream().filter(d -> d.getInitials().equals(initials)).collect(Collectors.toList());
		if(matches.size() == 1) {
			return matches.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Adds a developer to the list of developers registered with the application
	 * @param developer The developer to add
	 */
	public void addDeveloper(Developer developer) {
		developerList.add(developer);
	}

	/**
	 * Checks whether a developer with the given set of initials exists.
	 * @param initials The initials to be checked against
	 * @return True if a developer exists, false otherwise
	 */
	public boolean hasDevWithInitials(String initials) {
		return developerList.stream().anyMatch(d -> d.getInitials().equals(initials));
	}

	/**
	 * Returns a handle to the developer/user currently logged into the system
	 */
	public Developer getCurrentUser() {
		return this.currentUser;
	}
	
//	public void setCurrentDate(int year, int month, int date) {
//		this.currentDate = new GregorianCalendar(year, month, date);
//	}

//	public Calendar getCurrentDate() {
//		return this.currentDate;
//	}

	public int getAmountOfProjectsCreatedYear(int year) {
		List<Project> matches =  projectList.stream().filter(p -> p.getCreationDate().get(1) == year).collect(Collectors.toList());
		return matches.size();
	}

}
