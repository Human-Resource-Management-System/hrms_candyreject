<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.ApplyPermissions" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Permissions</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
        h2 {
            text-align: center;
        }
        
        table {
            border-collapse: collapse;
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
        }
        
        th, td {
            padding: 8px;
            text-align: left;
        }
        
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
        }
        
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        
         tr.accept {
            background: linear-gradient(to right, #d8f0d8, #87d687);
        }

        tr.reject {
            background: linear-gradient(to right, #ffd3d3, #f97a7a);
        }
        .no-permissions {
            font-style: italic;
            color: gray;
        }
    </style>
    
</head>
<body>
    <h2>Employee Permissions</h2><br><br>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <% List<ApplyPermissions> listOfPermission = (List<ApplyPermissions>) request.getAttribute("listOfPermission");
            if (listOfPermission != null) {
                for (ApplyPermissions permission : listOfPermission) { %>
                    <tr class="<%= permission.getEprq_status() %>">
                        <td><%= permission.getId().getId() %></td>
                        <td><%= permission.getCurrent_date() %></td>
                        <td><%= permission.getStart_time() %></td>
                        <td><%= permission.getEnd_time() %></td>
                        <td><%= permission.getEprq_status() %></td>
                    </tr>
                <% }
            } else { %>
                <tr>
                    <td colspan="5" class="no-permissions">No permissions found.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
