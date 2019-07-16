package projectDao;

import java.io.File;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;

import com.google.gson.JsonElement;

import Exceptions.DBException;
import projectModels.Employee;
import projectModels.Reimbursement;
import util.ConnectionUtil;

public class ReimbursementOracle implements ReimbursementDao {

	static Logger log = Logger.getLogger(ReimbursementOracle.class);
	private static ReimbursementOracle reimbursementOracle;
	public static String currentUser = null;
	public static int currentReimbusementId = 0;
	

	private ReimbursementOracle() {

	}

	public static ReimbursementDao getDao() {
		if (reimbursementOracle == null) {
			reimbursementOracle = new ReimbursementOracle();
		}
		return reimbursementOracle;
	}
	
	public List<Reimbursement> getPendingReimbursements() throws DBException {
		byte[] img = null;
		
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
		}
		List<Reimbursement> pendingReimbursements = null;

		try {
			String sql = "select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r WHERE STATUS = 'Pending'";
			Statement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			pendingReimbursements = new LinkedList<Reimbursement>();

			while (rs.next()) {
				pendingReimbursements.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
				//img = rs.getBytes(6);
				//pendingReimbursements = new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED"));
				//pendingReimbursements.add(e);
				
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursements", e);
		}
		for (Reimbursement reimbursements : pendingReimbursements) {
			System.out.println(reimbursements);
		}
		return pendingReimbursements;
	}
	
	public List<Reimbursement> getCompletedReimbursements() throws DBException {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
		}
		List<Reimbursement> completedReimbursements = null;

		try {
			String sql = "select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r WHERE STATUS = 'Completed'";
			Statement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			completedReimbursements = new LinkedList<Reimbursement>();

			while (rs.next()) {
				completedReimbursements.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
				//.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"),rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get completed reimbursements", e);
		}
		for (Reimbursement reimbursements : completedReimbursements) {
			System.out.println(reimbursements);
		}
		return completedReimbursements;
	}
	
	public List<Reimbursement> getReimbursementByEmployee(String employee) throws DBException {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {	
		}
		List<Reimbursement> reimbursementByName = null;

		try {
			
			PreparedStatement ps = con.prepareStatement("select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r WHERE r.EMPLOYEEID LIKE ?");
			//int empId = Integer.parseInt(employee);
			ps.setString(1, "%"+employee+"%");
			ResultSet rs = ps.executeQuery();

			reimbursementByName = new LinkedList<Reimbursement>();

			while (rs.next()) {
				reimbursementByName.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
				//.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursement", e);
		}
		for (Reimbursement reimbursements : reimbursementByName) {
			System.out.println(reimbursements);
		}
		return reimbursementByName;
	}
	
	public boolean createReimbursement(String empUser, String reimbursementStatus, InputStream inputStream, String reimbursementType,
			 double reimburseAmt, String reimbursementReason, String approvalStatus) throws SQLException, IOException {

		Connection con = ConnectionUtil.getConnection();

		PreparedStatement ps = con.prepareStatement("SELECT EMPLOYEEID FROM PROJECT1EMPLOYEES WHERE EMPLOYEE_EMAIL = ?");
		ps.setString(1, empUser);
		ResultSet rs = ps.executeQuery();
		int empId = 0;
		while (rs.next()) {
			empId=rs.getInt("EMPLOYEEID");
		}
		rs.close();
		
		PreparedStatement ps2 = con.prepareStatement("INSERT INTO PROJECT1REIMBURSEMENT (STATUS, AMOUNT, EMPLOYEEID, REIMBURSE_IMG, REIMBURSEMENTTYPE, REIMBURSEMENTREASON, APPROVED) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps2.setString(1, reimbursementStatus);
		ps2.setDouble(2, reimburseAmt);
		ps2.setInt(3, empId);
		ps2.setBlob(4, inputStream);
		ps2.setString(5, reimbursementType);
		ps2.setString(6, reimbursementReason);
		ps2.setString(7, approvalStatus);
		ps2.executeUpdate();		
		
		ps.close();
		
		return true;

		}
	
	public void createEmployee(String email, String newPassword, String fname, String lname, String address, String city,
			String state, String jobTitle) throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		String sql = "call create_employee(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		CallableStatement ps = con.prepareCall(sql);
		ps.setString(1, email);
		ps.setString(2, newPassword);
		ps.setString(4, lname);
		ps.setString(5, address);
		ps.setString(6, city);
		ps.setString(7, state);
		ps.setString(8, jobTitle);
		ps.registerOutParameter(9, Types.INTEGER);
		ps.execute();
		int id = ps.getInt(9);

		ps.close();
		
	}

	public List<Reimbursement> getEmpPendingReimbursements(String currentUser2) throws DBException {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
		}
		List<Reimbursement> empPendingReimbursements = null;

		try {
			PreparedStatement ps = con.prepareStatement("select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r INNER JOIN PROJECT1EMPLOYEES e ON e.EMPLOYEEID = r.EMPLOYEEID WHERE r.STATUS = 'Pending' AND e.EMPLOYEE_EMAIL = ?");
			ps.setString(1, currentUser2);
			

			ResultSet rs = ps.executeQuery();

			empPendingReimbursements = new LinkedList<Reimbursement>();

			while (rs.next()) {
				empPendingReimbursements.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
			}
			
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursements", e);
		}
		for (Reimbursement reimbursements : empPendingReimbursements) {
			System.out.println(reimbursements);
		}
		return empPendingReimbursements;
	}
	
