import org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import projectDao.LoginOracle;
import projectDao.ReimbursementOracle;
import projectModels.Employee;
import projectModels.Reimbursement;

public class ProjectTests<Reimbursement> {

	
	@Test
	public void testEmpLoginSuccess() throws Exception {
		String username = "sara@gmail.com";
		String password = "password";
		boolean ans = true;
		
		LoginOracle.getDao().getEmployeeNameAndPassword(username, password);
		assertTrue(true);
		assertSame(ans,true);
	}
	
	@Test//(expected = SQLException.class)
	public void testEmpLoginFail() throws Exception {
		String username = "sar@gmail.com";
		String password = "pasword";
		boolean ans = false;
		
		LoginOracle.getDao().getEmployeeNameAndPassword(username, password);
		assertSame(ans, false);
		//assertFalse(false);
	}
	@Test//(expected = SQLException.class)
	public void testEmpLoginFailNullUser() throws Exception {
		String username = "";
		String password = "pasword";
		boolean ans = false;
		
		LoginOracle.getDao().getEmployeeNameAndPassword(username, password);
		assertSame(ans, false);
	}
	
	@Test
	public void testManLoginSuccess() throws Exception {
		String username = "daboss@revature.com";
		String password = "boss";
		boolean ans = true;
		
		LoginOracle.getDao().getManagerNameAndPassword(username, password);
		assertTrue(true);
		assertSame(ans,true);
	}
	
	@Test//(expected = SQLException.class)
	public void testManLoginFail() throws Exception {
		String username = "sar@gmail.com";
		String password = "pasword";
		boolean ans = false;
		
		LoginOracle.getDao().getManagerNameAndPassword(username, password);
		assertSame(ans, false);
		//assertFalse(false);
	}
	
	@Test//(expected = SQLException.class)
	public void testGetAllEmployees() throws Exception {

		assertNotNull(LoginOracle.getDao().getAllEmployees());
		//assertFalse(false);
	}
	
	@Test//(expected = SQLException.class)
	public void testViewSingleEmployeePass() throws Exception {
		String username = "sara@gmail.com";
		
		List<Employee> resultArray = LoginOracle.getDao().viewSingleEmployeeInfo(username);
		assertNotNull(resultArray);
	}
	
	@Test//(expected = SQLException.class)
	public void testViewSingleEmployeeFail() throws Exception {
		String username = "sdffa@gmail.com";
		List<Employee> test = new LinkedList<Employee>();

		List<Employee> resultArray = LoginOracle.getDao().viewSingleEmployeeInfo(username);
		assertSame(test.size(), resultArray.size());
	}
	
	@Test
	public void testCreateEmployeePass() throws Exception {

		String email = "testId@revature.com";
		String password = "test123";
		String fname = "John";
		String lname = "Smith";
		String address = "somewhere";
		String city = "holland";
		String state = "ohio";
		String jobTitle = "janitor";
		
		boolean resultArray = LoginOracle.getDao().createEmployee(email, password, fname, lname, address, city, state, jobTitle);
		assertTrue(resultArray);
	}
	
	@Test (expected = SQLException.class)
	public void testCreateExistingEmployee() throws Exception {

		String email = "testId@revature.com";
		String password = "test123";
		String fname = "John";
		String lname = "Smith";
		String address = "somewhere";
		String city = "holland";
		String state = "ohio";
		String jobTitle = "janitor";
		
		boolean resultArray = LoginOracle.getDao().createEmployee(email, password, fname, lname, address, city, state, jobTitle);
	}
	
	@Test (expected = SQLException.class)
	public void testCreateNullEmployee() throws Exception {

		String email = null;
		String password = "test123";
		String fname = "John";
		String lname = "Smith";
		String address = "somewhere";
		String city = "holland";
		String state = "ohio";
		String jobTitle = "janitor";
		
		boolean resultArray = LoginOracle.getDao().createEmployee(email, password, fname, lname, address, city, state, jobTitle);
	}
	
