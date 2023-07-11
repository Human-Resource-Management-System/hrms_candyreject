<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Candidate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rejected Candidates</title>
    <style>
    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 8px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f2f2f2;
    }
     tr:hover {
        background-color: #e6eeff;
    }
</style>
</head>
<body>
    <h1>Rejected Candidates</h1>
    <table>
        <thead>
            <tr>
                <th>Candidate ID</th>
                <th>First Name</th>
                <th>Gender</th>
                 <th>Email</th>
               <th>Mobile Number</th>
            </tr>
        </thead>
        <tbody>
          <% List<Candidate> rejectedList = (List<Candidate>) request.getAttribute("rejectedList"); %>
            <% for (Candidate candidate : rejectedList) { %>
                <tr>
                    <td><%= candidate.getCandId() %></td>
                    <td><%= candidate.getCandFirstName() %></td>
                    <td><%= candidate.getCandGender() %></td>
                     <td><%= candidate.getCandEmail() %></td>
                       <td><%= candidate.getCandMobile() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
