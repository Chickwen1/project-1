package projectService;

import Exceptions.DBException;
import projectDao.LoginDao;
import projectDao.LoginOracle;
import projectModels.Employee;

public class UserService {
	private LoginDao loginDAO = new LoginOracle();
	
	public boolean empLogin(String userId,String password) throws Exception {

		//Employee user = new Employee();
		String username = userId;
		String empPassword = password;

		boolean result = loginDAO.getEmployeeNameAndPassword(username, empPassword);
		return result; 
	}
	
	public boolean manLogin(String userId,String password) throws Exception {

		//Employee user = new Employee();
		String username = userId;
		String manPassword = password;

		boolean result = loginDAO.getManagerNameAndPassword(username, manPassword);
		return result; 
	}
}
