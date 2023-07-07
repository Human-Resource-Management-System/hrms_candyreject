<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="models.Candidate" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Candidates</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
        #tableContainer {
            width: 80%;
            margin: 20px auto;
        }
        
        #filterInput {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        
        #dataTable {
            width: 100%;
            border-collapse: collapse;
        }
        
        #dataTable th,
        #dataTable td {
            padding: 10px;
            border: 1px solid #ccc;
        }
        
        #dataTable th {
            background-color: #f2f2f2;
        }
        
        .view-link {
            display: inline-block;
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./js/OfferProvidedCandidates.js"></script>
</head>
<body>
    <div id="tableContainer">
        <input type="text" id="filterInput" placeholder="Search...">

        <table id="dataTable">
            <thead>
                <tr>
                    <th>Candidate ID</th>
                    <th>Candidate First Name</th>
                    <th>Candidate Last Name</th>
                    <th>Candidate Register Date</th>
                    <th>Candidate Status</th>
                    <th>View Candidate</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Candidate> candidates = (List<Candidate>) request.getAttribute("candidates");
                if (candidates != null) {
                    for (Candidate candidate : candidates) { %>
                        <tr>
                            <td><%= candidate.getCandId() %></td>
                            <td><%= candidate.getCandFirstName() %></td>
                            <td><%= candidate.getCandLastName() %></td>
                            <td><%= candidate.getCandRDate() %></td>
                            <td><%= candidate.getCandStatus() %></td>
                            <td><a class="view-link" href="#" onclick="loadContent('<%= candidate.getCandId() %>')">View</a></td>
                        </tr>
                    <% }
                } %>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</body>
</html>
