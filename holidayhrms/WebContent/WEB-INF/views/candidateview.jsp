<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.input.output.CandidateIO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Candidate List</title>
    <style>
      
        h1 {
            color: #333;
            margin-bottom: 30px;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
            color: #333;
            text-transform: uppercase;
        }


        .no-employees {
            margin-top: 20px;
            color: #888;
            text-align: center;
        }

        .table-container {
            overflow-x: auto;
        }

        .table-container::-webkit-scrollbar {
            height: 8px;
            background-color: #f4f4f4;
        }

        .table-container::-webkit-scrollbar-thumb {
            background-color: #888;
            border-radius: 4px;
        }

        .table-container::-webkit-scrollbar-track {
            background-color: #f4f4f4;
        }

        /* Custom styles */
        .view-link {
            color: #007bff;
            text-decoration: none;
        }

        .view-link:hover {
            text-decoration: underline;
        }
         .center {
            text-align: center;
            margin-top: 20px;
        }
         .center button {
        padding: 10px 20px;
        font-size: 16px;
        border: none;
      background-color: #333;
        color: #fff;
        cursor: pointer;
        margin-right: 10px;
    }

        
         .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 10% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        max-width: 800px;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }
    </style>
     <script>
        // Function to open the modal popup
        function openModal(employeeId) {
            var modal = document.getElementById("myModal");
            modal.style.display = "block";
            // Set the URL to fetch employee details
            var url = "viewcandidate?id=" + employeeId;
            // Use AJAX to fetch the employee details and update the modal content
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var employeeDetails = this.responseText;
                    document.getElementById("modalContent").innerHTML = employeeDetails;
                }
            };
            xhttp.open("GET", url, true);
            xhttp.send();
        }                 

        
        // Function to close the modal popup
        function closeModal() {
            var modal = document.getElementById("myModal");
            modal.style.display = "none";
        }

        // Event listener to close the modal when clicking outside of it
        window.onclick = function(event) {
            var modal = document.getElementById("myModal");
            if (event.target == modal) {
                modal.style.display = "none";
            }
        };
        
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
    <h1>Candidate List</h1>
    <%-- Retrieve the list of candidates from the model --%>
    <% List<CandidateIO> candidates = (List<CandidateIO>) request.getAttribute("candidates"); %>
    <%-- Check if the list is not null and not empty --%>
    <% if (candidates != null && !candidates.isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Candidate ID</th>
                    <th>First Name</th>
                    <th>Middle Name</th>
                    <th>Last Name</th>
                    <th>Registered Date</th>
                    <th>Gender</th>
                    <th>Date of Birth</th>
                    <th>Email</th>
                    <th>Mobile</th>
                    <th>Status</th>                   
                </tr>
            </thead>
            <tbody>
                <%-- Iterate over the list of candidates and display the data --%>
                <% for (CandidateIO candidate : candidates) { %>
                    <tr onclick="openModal('<%= candidate.getCandId() %>')">
                        <td><%= candidate.getCandId() %></td>
                        <td><%= candidate.getCandFirstName() %></td>
                        <td><%= candidate.getCandMiddleName() %></td>
                        <td><%= candidate.getCandLastName() %></td>
                        <td><%= candidate.getCandRDate() %></td>
                        <td><%= candidate.getCandGender() %></td>
                        <td><%= candidate.getCandDOB() %></td>
                        <td><%= candidate.getCandEmail() %></td>
                        <td><%= candidate.getCandMobile() %></td>
                        <td><%= candidate.getCandStatus() %></td>                      
                    </tr>
                <% } %>
            </tbody>
        </table>
        
        <div class="center">
            <%-- Generate pagination links based on the total number of pages --%>
            <% int totalPages = (int) request.getAttribute("totalPages"); %>
            <% int currentPage = (int) request.getAttribute("currentPage"); %>
            <% for (int i = 1; i <= totalPages; i++) { %>
                <% if (i == currentPage) { %>
                    <strong><%= i %></strong>
                <% } else { %>
                    <a href="?page=<%= i %>" class="page-link"><%= i %></a>
                <% } %>
            <% } %>
            <br><br>
           <button class="addcand" onclick="addCandidate()">Add</button>

        </div>
        
        <!-- Modal popup content -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <div id="modalContent"></div>
            </div>
        </div>
        
    <% } else { %>
        <p class="no-employees">No candidates found.</p>
    <% } %>
</body>
</html>