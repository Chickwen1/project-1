package projectDao;

import java.sql.SQLException;
import java.util.List;

import projectModels.Employee;


public interface LoginDao {

	boolean getEmployeeNameAndPassword(String username, String password);
	boolean getManagerNameAndPassword(String userName, String password);
	List<Employee> getAllEmployees() throws Exception;
	List<Employee> viewEmployeeInfo() throws Exception;
	List<Employee> viewSingleEmployeeInfo(String username) throws Exception ;
	Employee viewSingleEmployeeInfo1(String username) throws Exception;
	boolean createEmployee(String email, String newPassword, String fname, String lname, String address, String city,
			String state, String jobTitle) throws SQLException;
	boolean resetPassword(String username, String newPass) throws SQLException;
	boolean updateEmployee(String empUser, String firstName, String lastName, String address,
			String city, String state) throws SQLException;
}
