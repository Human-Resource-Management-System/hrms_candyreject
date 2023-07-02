<%@page import="models.HrmsJobGrade"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Job Grade Leaves</title>
<link rel="stylesheet" type="text/css" href="./css/jobGradeWiseLeaves.css">

<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script>
	function showEditModal(jobGradeId, totalLeaves, casualLeaves, sickLeaves,
			otherLeaves) {
		var modal = document.getElementById("editModal");
		var modalContent = document.getElementById("editModalContent");

		// Display the modal
		modal.style.display = "block";

		// Set the job grade details in the modal
		document.getElementById("jobGradeId").value = jobGradeId;
		document.getElementById("totalLeaves").value = totalLeaves;
		document.getElementById("casualLeaves").value = casualLeaves;
		document.getElementById("sickLeaves").value = sickLeaves;
		document.getElementById("otherLeaves").value = otherLeaves;

		// Close the modal when the close button is clicked
		var closeButton = document.getElementsByClassName("close")[1];
		closeButton.onclick = function() {
			modal.style.display = "none";
		};
	}

	function updateLeaves() {
		var modal = $("#editModal");
		var modalContent = $("#editModalContent");
		var formData = $("#editForm");
		
		$.ajax({
			url:"updatejobgradeleaves",
			method:"POST",
			data:formData.serialize(),
			success: function (response){
				console.log(response);
				alert("updated successfully");
				location.reload();
			},
			error:function (error){
				console.log(error);
				alert("something went wrong.please try again.");
			}
			
		});
		
		
	}

	function showAddModal() {
		var modal = document.getElementById("addModal");
		var modalContent = document.getElementById("addModalContent");

		// Display the modal
		modal.style.display = "block";

		// Close the modal when the close button is clicked
		var closeButton = document.getElementsByClassName("close")[0];
		closeButton.onclick = function() {
			modal.style.display = "none";
		};
	}

	function addRow() {
		var modal = $("#addModal");
		var modalContent = $("#addModalContent");
		var formData = $("#addForm");
		
		$.ajax({
			url:"addjobgradeleaves",
			method:"POST",
			data:formData.serialize(),
			success: function (response){
				console.log(response);
				alert("added successfully.");
				location.reload();
			},
			error:function (error){
				console.log(error);
				alert("something went wrong.please try again.");
			}
			
		});
		
	}
</script>
</head>
<body>
	<h1>Job Grade Leaves</h1>
	<%@ page import="java.util.List, java.util.ArrayList"%>
	<%@ page import="models.input.output.JobGradeLeavesOutModel,models.HrmsJobGrade"%>
	<table>
		<tr>
			<th>Job Grade ID</th>
			<th>Total Leaves</th>
			<th>Casual Leaves</th>
			<th>Sick Leaves</th>
			<th>Other Leaves</th>
			<th></th>
		</tr>

		<%
		List<JobGradeLeavesOutModel> jobGradeLeaves = (List<JobGradeLeavesOutModel>) request.getAttribute("jobgradeleaves");

		for (JobGradeLeavesOutModel jobGrade : jobGradeLeaves) {
		%>
		<tr>
			<td><%=jobGrade.getJobGradeId()%></td>
			<td><%=jobGrade.getTotalLeaves()%></td>
			<td><%=jobGrade.getCasualLeaves()%></td>
			<td><%=jobGrade.getSickLeaves()%></td>
			<td><%=jobGrade.getOtherLeaves()%></td>
			<td>
				<button class="button edit" onclick="showEditModal('<%=jobGrade.getJobGradeId()%>', <%=jobGrade.getTotalLeaves()%>,<%=jobGrade.getCasualLeaves()%>, <%=jobGrade.getSickLeaves()%>, <%=jobGrade.getOtherLeaves()%>)">Edit</button>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	
	<div class="button-container"> <!-- Container for the Add button -->
        <button class="button add" onclick="showAddModal()">Add</button>
    </div>


	<!-- The add modal -->
	<div id="addModal" class="modal">
		<div id="addModalContent" class="modal-content">
			<span class="close">&times;</span>
			<h2>Add Job Grade Leaves</h2>
			<form id="addForm">
				<div class="form-row">
					<select id="addJobGradeId" name="jobGradeId">
					<option value="">Select Job Grade ID</option>
					<%
					List<HrmsJobGrade> jobgrades = (List<HrmsJobGrade>) request.getAttribute("jobgradeinfo");
						for (HrmsJobGrade jobGrade : jobgrades) {
					%>
					<option value="<%= jobGrade.getId() %>"><%= jobGrade.getId() %></option>
					<%
						}
					%>
				</select>
				</div>

				<div class="form-row">
					<label for="addTotalLeaves">Total Leaves:</label> <input
						type="number" id="addTotalLeaves" name="totalLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="addCasualLeaves">Casual Leaves:</label> <input
						type="number" id="addCasualLeaves" name="casualLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="addSickLeaves">Sick Leaves:</label> <input
						type="number" id="addSickLeaves" name="sickLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="addOtherLeaves">Other Leaves:</label> <input
						type="number" id="addOtherLeaves" name="otherLeaves" min="0">
				</div>

				<div class="form-row">
					<input type="button" class="button" value="Add" onclick="addRow()">
				</div>
			</form>
		</div>
	</div>


	<!-- The edit modal -->
	<div id="editModal" class="modal">
		<div id="editModalContent" class="modal-content">
			<span class="close">&times;</span>
			<h2>Edit Job Grade Leaves</h2>
			<form id="editForm">
				<div class="form-row">
					<label for="jobGradeId">Job Grade ID:</label> <input type="text"
						id="jobGradeId" name="jobGradeId" readonly>
				</div>

				<div class="form-row">
					<label for="totalLeaves">Total Leaves:</label> <input type="number"
						id="totalLeaves" name="totalLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="casualLeaves">Casual Leaves:</label> <input
						type="number" id="casualLeaves" name="casualLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="sickLeaves">Sick Leaves:</label> <input type="number"
						id="sickLeaves" name="sickLeaves" min="0">
				</div>

				<div class="form-row">
					<label for="otherLeaves">Other Leaves:</label> <input type="number"
						id="otherLeaves" name="otherLeaves" min="0">
				</div>

				<div class="form-row">
					<input type="button" class="button" value="Update"
						onclick="updateLeaves()">
				</div>
			</form>
		</div>
	</div>
</body>
</html>