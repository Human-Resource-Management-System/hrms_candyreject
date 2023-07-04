<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Induction List</title>
	 <link rel="stylesheet" type="text/css" href="./css/inductions.css">

         
</head>
<body>
    <h1>Induction List</h1>
  
    <% List<Integer> inductions = (List<Integer>) request.getAttribute("inductions"); %>
    
    <% if (inductions != null && !inductions.isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Induction ID</th>
                </tr>
            </thead>
            <tbody>
                <% for (Integer induction : inductions) { %>
                    <tr>
                       <td>Induction :  <a class="view-link" href="getinductiondetails?id=<%= induction %>"><%= induction  %></a></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p class="no-inductions">No inductions found.</p>
    <% } %>
</body>
</body>
</html>
