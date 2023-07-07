<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="models.Candidate" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Candidates</title>
 <link rel="stylesheet" type="text/css" href="./css/offerCandidates.css">
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="./js/offerCandidates.js"></script>
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
                    <th>Generate Offer</th>
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
                            <td><a class="view-link" href="viewcandidate?id=<%= candidate.getCandId() %>">View</a></td>
                            
            			
            				
<td>
    <form action="get-candidate-details" method="GET">
        <input type="hidden" name="id" value="<%= candidate.getCandId() %>">
        <button type="submit" class="view-button" style="background-color: #4CAF50; color: #fff;">generate offer letter</button>
    </form>
</td>
            				
            				
                        </tr>
                        
                    <% }
                } %>
            </tbody>
        </table>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
  

</body>
</html>