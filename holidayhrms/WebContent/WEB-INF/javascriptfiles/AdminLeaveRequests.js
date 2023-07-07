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
	document.getElementById("startDate").value = startLeaveDate;
	document.getElementById("endDate").value = endLeaveDate;
	document.getElementById("reason").value = reason;
	document.getElementById("leaveId").value = leaveId;

	// Close the modal when the close button is clicked
	var closeButton = document.getElementsByClassName("close")[0];
	closeButton.onclick = function() {
		modal.style.display = "none";
	};
}

function showRejectModal(employeeId, leaveId) {
	 var modal = $("#rejectModal");
    var modalContent = $("#rejectModalContent");

    // Display the modal
    modal.show();

    console.log(employeeId);
    console.log(leaveId);
    console.log("hello");

    $("#rejectemployeeId").val(employeeId);
    $("#rejectleaveId").val(leaveId);

    // Close the modal when the close button is clicked
    $(".close", modalContent).click(function() {
      modal.hide();
    });
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

function rejectLeave() {
	$.ajax({
		url: "rejectLeave",
		type: "POST",
		data: $("#rejectform").serialize(),
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