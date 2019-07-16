package projectModels;

public class Manager {
	
	private int managerId;
	private String managerEmail;
	private String firstName;
	private String lastName;
	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Manager(int managerId, String managerEmail, String firstName, String lastName) {
		super();
		this.managerId = managerId;
		this.managerEmail = managerEmail;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((managerEmail == null) ? 0 : managerEmail.hashCode());
		result = prime * result + managerId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (managerEmail == null) {
			if (other.managerEmail != null)
				return false;
		} else if (!managerEmail.equals(other.managerEmail))
			return false;
		if (managerId != other.managerId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", managerEmail=" + managerEmail + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
	
	

}
