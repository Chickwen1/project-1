/**
 * 
 */

function ajaxGET(url, formData) {
	console.log(formData);
	var promiseObj = new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var response = this.responseText;
				resolve(response);
			} else if (this.readyState == 4 && this.status != 200) {
				reject(this.responseText);
			}
		}
		xhr.open("GET", url + "?" + formData, true);
		xhr.send();
	});
	return promiseObj;
}

function ajaxPOST(url, formData) {
	console.log(formData);
	var promiseObj = new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var response = this.responseText;
				resolve(response);
			} else if (this.readyState == 4 && this.status != 200) {
				reject(this.responseText);
			}
		}
		xhr.open("POST", url, true);
		xhr.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xhr.send(formData);
	});
	return promiseObj;
}

function ajaxGETResult(url, method, successCallback, errorCallback) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = this.responseText;
			successCallback(response);
		} else if (this.readyState == 4 && this.status != 200) {
			errorCallback(this.responseText);
		}
	}
	xhr.open("GET", url, true);
	xhr.send();
}

function clearForms() {
	var i;
	for (i = 0; (i < document.forms.length); i++) {
		document.forms[i].reset();
	}
}

function viewInfo() {
	console.log('viewInfo');
	var url = "http://localhost:8081/revature-project-one/empViewInfo.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var employee = JSON.parse(response);
		console.log(employee.length);

		var form = document.querySelector("input");
		for (var i = 0; i < employee.length; i++) {
			var employeeObj = employees[i];

			document.getElementById("empid").value = employeeObj.employeeId;
			document.getElementById("empEmail").value = employeeObj.empEmail;
			document.getElementById("firstname").value = employeeObj.firstName;
			document.getElementById("lastname").value = employeeObj.lastName;
			document.getElementById("address").value = employeeObj.address;
			document.getElementById("city").value = employeeObj.city;
			document.getElementById("state").value = employeeObj.stateString;
			document.getElementById("jobTitle").value = employeeObj.jobTitle;
		}
	});
}

