package com.sara;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import projectDao.LoginOracle;
import util.ConnectionUtil;


@MultipartConfig(maxFileSize = 16999999)
public class UserFrontControllerServlet extends HttpServlet {
	static Logger log = Logger.getLogger(UserFrontControllerServlet.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		setAccessControlHeaders(response);
		System.out.println("Front Controller Servlet - get");

		String requestURI = request.getRequestURI();
		System.out.println("RequestURI: " + requestURI);

		String contextPath = request.getContextPath();
		System.out.println("ContextPath: " + contextPath);

		String path = requestURI.substring(contextPath.length());
		System.out.println("path: " + path);

		switch (path) {
		case "/empLogin.do":
			System.out.println("EmpLoginServlet");
			UserController.empLogin(request, response);
			break;
		case "/manLogin.do":
			System.out.println("ManLoginServlet");
			UserController.manLogin(request, response);
			break;
		
		case "/logout.do":
			System.out.println("LoginOutServlet");
			String logout = UserController.logout(request, response);
			response.sendRedirect(logout);
			break;
			
		case "/managerViewEmp.do":
			System.out.println("ManViewEmpServlet");
			try {
				UserController.manViewEmp(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/empViewInfo.do":
			System.out.println("EmpViewInfoServlet");
			try {
				UserController.empViewInfo(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/empViewbyName.do":
			System.out.println("EmpViewByNameServlet");
			try {
				UserController.empViewByName(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		
		
		case "/viewCurrentInfo.do":
			System.out.println("ViewInfoServlet");
			try {
				UserController.viewEmpInfo(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/viewPendingReimbursements.do":
			System.out.println("ViewPendingReimbursementsServlet");
			try {
				UserController.viewPendingReimbursements(request, response);
			}  catch (Exception e) {
				log.error(e);
			}
			break;
		case "/viewCompletedReimbursements.do":
			System.out.println("ViewCompletedReimbursementsServlet");
			try {
				UserController.viewCompletedReimbursements(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/viewEmpPendingReimbursements.do":
			System.out.println("ViewEmpPendingReimbursementsServlet");
			try {
				UserController.viewEmpPendingReimbursements(request, response);
			} catch (Exception e1) {
				log.error(e1);
			}
			break;
		case "/viewEmpCompletedReimbursements.do":
			System.out.println("ViewEmpCompletedReimbursementsServlet");
			try {
				UserController.viewEmpCompletedReimbursements(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/decideReimburse.do":
			System.out.println("DecideReimburseServlet");
			try {
				UserController.decideReimbursements(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/updateEmpInfo.do":
			System.out.println("UpdateEmpInfoServlet");
			try {
				UserController.updateEmployeeInfo(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		}
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		System.out.println("Front Controller Servlet-Post");

		String requestURI = request.getRequestURI();
		System.out.println("RequestURI: " + requestURI);

		String contextPath = request.getContextPath();
		System.out.println("ContextPath: " + contextPath);

		String path = requestURI.substring(contextPath.length());
		System.out.println("path: " + path);

		switch (path) {
		case "/submitReimburse.do":
			System.out.println("SubmitReimburseServlet");
			try {
				UserController.submitReimbursement(request, response);
			}  catch (Exception e) {
				log.error(e);
			}
			break;
		case "/createEmployee.do":
			System.out.println("CreateEmployeeServlet");
			try {
				UserController.createEmployee(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		case "/empHome.do":
			System.out.println("EmpHomeServlet");
			try {
				UserController.empHome(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "/manHome.do":
			System.out.println("ManHomeServlet");
			try {
				UserController.manHome(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "/resetPassword.do":
			System.out.println("ResetPasswordServlet");
			try {
				UserController.resetPassword(request, response);
			} catch (Exception e) {
				log.error(e);
			}
			break;
		}
	}
	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET");

	}
}
