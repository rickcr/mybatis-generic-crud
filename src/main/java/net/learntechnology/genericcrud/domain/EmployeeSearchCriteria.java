package net.learntechnology.genericcrud.domain;

public class EmployeeSearchCriteria implements SearchCriteria {
	private String firstName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