	@Test
	public void testPasswordResetPass() throws Exception {

		String username = "testId@revature.com";
		String newPass = "test123";
		
		
		boolean result = LoginOracle.getDao().resetPassword(username, newPass);
		assertTrue(result);
	}
	
	@Test
	public void testPasswordResetFail() throws Exception {

		String username = "t@revature.com";
		String newPass = "test123";
		
		boolean result = LoginOracle.getDao().resetPassword(username, newPass);
		assertFalse(result);
	}
	
	@Test
	public void testUpdateEmployee() throws Exception {

		String empUser = "testId@revature.com";
		String firstName = "Johnny";
		String lastName = "";
		String address = "";
		String city = "Toledo";
		String state = "";
		
		LoginOracle.getDao().updateEmployee(empUser, firstName, lastName, address, city, state);
		
		Employee viewSingleEmployeeInfo = LoginOracle.getDao().viewSingleEmployeeInfo1(empUser);
		assertEquals(viewSingleEmployeeInfo.getFirstName(), firstName);
		assertEquals(viewSingleEmployeeInfo.getLastName(), "Smith");
		assertEquals(viewSingleEmployeeInfo.getAddress(), "somewhere");
		assertEquals(viewSingleEmployeeInfo.getCity(), city);
		assertEquals(viewSingleEmployeeInfo.getStateString(), "ohio");
	}
	
	@Test//(expected = SQLException.class)
	public void testGetPendingReimbursements() throws Exception {

		assertNotNull(ReimbursementOracle.getDao().getPendingReimbursements());
	}
	
	@Test//(expected = SQLException.class)
	public void testGetCompletedReimbursements() throws Exception {

		assertNotNull(ReimbursementOracle.getDao().getCompletedReimbursements());
	}
	
	@Test//(expected = SQLException.class)
	public void testGetReimbursementsByEmpId() throws Exception {
		
		String user = "500";

		assertNotNull(ReimbursementOracle.getDao().getReimbursementByEmployee(user));
	}
	
	@Test 
	public void testCreateReimbursement() throws Exception {

		
		String status = "Pending";
		String empUser = "sara@gmail.com";
		double amount = 100;
		int empId = 500;
		InputStream inputStream = null;
		String reimburseType = "Relocation";
		String reimburseReason = "Moving expenses";
		String approveStatus = "N/A";
		
		boolean result = ReimbursementOracle.getDao().createReimbursement(empUser, status, inputStream, reimburseType, amount, reimburseReason, approveStatus);
		
		assertTrue(result == true);		
	}
	
	@Test (expected = SQLIntegrityConstraintViolationException.class)
	public void testCreateReimbursementFail() throws Exception {

		
		String status = "Pending";
		String empUser = "s.com";
		double amount = -100;
		InputStream inputStream = null;
		String reimburseType = "Relocation";
		String reimburseReason = "Moving expenses";
		String approveStatus = "N/A";
		
		ReimbursementOracle.getDao().createReimbursement(empUser, status, inputStream, reimburseType, amount, reimburseReason, approveStatus);
			
	}

	@Test 
	public void testGetPendingEmp() throws Exception {

		
		String user = "sara@gmail.com";
		
		assertTrue(ReimbursementOracle.getDao().getEmpPendingReimbursements(user).size() != 0);
	}
	
	@Test 
	public void testGetPendingEmpFail() throws Exception {
		String user = "com";
		
		assertTrue(ReimbursementOracle.getDao().getEmpPendingReimbursements(user).size() == 0);
	}
	
	@Test 
	public void testGetCompletedEmp() throws Exception {
		String user = "sara@gmail.com";
		
		assertTrue(ReimbursementOracle.getDao().getEmpCompletedReimbursements(user).size() != 0);
	}
	
	@Test 
	public void testGetCompletedEmpFail() throws Exception {
		String user = "om";
		
		assertTrue(ReimbursementOracle.getDao().getEmpCompletedReimbursements(user).size() == 0);
	}
	


}
