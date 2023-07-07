<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employment Induction Documents</title>
  <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f4f4f4;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        .pagination {
            margin-top: 20px;
            text-align: center;
        }

        .pagination ul {
            list-style-type: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .pagination li {
            margin: 0 5px;
        }

        .pagination a {
            padding: 5px 10px;
            text-decoration: none;
            color: #333;
            background-color: #f2f2f2;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }

        .add-button-container {
            text-align: center;
            margin-top: 20px;
        }

        .add-button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
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
            background-color: #f4f4f4;
    margin: 10% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 30%;
    border-radius: 5px;
  
        }

        .modal-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .modal-header h2 {
            margin: 0;
        }

        .modal-header .close {
            background-color: transparent;
            border: none;
            font-size: 24px;
            cursor: pointer;
        }

        .modal-body {
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 10px;
        }

        .form-group label {
            font-weight: bold;
        }

        .form-group input[type="text"],
       
        .form-group input[type="file"] {
            display: block;
        }

        .form-group input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .show-form {
            display: block;
        }
        #insert{ display: block;
        margin: 0 auto; /* Center align the button horizontally */
        margin-top: 10px; /* Add top margin as needed */
           background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);}
        
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>    
    function showAddDocumentForm() {
        var form = document.getElementById("addDocumentForm");
        form.classList.toggle("show-form");
    }</script>

    <script>
        function showAddDocumentForm() {
            var modal = document.getElementById("addDocumentModal");
            modal.style.display = "block";
        }

        function hideAddDocumentForm() {
            var modal = document.getElementById("addDocumentModal");
            modal.style.display = "none";
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Employment Induction Documents</h1>

        <table>
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Document Type</th>
                     <th>Title</th>
                    <th>Verification Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${doc}" var="document">
                    <tr>
                        <td>${document.emplid}</td>
                        <td>${document.emid_idty_id}</td>
                         <td>${document.title}</td>
                        <td>Submitted</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <nav class="pagination">
            <ul>
                <li><a href="#">Previous</a></li>
                <li><a href="#" class="active">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">Next</a></li>
            </ul>
        </nav>

        <!-- ADD Button -->
        <div class="add-button-container">
            <button class="add-button" onclick="showAddDocumentForm()">Add Document</button>
        </div>

        <div id="addDocumentModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Add Document</h2>
                    <button type="button" class="close" onclick="hideAddDocumentForm()">&times;</button>
                </div>
                <div class="modal-body">
                    <form action="add" method="get" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="employmentOffer">Employment Offer ID:</label>
                            <input type="text" id="employmentOffer" name="employmentOfferId" required>
                        </div>
                        <div class="form-group">
                            <label for="documentType">Document Type:</label>
                            <input type="text" id="documentType" name="documentTypeId" required>
                        </div>
                        <div class="form-group">
                            <label for="documentData">Document Data:</label>
                            <input type="file" id="documentData" name="documentData[]" required>
                    
                        <div class="form-group">
                            <label for="processedUser">Processed User:</label>
                            <input type="text" id="processedUser" name="processedUserId" required>
                        </div>
                        <div class="form-group">
                            <label for="verified">Verified:</label>
                            <input type="text" id="verified" name="verified" required>
                        </div>
                        <button type="submit" id="insert">Insert</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
    function addFileInput() {
        var container = document.getElementById("fileInputsContainer");
        var input = document.createElement("input");
        input.type = "file";
        input.name = "documentData[]";
        container.appendChild(input);
    }
</script>
</body>
</html>
