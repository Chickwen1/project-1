package projectModels;

import java.io.OutputStream;
import java.sql.Blob;
import java.util.Arrays;

public class Reimbursement {
	
	private int reimbursementId;
	private String status;
	private double amount;
	private int employeeId;
	private int managerId;
	private byte[] receipt;
	private String reimbursementType;
	private String reimbursementReason;
	private String approved;
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reimbursement(int reimbursementId, String status, double amount, int employeeId, int managerId,
			byte[] receipt, String reimbursementType, String reimbursementReason, String approved) {
		super();
		this.reimbursementId = reimbursementId;
		this.status = status;
		this.amount = amount;
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.receipt = receipt;
		this.reimbursementType = reimbursementType;
		this.reimbursementReason = reimbursementReason;
		this.approved = approved;
	}
	public int getReimbursementId() {
		return reimbursementId;
	}
	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public byte[] getReceipt() {
		return receipt;
	}
	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}
	public String getReimbursementType() {
		return reimbursementType;
	}
	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}
	public String getReimbursementReason() {
		return reimbursementReason;
	}
	public void setReimbursementReason(String reimbursementReason) {
		this.reimbursementReason = reimbursementReason;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + employeeId;
		result = prime * result + managerId;
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + reimbursementId;
		result = prime * result + ((reimbursementReason == null) ? 0 : reimbursementReason.hashCode());
		result = prime * result + ((reimbursementType == null) ? 0 : reimbursementType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (approved == null) {
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (managerId != other.managerId)
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (reimbursementId != other.reimbursementId)
			return false;
		if (reimbursementReason == null) {
			if (other.reimbursementReason != null)
				return false;
		} else if (!reimbursementReason.equals(other.reimbursementReason))
			return false;
		if (reimbursementType == null) {
			if (other.reimbursementType != null)
				return false;
		} else if (!reimbursementType.equals(other.reimbursementType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Reimbursement [reimbursementId=" + reimbursementId + ", status=" + status + ", amount=" + amount
				+ ", employeeId=" + employeeId + ", managerId=" + managerId + ", receipt=" + Arrays.toString(receipt)
				+ ", reimbursementType=" + reimbursementType + ", reimbursementReason=" + reimbursementReason
				+ ", approved=" + approved + "]";
	}
	


}
