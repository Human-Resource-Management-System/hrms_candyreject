<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="models.Holiday" %>
<%@ page import="java.util.List" %>
<c:set var="employee" value="${employee}" />


<!DOCTYPE html>
<html>
<head>
    <title>Employee Data</title>
    <style>
    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        text-align: left;
        padding: 8px;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .checkbox-cell {
        text-align: center;
    }
    
      #chandu {
  
            margin-top: 100px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
         #submitBtn {
        display: block;
        margin: 0 auto; /* Center align the button horizontally */
        margin-top: 10px; /* Add top margin as needed */
           background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        
    }
        
      
        
        
</style>
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <script>
        $(document).ready(function () {
        	
        	
        	if( ${jobGradeHolidays.jbgr_totalnoh - mandholidays} - ${count} == 0){
        		alert("Already Applied Your Opted Leaves");
        		window.location.reload();
        		
        	}
        	
            var maxSelection = ${jobGradeHolidays.jbgr_totalnoh} - ${mandholidays};

            $('input[name="selectedHolidays"]').on('change', function () {
                var checkedCount = $('input[name="selectedHolidays"]:checked').length;
                if (checkedCount > maxSelection) {
                    this.checked = false;
                }
            });

            $('#submitBtn').click(function () {
                var checkedCount = $('input[name="selectedHolidays"]:checked').length;
                if (checkedCount < maxSelection) {
                    alert('Please select ' + maxSelection + ' holidays.');
                    return false;
                }
                
                console.log($("#HolidayForm").serialize());
                var formData = $("#HolidayForm").serializeArray();
                formData.push({ name: "emplId", value: "${employee.emplId}" });
                $.ajax({
                	url:"submitOptionalHolidays",
                	type:"POST",
                	 data: formData,
                	success: function(response){
                		console.log(response);
                		alert(response);
                		window.location.reload();
                	},
                    error:function(error){
                    	console.log(error);
                    }   
                });
                
            });
        });
    </script>
</head>
<body>
    <!-- Add other employee details as needed -->
<div id="chandu">
    <h2>Holiday Information</h2>
    <p>Total Number of Holidays: ${jobGradeHolidays.jbgr_totalnoh}</p>
    <p>Total Number of Optional Holidays: ${jobGradeHolidays.jbgr_totalnoh - mandholidays}</p>
    <!-- Add other leave-related information as needed -->
<br><br>
    <form id="HolidayForm">
        <table id="dataTable">
            <thead>
                <tr>
                    <th>Year</th>
                    <th>Holiday Date</th>
                    <th>Holiday Title</th>
                    <th>Holiday Type</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Holiday> holidays = (List<Holiday>) request.getAttribute("holidays");
                if (holidays != null) {
                    for (Holiday holiday : holidays) { %>
                        <tr>
                            <td><%= holiday.getYear() %></td>
                            <td><%= holiday.gethday_date() %></td>
                            <td><%= holiday.gethday_title() %></td>
                            <td><%= holiday.gethday_type() %></td>
                            <td class="checkbox-cell">
                                <input type="checkbox" name="selectedHolidays" value="<%= holiday.getYear() %>|<%= holiday.gethday_date() %>">
                            </td>
                        </tr>
                    <% }
                } %>
            </tbody>
        </table>

    </form>
    </div>
        <input type="button" value="Submit" id="submitBtn">
  
</body>
</html>


