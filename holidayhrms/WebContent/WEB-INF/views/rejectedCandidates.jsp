<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.input.output.CandidateDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate List</title>
    <style>
    table {
        width: 50%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    th, td {
        padding: 10px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
        font-weight: bold;
    }

    tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    tr:hover {
        background-color: #e6eeff;
    }

    input[type="submit"] {
        background-color: #ff9999;
        color: white;
        border: none;
        padding: 5px 10px;
        cursor: pointer;
        border-radius: 4px;
    }

    input[type="submit"]:hover {
        background-color: #ff4d4d;
    }
</style>
    
</head>
<body>
    <h1>Candidate List</h1>
    <table>
        <thead>
            <tr>
                <th>Candidate ID</th>
                <th>First Name</th>
                <th>Reject</th>
            </tr>
        </thead>
        <tbody>
          <% List<CandidateDTO> rejected = (List<CandidateDTO>) request.getAttribute("rejected"); %>
            <% for (CandidateDTO candidate : rejected) { %>
                <tr>
                    <td><%= candidate.getCandidateId() %></td>
                    <td><%= candidate.getCandidateFirstName() %></td>
                    <td>
                        <form method="POST" action="reject">
                            <input type="hidden" name="candidateId" value="<%= candidate.getCandidateId() %>">
                            <input type="submit" value="Reject">
                        </form>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
