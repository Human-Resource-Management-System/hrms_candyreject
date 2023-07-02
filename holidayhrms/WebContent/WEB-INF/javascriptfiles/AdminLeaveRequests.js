function showLeaveModal(employeeId, employeeName, leaveType,
	startLeaveDate, endLeaveDate, reason, leaveId) {
	var modal = document.getElementById("leaveModal");
	var modalContent = document.getElementById("leaveModalContent");

	// Display the modal
	modal.style.display = "block";

	// Set the leave request details in the modal
	document.getElementById("employeeId").value = employeeId;
	document.getElementById("employeeName").value = employeeName;
	document.getElementById("leaveType").value = leaveType;
	document.getElementById("startDate").min = startLeaveDate;
	document.getElementById("startDate").max = endLeaveDate;
	document.getElementById("startDate").value = startLeaveDate;
	document.getElementById("endDate").min = startLeaveDate;
	document.getElementById("endDate").max = endLeaveDate;
	document.getElementById("endDate").value = endLeaveDate;
	document.getElementById("reason").value = reason;
	document.getElementById("leaveId").value = leaveId;

	// Close the modal when the close button is clicked
	var closeButton = document.getElementsByClassName("close")[0];
	closeButton.onclick = function() {
		modal.style.display = "none";
	};
}

function acceptLeave() {
	var modal = document.getElementById("leaveModal");
	console.log($("#leaveForm").serialize());
	$.ajax({
		url: "acceptLeave",
		type: "POST",
		data: $("#leaveForm").serialize(),
		success: function(response) {
			console.log(response);
			modal.style.display = "none";
			alert("Leave Accepted.");
			location.reload();
		},
		error: function(error) {
			alert("Something went wrong.");
			console.log(error);
		}
	});
}

function rejectLeave(id, index) {
	$.ajax({
		url: "rejectLeave",
		type: "POST",
		data: {
			"empId": id,
			"leaveRequestIndex": index
		},
		success: function(response) {
			console.log(response);
			alert("leave rejected.");
			location.reload();
		},
		error: function(error) {
			alert = ("Something went wrong.");
			console.log(error);
		}
	});

}