	public List<Reimbursement> getEmpCompletedReimbursements(String currentUser2) throws DBException {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		List<Reimbursement> empCompletedReimbursements = null;

		try {
			PreparedStatement ps = con.prepareStatement("select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r INNER JOIN PROJECT1EMPLOYEES e ON e.EMPLOYEEID = r.EMPLOYEEID WHERE r.STATUS = 'Completed' AND e.EMPLOYEE_EMAIL = ?");
			ps.setString(1, currentUser2);

			ResultSet rs = ps.executeQuery();

			empCompletedReimbursements = new LinkedList<Reimbursement>();

			while (rs.next()) {
				empCompletedReimbursements.add(new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED")));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursements", e);
		}
		for (Reimbursement reimbursements : empCompletedReimbursements) {
			System.out.println(reimbursements);
		}
		return empCompletedReimbursements;
	}

	public boolean decideReimbursement(String empUser,  String status, String reimbursementId, String reimbursementDecision) throws DBException  {
		Connection con = ConnectionUtil.getConnection();
		try {
		PreparedStatement ps = con.prepareStatement("SELECT m.MANAGERID FROM PROJECT1MANAGERS m WHERE m.MANAGER_EMAIL = ?");
		ps.setString(1, empUser);
		ps.executeQuery();
		
		ResultSet rs = ps.executeQuery();
		
		
		while (rs.next()) {
			int managerId = rs.getInt("MANAGERID");
				ps = con.prepareStatement("UPDATE PROJECT1REIMBURSEMENT r SET r.STATUS = ?, r.MANAGERID = ?, r.APPROVED = ? WHERE r.REIMBURSEMENTID = ?");
				ps.setString(1, status);
				ps.setInt(2, managerId);
				ps.setString(3, reimbursementDecision);
				ps.setString(4, reimbursementId);
				ps.executeUpdate();
				rs.close();
	    		ps.close();
			return true;
		}
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursements", e);
		}
		
		return false;
		
	}
	
	public Reimbursement viewReimbursment(String username) throws DBException {
		Connection con = ConnectionUtil.getConnection();
		if (con == null) {
			System.out.println(":(");	
		}
		Reimbursement reimburseInfo = null;

		try {
			String sql = "select r.REIMBURSEMENTID, r.STATUS, r.AMOUNT, r.EMPLOYEEID, r.MANAGERID, r.REIMBURSE_IMG, r.REIMBURSEMENTTYPE, r.REIMBURSEMENTREASON, r.APPROVED from PROJECT1REIMBURSEMENT r INNER JOIN PROJECT1EMPLOYEES e ON e.EMPLOYEEID = r.EMPLOYEEID WHERE r.STATUS = 'Pending' AND e.EMPLOYEE_EMAIL = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();


			while (rs.next()) {
				reimburseInfo = new Reimbursement(rs.getInt("REIMBURSEMENTID"), rs.getString("STATUS"), rs.getDouble("AMOUNT"), rs.getInt("EMPLOYEEID"), rs.getInt("MANAGERID"), rs.getBytes(6), rs.getString("REIMBURSEMENTTYPE"),rs.getString("REIMBURSEMENTREASON"), rs.getString("APPROVED"));
			}
			rs.close();
    		ps.close();
			
		} catch (SQLException e) {
			log.error("Error");
			throw new DBException("Unable to get reimbursements", e);
		}
		
		return reimburseInfo;
	}
}
