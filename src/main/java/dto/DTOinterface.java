package dto;

public interface DTOinterface {
	
	/**
	 * A DTO-specific toString-alternative. Useful for passing DTO's into the View's showOptions method
	 * @return A human-readable string representing the DTO
	 */
	public String toPrint();
}
