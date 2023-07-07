<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Leave Requests</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"
	integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
	crossorigin="anonymous"></script>


<link rel="stylesheet" type="text/css"
	href="./css/AdminLeaveRequests.css">
<script src="./js/AdminLeaveRequests.js"></script>
</head>
<body>
	<%@ page
		import="java.util.List,java.util.ArrayList,models.EmployeeLeaveModel"%>


	<h1>Leave Requests</h1>
	<br>
	<table>
		<!--  type safety -->
		<%
		List<EmployeeLeaveModel> data = new ArrayList<>();
		Object attribute = request.getAttribute("data");
		if (attribute instanceof List<?>) {
			List<?> attributeList = (List<?>) attribute;
			for (Object item : attributeList) {
				if (item instanceof EmployeeLeaveModel) {
			EmployeeLeaveModel leaveModel = (EmployeeLeaveModel) item;
			data.add(leaveModel);
				}
			}
		}
		
		%>
		<%
		 if(data.size()!=0){
			 %>
			 <tr>
			<th>Employee ID</th>
			<th>Employee Name</th>
			<th>Leave Type</th>
			<th>Start Leave Date</th>
			<th>End Leave Date</th>
			<th>Reason</th>
			<th>Action</th>
		</tr><% 
		for (int i = 0; i < data.size(); i++) {
			EmployeeLeaveModel model = data.get(i);
		%>
		<tr>
			<td><%=model.getEmpId()%></td>
			<td><%=model.getName()%></td>
			<td><%=model.getLeaveType()%></td>
			<td><%=model.getLeaveStartDate()%></td>
			<td><%=model.getLeaveEndDate()%></td>
			<td><%=model.getReason()%></td>
			<td>
				<div class="action-buttons">
					<button class="button"
						onclick="showLeaveModal('<%=model.getEmpId()%>','<%=model.getName()%>','<%=model.getLeaveType()%>','<%=model.getLeaveStartDate()%>','<%=model.getLeaveEndDate()%>','<%=model.getReason()%>','<%=model.getLeaveRequestIndex()%>')">Accept</button>
					<button class="button reject"
						onclick="showRejectModal('<%=model.getEmpId()%>','<%=model.getLeaveRequestIndex()%>')">Reject</button>
				</div>
			</td>
		</tr>
		<%
		}  } else {
		%>
		<h3 style="text-align:center">No Leave Requests Found</h3>
		<%} %>
	</table>

	<!-- The modal -->
	<div id="leaveModal" class="modal">
		<div id="leaveModalContent" class="modal-content">
			<span class="close">&times;</span>
			<h2>Leave Request Details</h2>
			<form id="leaveForm">
				<div class="form-row">
					<label for="employeeId">Employee ID:</label> <input type="text"
						id="employeeId" name="employeeId" readonly>
				</div>

				<div class="form-row">
					<label for="employeeName">Employee Name:</label> <input type="text"
						id="employeeName" name="name" readonly>
				</div>

				<div class="form-row">
					<label for="leaveId">Leave Request ID:</label> <input type="text"
						id="leaveId" name="leaveRequestIndex" readonly>
				</div>

				<div class="form-row">
					<label for="leaveType">Leave Type:</label> <input type="text"
						id="leaveType" name="leaveType" readonly>
				</div>

				<div class="form-row">
					<label for="startDate">Start Leave Date:</label> <input type="date"
						id="startDate" name="leaveStartDate" readonly>
				</div>

				<div class="form-row">
					<label for="endDate">End Leave Date:</label> <input type="date"
						id="endDate" name="leaveEndDate" readonly>
				</div>

				<div class="form-row">
					<label for="reason">Reason:</label> <input type="text" id="reason"
						name="reason" readonly>
				</div>

				<div class="form-row">
					<label for="remarks">Remarks:</label>
					<textarea id="remarks" name="remarks"></textarea>
				</div>

				<div class="form-row">
					<input type="button" class="button" value="Accept"
						onclick="acceptLeave()">
				</div>
			</form>

		</div>
	</div>

	<!-- reject modal -->

	<div id="rejectModal" class="modal">
		<div id="rejectModalContent" class="modal-content">
		<span class="close">&times;</span>
			<form id="rejectform">
				<div class="form-row">
					<label for=""rejectemployeeId">Employee ID:</label> <input type="text"
						id="rejectemployeeId" name="employeeId" readonly>
				</div>
				<div class="form-row">
					<label for="rejectleaveId">Leave Request ID:</label> <input type="text"
						id="rejectleaveId" name="leaveRequestIndex" readonly>
				</div>
				<div class="form-row">
					<label for="rejectremarks">Remarks:</label>
					<textarea id="rejectremarks" name="remarks"></textarea>
				</div>
				<div class="form-row">
					<input type="button" class="button" value="Reject"
						onclick="rejectLeave()">
				</div>
			</form>
		</div>
	</div>
</body>
</html>