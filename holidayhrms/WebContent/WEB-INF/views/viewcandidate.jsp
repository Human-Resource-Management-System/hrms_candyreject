<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.input.output.CandidateIO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Candidate Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
        h1 {
            text-align: center;
        }
        
        h2 {
            margin-bottom: 10px;
        }
        
        p {
            margin: 5px 0;
        }
        
        .error-message {
            color: red;
            font-weight: bold;
        }
          button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin-top: 20px;
            cursor: pointer;
        }
        
        button {
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
        
        button:hover {
            background-color: #45a049;
        }
    </style>

      
</head>
<body>
    <h1>Candidate Details</h1><br>
    <%
    CandidateIO candidate = (CandidateIO) request.getAttribute("candidate");
        String error = (String) request.getAttribute("error");
        
        if (candidate != null) {
    %>
            <h2>Candidate ID: <%= candidate.getCandId() %></h2>
           <p><strong>First Name:</strong> <%= candidate.getCandFirstName() %></p>
<p><strong>Middle Name:</strong> <%= candidate.getCandMiddleName() %></p>
<p><strong>Last Name:</strong> <%= candidate.getCandLastName() %></p>
<p><strong>Registration Date:</strong> <%= candidate.getCandRDate() %></p>
<p><strong>Gender:</strong> <%= candidate.getCandGender() %></p>
<p><strong>Date of Birth:</strong> <%= candidate.getCandDOB() %></p>
<p><strong>Email:</strong> <%= candidate.getCandEmail() %></p>
<p><strong>Mobile Number:</strong> <%= candidate.getCandMobile() %></p>
<p><strong>Address:</strong> <%= candidate.getCandAddress() %></p>
<p><strong>Status:</strong> <%= candidate.getCandStatus() %></p>

    <%
        } else if (error != null) {
    %>
            <p class="error-message"><%= error %></p>
    <%
        }
    %>
  
</body>
</html>
