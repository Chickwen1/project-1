package projectDao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.sun.crypto.provider.RSACipher;
import com.sun.xml.internal.ws.Closeable;

import projectModels.Employee;
import util.ConnectionUtil;

public class LoginOracle implements LoginDao {
	static Logger log = Logger.getLogger(LoginOracle.class);
	
private static LoginOracle loginOracle;
public static String currentUser = null;

public LoginOracle() {
		
	}
	
	public static LoginDao getDao() {
		if (loginOracle == null) {
			loginOracle = new LoginOracle();
		}
		return loginOracle;
	}

	public boolean getEmployeeNameAndPassword(String user, String password) {
		
		Connection con = ConnectionUtil.getConnection();

	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT EMPLOYEE_EMAIL, EMPPASSWORD FROM PROJECT1EMPLOYEES WHERE EMPLOYEE_EMAIL = ?");
	        ps.setString(1, user);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        		if(rs.getString("EMPPASSWORD").equals(password))
	        		{
	        			currentUser = user;
	        			return true;
	        		}
	        		
	        }
	        rs.close();
    		ps.close();
	        
	    } catch (SQLException ex) {
	    	log.error("Error");
	    }
	    
		return false;
	}

	public boolean getManagerNameAndPassword(String userName, String password) {
Connection con = ConnectionUtil.getConnection();

	    try {
	        PreparedStatement ps = con.prepareStatement("SELECT MANAGER_EMAIL, MANPASSWORD FROM PROJECT1MANAGERS WHERE MANAGER_EMAIL = ?");
	        ps.setString(1, userName);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        		if(rs.getString("MANPASSWORD").equals(password))
	        		{
	        			currentUser = userName;
	        			return true;
	        		}
	        }
	        
	    } catch (SQLException ex) {
	    	log.error("Error");
	    }
	    
		return false;
	}
	
	public List<Employee> getAllEmployees() throws Exception {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		List<Employee> listOfEmployees = null;

		try {
			String sql = "select e.EMPLOYEEID, e.EMPLOYEE_EMAIL, e.FIRSTNAME, e.LASTNAME, e.ADDRESS, e.CITY, e.STATE, e.JOB_TITLE from PROJECT1EMPLOYEES e";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			listOfEmployees = new LinkedList<Employee>();

			while (rs.next()) {
				listOfEmployees.add(new Employee(rs.getInt("EMPLOYEEID"), rs.getString("EMPLOYEE_EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("ADDRESS"),rs.getString("CITY"),rs.getString("STATE"),rs.getString("JOB_TITLE")));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
		}
		for (Employee employees : listOfEmployees) {
			System.out.println(employees);
		}
		return listOfEmployees;
	}
	
	public List<Employee> viewEmployeeInfo() throws Exception {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		List<Employee> employeeInfo = null;

		try {
			String sql = "select e.EMPLOYEEID, e.EMPLOYEE_EMAIL, e.FIRSTNAME, e.LASTNAME, e.ADDRESS, e.CITY, e.STATE, e.JOB_TITLE from PROJECT1EMPLOYEES e WHERE EMPLOYEE_EMAIL = '?'";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, currentUser);
			ResultSet rs = ps.executeQuery();

			employeeInfo = new LinkedList<Employee>();

			while (rs.next()) {
				employeeInfo.add(new Employee(rs.getInt("EMPLOYEEID"), rs.getString("EMPLOYEE_EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("ADDRESS"),rs.getString("CITY"),rs.getString("STATE"),rs.getString("JOB_TITLE")));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
		}
		for (Employee employee : employeeInfo) {
			System.out.println(employee);
		}
		return employeeInfo;
	}
	
	public List<Employee> viewSingleEmployeeInfo(String username) throws Exception {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		List<Employee> employeeInfo = null;

		try {
			String sql = "select e.EMPLOYEEID, e.EMPLOYEE_EMAIL, e.FIRSTNAME, e.LASTNAME, e.ADDRESS, e.CITY, e.STATE, e.JOB_TITLE from PROJECT1EMPLOYEES e WHERE EMPLOYEE_EMAIL = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			employeeInfo = new LinkedList<Employee>();

			while (rs.next()) {
				employeeInfo.add(new Employee(rs.getInt("EMPLOYEEID"), rs.getString("EMPLOYEE_EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("ADDRESS"),rs.getString("CITY"),rs.getString("STATE"),rs.getString("JOB_TITLE")));
			}
			rs.close();
    		ps.close();
    		
		} catch (SQLException e) {
			log.error("Error");
		}
		for (Employee employee : employeeInfo) {
			System.out.println(employee);
		}
		return employeeInfo;
	}
	public Employee viewSingleEmployeeInfo1(String username) throws Exception {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		Employee employeeInfo = null;

		try {
			String sql = "select e.EMPLOYEEID, e.EMPLOYEE_EMAIL, e.FIRSTNAME, e.LASTNAME, e.ADDRESS, e.CITY, e.STATE, e.JOB_TITLE from PROJECT1EMPLOYEES e WHERE EMPLOYEE_EMAIL = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();


			while (rs.next()) {
				employeeInfo = new Employee(rs.getInt("EMPLOYEEID"), rs.getString("EMPLOYEE_EMAIL"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("ADDRESS"),rs.getString("CITY"),rs.getString("STATE"),rs.getString("JOB_TITLE"));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
		}
		
		return employeeInfo;
	}
	
	public boolean createEmployee(String email, String newPassword, String fname, String lname, String address, String city,
			String state, String jobTitle) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "call create_employee(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		CallableStatement ps = con.prepareCall(sql);
		ps.setString(1, email);
		ps.setString(2, newPassword);
		ps.setString(3, fname);
		ps.setString(4, lname);
		ps.setString(5, address);
		ps.setString(6, city);
		ps.setString(7, state);
		ps.setString(8, jobTitle);
		ps.registerOutParameter(9, Types.INTEGER);
		ps.execute();
		
		int id = ps.getInt(9);

		ps.close();
		return true;		
	}

	public boolean resetPassword(String username, String newPass) throws SQLException {
		Connection con = ConnectionUtil.getConnection();

		PreparedStatement ps = con.prepareStatement("SELECT EMPLOYEE_EMAIL FROM PROJECT1EMPLOYEES WHERE EMPLOYEE_EMAIL = ?");
		ps.setString(1, username);
		ps.executeQuery();
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			if (rs.getString("EMPLOYEE_EMAIL").equals(username)) {
				ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES SET EMPPASSWORD = ? WHERE EMPLOYEE_EMAIL = ?");
				ps.setString(1, newPass);
				ps.setString(2, username);
				ps.executeUpdate();
				System.out.println("You have changed your password.");
			} 
			rs.close();
    		ps.close();
			return true;
		}
		return false;

		
	}

	public boolean updateEmployee(String empUser, String firstName, String lastName, String address,
			String city, String state) throws SQLException {
		Connection con = ConnectionUtil.getConnection();

		PreparedStatement ps = con.prepareStatement("SELECT e.EMPLOYEEID, e.FIRSTNAME, e.LASTNAME, e.ADDRESS, e.CITY, E.STATE FROM PROJECT1EMPLOYEES e WHERE e.EMPLOYEE_EMAIL = ?");
		ps.setString(1, empUser);
		ps.executeQuery();
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
				if(firstName != "") {
				ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES e SET e.FIRSTNAME = ? WHERE e.EMPLOYEE_EMAIL = ?");
				ps.setString(1, firstName);
				ps.setString(2, empUser);
				ps.executeUpdate();
				}
				if(lastName != "") {
					ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES e SET e.LASTNAME = ? WHERE e.EMPLOYEE_EMAIL = ?");
					ps.setString(1, lastName);
					ps.setString(2, empUser);
					ps.executeUpdate();
					}
				if(address != "") {
					ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES e SET e.ADDRESS = ? WHERE e.EMPLOYEE_EMAIL = ?");
					ps.setString(1, address);
					ps.setString(2, empUser);
					ps.executeUpdate();
					}
				if(city != "") {
					ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES e SET e.CITY = ? WHERE e.EMPLOYEE_EMAIL = ?");
					ps.setString(1, city);
					ps.setString(2, empUser);
					ps.executeUpdate();
					}
				if(state != "") {
					ps = con.prepareStatement("UPDATE PROJECT1EMPLOYEES e SET e.state = ? WHERE e.EMPLOYEE_EMAIL = ?");
					ps.setString(1, state);
					ps.setString(2, empUser);
					ps.executeUpdate();
					}
				rs.close();
	    		ps.close();
				return true;

		}
		return false;
	}


}
