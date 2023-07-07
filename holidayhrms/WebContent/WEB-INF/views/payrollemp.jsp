<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter Candidate ID</title>
  
 <link rel="stylesheet" type="text/css" href="./css/payRollEmp.css">
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script>
            function payroll() {
            	  // Show the loading spinner
            	  $("#msg").html('<div class="loading-spinner"></div>');

            	  $.ajax({
            	    type: "POST",
            	    url: "getpayslip",
            	    data: $("#payrollemp").serialize(),
            	    success: function(response) {
            	      // Hide the loading spinner
            	      $("#msg").empty();

            	      var containerDiv = $(".main");
            	      containerDiv.html(response);
            	    },
            	    error: function() {
            	      // Hide the loading spinner
            	      $("#msg").empty();

            	      $("#msg").text("Error Occurred");
            	      alert("Error occurred. Please try again later.");
            	    }
            	  });
            	}
            
            </script>
  
  
    
</head>
<body>
    <h1>Enter Employee ID</h1><br>
    <form id="payrollemp" method="post">
        <label for="employeeeId">Employee ID:</label>
        <input type="text" id="empl_id" name="empl_id" required><br><br>
        <label for="month">Month:</label>
    	<select id="month" name="month">
        <option value="1">January</option>
        <option value="2">February</option>
        <option value="3">March</option>
        <option value="4">April</option>
        <option value="5">May</option>
        <option value="6">June</option>
        <option value="7">July</option>
        <option value="8">August</option>
        <option value="9">September</option>
        <option value="10">October</option>
        <option value="11">November</option>
        <option value="12">December</option>
    </select><br><br>
        <input type="button" onclick="payroll();" value="Generate Pay Slip" class="payroll-button">
    </form>
    <center>
    <div id="msg"></div>
    </center>
     </div>
     
    
  
</body>
</html>