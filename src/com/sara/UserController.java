package com.sara;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import projectDao.LoginOracle;
import projectDao.ReimbursementOracle;
import projectModels.Employee;
import projectService.UserService;

@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)

public class UserController {

	public static void empLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setAccessControlHeaders(response);
		System.out.println("empLogin");
		response.getWriter().append("UserController-empLogin");
		response.sendRedirect("employeeLogin.html");
	}
	
	public static void manLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setAccessControlHeaders(response);
		System.out.println("manLogin");
		response.getWriter().append("UserController-manLogin");
		response.sendRedirect("managerLogin.html");
	}

	public static void empHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		System.out.println("empHome");

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		UserService user = new UserService();
		
		boolean result = user.empLogin(userName, password);

		if (result == true) {
			HttpSession session = request.getSession();
			 session.setAttribute("LOGGEDIN_USER", userName);
			 response.getWriter().append("" + result);
		}else
			response.getWriter().append("" + result);
	}
	
	public static void manHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		System.out.println("manHome");
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserService user = new UserService();
		boolean result = user.manLogin(userName, password);
		
		//boolean result =LoginOracle.getDao().getManagerNameAndPassword(userName, password);
		if (result == true) {
			HttpSession session = request.getSession();
			 session.setAttribute("LOGGEDIN_USER", userName);
			 response.getWriter().append("" + result);
		}else
			response.getWriter().append("" + result);
	}
	
	public static void submitReimbursement(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("submitReimbursement");

		if (session == null)
			response.sendRedirect("index.html");
		else {

			try {
				String empUser = (String) session.getAttribute("LOGGEDIN_USER");
				String reimbursementStatus = "Pending";
				String reimbursementType = request.getParameter("reimbursementType");
				String reimbursementAmt = request.getParameter("reimbursementAmount");
				double reimburseAmt = Double.parseDouble(reimbursementAmt);
				String reimbursementReason = request.getParameter("reason");
				Part filePart = request.getPart("receipt");
				String approvalStatus = "N/A";
				
				System.out.println("hello"+" "+empUser+" "+filePart);
				
				InputStream inputStream = null;
				
				if(filePart != null) {
					long fileSize = filePart.getSize();
					String fileContent = filePart.getContentType();
					inputStream = filePart.getInputStream();
				}
			ReimbursementOracle.getDao().createReimbursement(empUser, reimbursementStatus, inputStream, reimbursementType, reimburseAmt, reimbursementReason, approvalStatus);
			
//			if(ReimbursementOracle.createReimbursement(empUser, reimbursementStatus, inputStream, reimbursementType, reimburseAmt,
//			reimbursementReason, approvalStatus) == 0) {
//				request.setAttribute("Message", "Error inserting file");
//				getServletContext().getRequestDispatcher("/failure.jsp").forward(request, response);
//				
//			}else {
//				request.setAttribute("Message", "Your record inserted");
//				getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
//			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}}
	}

	public static void manViewEmp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		Gson gson = new Gson();
		String json = gson.toJson(LoginOracle.getDao().getAllEmployees());
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();
	}

	public static void createEmployee(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setAccessControlHeaders(response);
		System.out.println("createEmployee");
		HttpSession session = request.getSession(false);
		System.out.println("decideReimbursement");

		if (session == null)
			response.sendRedirect("index.html");
		else {

		String empEmail = request.getParameter("email");
		String newPassword = "Revature123";
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String jobTitle = request.getParameter("employeeJobTitle");

		
		boolean result = LoginOracle.getDao().createEmployee(empEmail, newPassword, firstName, lastName, address, city, state, jobTitle);
		if(result == true)
		{	
		JavaMailNewEmp.sendNewEmployeeMail("miss.iserah@gmail.com", empEmail, newPassword, firstName, lastName);
		response.getWriter().append("" + result);
		}else
			response.getWriter().append("" + result);
		}
	}

	public static void viewEmpInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("ViewEmpInfoServlet");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			String username = (String) session.getAttribute("LOGGEDIN_USER");
			Gson gson = new Gson();
			String json = gson.toJson(LoginOracle.getDao().viewSingleEmployeeInfo(username));
			PrintWriter out = response.getWriter();
			out.write(json.toString());
			out.flush();
		}
	}

	public static void viewPendingReimbursements(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletOutputStream sos = null;
		setAccessControlHeaders(response);
		Gson gson = new Gson();
		String json = gson.toJson(ReimbursementOracle.getDao().getPendingReimbursements());
		
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();

	}

	public static void viewCompletedReimbursements(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setAccessControlHeaders(response);
		Gson gson = new Gson();
		String json = gson.toJson(ReimbursementOracle.getDao().getCompletedReimbursements());
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();
	}

	public static void viewEmpPendingReimbursements(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("logout");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			setAccessControlHeaders(response);
			String currentUser = (String) session.getAttribute("LOGGEDIN_USER");
			Gson gson = new Gson();
			String json = gson.toJson(ReimbursementOracle.getDao().getEmpPendingReimbursements(currentUser));
			PrintWriter out = response.getWriter();
			out.write(json.toString());
			out.flush();
		}
	}

	public static void viewEmpCompletedReimbursements(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("logout");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			setAccessControlHeaders(response);
			String currentUser = (String) session.getAttribute("LOGGEDIN_USER");
			Gson gson = new Gson();
			String json = gson.toJson(ReimbursementOracle.getDao().getEmpCompletedReimbursements(currentUser));
			PrintWriter out = response.getWriter();
			out.write(json.toString());
			out.flush();
		}
	}

	public static String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("logout");

		if (session == null)
			return "index.html";
		else {
			session.invalidate();
		}
		return "index.html";
	}

	public static void empViewInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("EmployeeViewServlet");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			String username = (String) session.getAttribute("LOGGEDIN_USER");
			Gson gson = new Gson();
			String json = gson.toJson(LoginOracle.getDao().viewSingleEmployeeInfo(username));
			PrintWriter out = response.getWriter();
			out.write(json.toString());
			out.flush();
		}
	}

	public static void empViewByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("logout");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			String employee = request.getParameter("searchById");
			Gson gson = new Gson();
			String json = gson.toJson(ReimbursementOracle.getDao().getReimbursementByEmployee(employee));
			PrintWriter out = response.getWriter();
			out.write(json.toString());
			out.flush();
		}
	}

	public static void resetPassword(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		setAccessControlHeaders(response);
		System.out.println("resetPassword");
		//response.getWriter().append("UserController-resetPassword");

		String username = request.getParameter("username");
		String newPass = request.getParameter("newPassword");
		String reEnterPass = request.getParameter("reEnterPassword");

		boolean result = false;
		if (newPass.equals(reEnterPass))
			if(LoginOracle.getDao().resetPassword(username, newPass) == true)
			{
				result = true;
				response.getWriter().append("" + result);
			}
			else {
				response.getWriter().append("" + result);
			}

	}

	public static void decideReimbursements(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("decideReimbursement");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			String empUser = (String) session.getAttribute("LOGGEDIN_USER");
			String reimbursementId = request.getParameter("reimburseId");
			String reimbursementDecision= request.getParameter("decisionName");
			
			String status = "Completed";

			boolean result = ReimbursementOracle.getDao().decideReimbursement(empUser, status, reimbursementId, reimbursementDecision);
			if(result == true) {
				response.getWriter().append("" + result);
				JavaMailUtil.sendReimburseMail("miss.iserah@gmail.com");
			}else
				response.getWriter().append("" + result);
			
		}
	}

	public static void updateEmployeeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setAccessControlHeaders(response);
		HttpSession session = request.getSession(false);
		System.out.println("You may close the window.");

		if (session == null)
			response.sendRedirect("index.html");
		else {
			String empUser = (String) session.getAttribute("LOGGEDIN_USER");
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			
			boolean result = LoginOracle.getDao().updateEmployee(empUser, firstName, lastName, address, city, state);
			if(result == true)
				response.getWriter().append("" + result);
			else
				response.getWriter().append("" + result);

		}
	}
	
	private static void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET");

	}
}
