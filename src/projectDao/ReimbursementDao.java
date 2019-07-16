package projectDao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import Exceptions.DBException;
import projectModels.Reimbursement;

public interface ReimbursementDao {
	List<Reimbursement> getPendingReimbursements() throws DBException;
	
	List<Reimbursement> getCompletedReimbursements() throws DBException;
	
	List<Reimbursement> getReimbursementByEmployee(String employee) throws DBException;
	
	boolean createReimbursement(String empUser, String reimbursementStatus, InputStream inputStream, String reimbursementType,
			 double reimburseAmt, String reimbursementReason, String approvalStatus) throws SQLException, IOException;
	
	void createEmployee(String email, String newPassword, String fname, String lname, String address, String city,
			String state, String jobTitle) throws Exception;
	
	List<Reimbursement> getEmpPendingReimbursements(String currentUser2) throws DBException;
	
	List<Reimbursement> getEmpCompletedReimbursements(String currentUser2) throws DBException;
	
	boolean decideReimbursement(String empUser,  String status, String reimbursementId, String reimbursementDecision) throws DBException;
	
	Reimbursement viewReimbursment(String username) throws DBException;
}