function loadEmployees() {
	console.log('loadEmployees');
	var url = "http://localhost:8081/revature-project-one/managerViewEmp.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var employees = JSON.parse(response);
		console.log(employees.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < employees.length; i++) {
			var employeesObj = employees[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = employeesObj.employeeId;
			var td2 = document.createElement("td");
			td2.textContent = employeesObj.empEmail;
			var td3 = document.createElement("td");
			td3.textContent = employeesObj.firstName;
			var td4 = document.createElement("td");
			td4.textContent = employeesObj.lastName;
			var td5 = document.createElement("td");
			td5.textContent = employeesObj.address;
			var td6 = document.createElement("td");
			td6.textContent = employeesObj.city;
			var td7 = document.createElement("td");
			td7.textContent = employeesObj.stateString;
			var td8 = document.createElement("td");
			td8.textContent = employeesObj.jobTitle;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function loadEmployeeInformation() {
	console.log('loadEmployees');
	var url = "http://localhost:8081/revature-project-one/empViewInfo.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var employees = JSON.parse(response);
		console.log(employees.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < employees.length; i++) {
			var employeesObj = employees[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = employeesObj.employeeId;
			var td2 = document.createElement("td");
			td2.textContent = employeesObj.empEmail;
			var td3 = document.createElement("td");
			td3.textContent = employeesObj.firstName;
			var td4 = document.createElement("td");
			td4.textContent = employeesObj.lastName;
			var td5 = document.createElement("td");
			td5.textContent = employeesObj.address;
			var td6 = document.createElement("td");
			td6.textContent = employeesObj.city;
			var td7 = document.createElement("td");
			td7.textContent = employeesObj.stateString;
			var td8 = document.createElement("td");
			td8.textContent = employeesObj.jobTitle;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function loadEmployeeByName() {
	console.log('loadEmployeeByName');
	var empId = document.getElementById("searchId").value;// 4;//d

	var url = "http://localhost:8081/revature-project-one/empViewbyName.do?searchById="
			+ empId;
	console.log(url);
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var reimbursements = JSON.parse(response);
		console.log(reimbursements.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < reimbursements.length; i++) {
			var reimbursementsObj = reimbursements[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = reimbursementsObj.reimbursementId;
			var td2 = document.createElement("td");
			td2.textContent = reimbursementsObj.status;
			var td3 = document.createElement("td");
			td3.textContent = reimbursementsObj.amount;
			var td4 = document.createElement("td");
			td4.textContent = reimbursementsObj.employeeId;
			var td5 = document.createElement("td");
			td5.textContent = reimbursementsObj.managerId;
			var td6 = document.createElement("td");
			td6.textContent = reimbursementsObj.reimbursementType;
			var td7 = document.createElement("td");
			td7.textContent = reimbursementsObj.reimbursementReason;
			var td8 = document.createElement("td");
			td8.textContent = reimbursementsObj.approved;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function deleteCurrentRows() {
	var tableHeaderRowCount = 1;
	// var table = document.getElementById('employeesTable');
	var table = document.querySelector(".table");
	var rowCount = table.rows.length;
	for (var i = tableHeaderRowCount; i < rowCount; i++) {
		table.deleteRow(tableHeaderRowCount);
	}
}

function loadPendingReimbursements() {
	// ServletOutputStream sos = null;
	console.log('loadPendingReimbursements');
	var url = "http://localhost:8081/revature-project-one/viewPendingReimbursements.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var reimbursements = JSON.parse(response);
		console.log(reimbursements.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < reimbursements.length; i++) {
			var reimbursementsObj = reimbursements[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = reimbursementsObj.reimbursementId;
			var td2 = document.createElement("td");
			td2.textContent = reimbursementsObj.status;
			var td3 = document.createElement("td");
			td3.textContent = reimbursementsObj.amount;
			var td4 = document.createElement("td");
			td4.textContent = reimbursementsObj.employeeId;
			var td5 = document.createElement("td");
			td5.textContent = reimbursementsObj.managerId;
			// var td6 = document.createElement("td");
			// td6.textContent = reimbursementsObj.receipt;
			var td6 = document.createElement("td");
			td6.textContent = reimbursementsObj.reimbursementType;
			var td7 = document.createElement("td");
			td7.textContent = reimbursementsObj.reimbursementReason;
			var td8 = document.createElement("td");
			td8.textContent = reimbursementsObj.approved;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			// tr.append(td9);
			tbody.append(tr);
		}
	});
}

function loadCompletedReimbursements() {
	console.log('loadCompletedReimbursements');
	var url = "http://localhost:8081/revature-project-one/viewCompletedReimbursements.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var reimbursements = JSON.parse(response);
		console.log(reimbursements.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < reimbursements.length; i++) {
			var reimbursementsObj = reimbursements[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = reimbursementsObj.reimbursementId;
			var td2 = document.createElement("td");
			td2.textContent = reimbursementsObj.status;
			var td3 = document.createElement("td");
			td3.textContent = reimbursementsObj.amount;
			var td4 = document.createElement("td");
			td4.textContent = reimbursementsObj.employeeId;
			var td5 = document.createElement("td");
			td5.textContent = reimbursementsObj.managerId;
			var td6 = document.createElement("td");
			td6.textContent = reimbursementsObj.reimbursementType;
			var td7 = document.createElement("td");
			td7.textContent = reimbursementsObj.reimbursementReason;
			var td8 = document.createElement("td");
			td8.textContent = reimbursementsObj.approved;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function loadEmpPendingReimbursements() {
	console.log('loadPendingReimbursements');
	var url = "http://localhost:8081/revature-project-one/viewEmpPendingReimbursements.do";

	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var reimbursements = JSON.parse(response);
		console.log(reimbursements.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < reimbursements.length; i++) {
			var reimbursementsObj = reimbursements[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = reimbursementsObj.reimbursementId;
			var td2 = document.createElement("td");
			td2.textContent = reimbursementsObj.status;
			var td3 = document.createElement("td");
			td3.textContent = reimbursementsObj.amount;
			var td4 = document.createElement("td");
			td4.textContent = reimbursementsObj.employeeId;
			var td5 = document.createElement("td");
			td5.textContent = reimbursementsObj.managerId;
			var td6 = document.createElement("td");
			td6.textContent = reimbursementsObj.reimbursementType;
			var td7 = document.createElement("td");
			td7.textContent = reimbursementsObj.reimbursementReason;
			var td8 = document.createElement("td");
			td8.textContent = reimbursementsObj.approved;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function loadEmpCompletedReimbursements() {
	console.log('loadCompletedReimbursements');
	var url = "http://localhost:8081/revature-project-one/viewEmpCompletedReimbursements.do";
	ajaxGETResult(url, "GET", function(response) {
		console.log(response);
		var reimbursements = JSON.parse(response);
		console.log(reimbursements.length);

		var tbody = document.querySelector("tbody");
		for (var i = 0; i < reimbursements.length; i++) {
			var reimbursementsObj = reimbursements[i];

			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			td1.textContent = reimbursementsObj.reimbursementId;
			var td2 = document.createElement("td");
			td2.textContent = reimbursementsObj.status;
			var td3 = document.createElement("td");
			td3.textContent = reimbursementsObj.amount;
			var td4 = document.createElement("td");
			td4.textContent = reimbursementsObj.employeeId;
			var td5 = document.createElement("td");
			td5.textContent = reimbursementsObj.managerId;
			var td6 = document.createElement("td");
			td6.textContent = reimbursementsObj.reimbursementType;
			var td7 = document.createElement("td");
			td7.textContent = reimbursementsObj.reimbursementReason;
			var td8 = document.createElement("td");
			td8.textContent = reimbursementsObj.approved;
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tbody.append(tr);
		}
	});
}

function submitEmployee() {
	console.log('submitEmployee');
	event.preventDefault();

	var email = document.getElementById("email").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var address = document.getElementById("address").value;
	var city = document.getElementById("city").value;
	var state = document.getElementById("state").value;
	var jobtitle = document
			.querySelector('input[name = employeeJobTitle]:checked').value;

	var formData = "email=" + email + "&firstname=" + firstname + "&lastname="
			+ lastname + "&address=" + address + "&city=" + city + "&state="
			+ state + "&employeeJobTitle=" + jobtitle;
	var url = "http://localhost:8081/revature-project-one/createEmployee.do";

	ajaxPOST(url, formData).then(
			function(response) {
				console.log(response);
				if (response === "true") {
					alert('Employee ' + email + 'has been created for '
							+ firstname + ' ' + lastname + '!');
					window.location = "managerEmployees.html";
				} else {

					alert('Could not create employee');
					window.location = "managerEmployees.html";
				}
			});
	return false;

}

function submitReimbursement() {
	console.log('submitReimburement');
	event.preventDefault();

	var reimburseType = document.getElementById("reimburseType").value;
	var reimburseAmt = document.getElementById("reimburseAmt").value;
	var reimburseReason = document.getElementById("reimburseReason").value;
	var receipt = document.getElementById("receipt").value;
	var formData = "reimburseType=" + reimburseType + "&reimburseAmt="
			+ reimburseAmt + "&reimburseReason=" + reimburseReason
			+ "&receipt=" + receipt;
	var url = "http://localhost:8081/revature-project-one/submitReimburse.do";
	ajaxPOST(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			alert("Your reimbursement has been submitted!");
			window.location = "empReimbursements.html";
		} else {
			alert("Your reimbursement failed to be submitted");
			window.location = "empReimbursements.html";
		}
	});

	return false;
}

function employeeLogin() {

	console.log('EmployeeLoggin');
	event.preventDefault();
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var formData = "username=" + username + "&password=" + password;

	var url = "http://localhost:8081/revature-project-one/empHome.do";

	console.log(formData, url);

	ajaxPOST(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			window.location = "employeeHomepage.html";
		} else {
			window.location = "employeeLogin.html";
			alert('Wrong username/password entered');
		}
	});
	return false;

}

function manLogin() {
	console.log('ManagerLogin');
	event.preventDefault();
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var formData = "username=" + username + "&password=" + password;
	var url = "http://localhost:8081/revature-project-one/manHome.do";
	ajaxPOST(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			window.location = "managerHomepage.html";
		} else {
			window.location = "managerLogin.html";
			alert('Wrong username/password entered');
		}
	});
	return false;
}

function resetPassword() {
	console.log('ResetPassword');
	event.preventDefault();
	var username = document.getElementById("username").value;
	var newPassword = document.getElementById("newPassword").value;
	var reEnterPassword = document.getElementById("reEnterPassword").value;

	var formData = "username=" + username + "&newPassword=" + newPassword
			+ "&reEnterPassword=" + reEnterPassword;
	var url = "http://localhost:8081/revature-project-one/resetPassword.do";

	ajaxPOST(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			alert('Your password has been changed');
			window.location = "employeeLogin.html";
		} else {
			alert('Wrong username/password entered');
			window.location = "resetPassword.html";
		}
	});
	return false;
}

function updateEmployee() {
	console.log('ResetPassword');
	event.preventDefault();
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var address = document.getElementById("address").value;
	var city = document.getElementById("city").value;
	var state = document.getElementById("state").value;

	var formData = "firstname=" + firstname + "&lastname=" + lastname
			+ "&address=" + address + "&city=" + city + "&state=" + state;
	var url = "http://localhost:8081/revature-project-one/updateEmpInfo.do";

	ajaxGET(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			alert('Your information has been updated');
			window.location = "empInformation.html";
		} else {
			alert('Information was not updated');
			window.location = "empInformation.html";

		}
	});
	return false;
}

function decideReimburse() {
	console.log('ResetPassword');
	event.preventDefault();
	var reimburseId = document.getElementById("reimburseId").value;
	var decision = document.querySelector('input[name = decisionName]:checked').value;

	var formData = "reimburseId=" + reimburseId + "&decisionName=" + decision;
	var url = "http://localhost:8081/revature-project-one/decideReimburse.do";

	ajaxGET(url, formData).then(function(response) {
		console.log(response);
		if (response === "true") {
			alert('Reimbursement ' + reimburseId + ' has been submitted');
			window.location = "managerReimbursements.html";
		} else {
			alert('The reimbursement did not process');
			window.location = "managerReimbursements.html";

		}
	});
	return false;
}
