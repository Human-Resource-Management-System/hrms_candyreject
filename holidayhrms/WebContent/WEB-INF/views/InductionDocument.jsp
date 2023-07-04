<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employment Induction Documents</title>

	 <link rel="stylesheet" type="text/css" href="./css/InductionDocument.css">
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="./js/InductionDocument.js"></script>
</head>
<body>
    <h1>Employment Induction Documents</h1>
    
     <table>
        <thead>
            <tr>
                <th>employee id</th>
                <th>Doc_type</th>
                <th>DOC_path</th>
                <th>verification Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${doc}" var="document">
                <tr>
                    <td>${document.emplid}</td>
                    <td>${document.emid_idty_id}</td>
                    <td>${document.documentData}</td>
                    <td>${document.verified}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <button onclick="showAddDocumentForm()">Add Document</button>
    
    <div id="addDocumentForm" class="add-document-form">
        <h2>Add Document</h2>
        <form action="add" method="get" >

            <label for="employmentOffer">Employment Offer ID:</label>
            <input type="text" id="employmentOffer" name="employmentOfferId" required><br>

            <label for="documentType">Document Type:</label>
            <input type="text" id="documentType" name="documentTypeId" required><br>

            <label for="documentData">Document Data:</label>
            <input type="file" id="documentData" name="documentData" required><br>
  
            <label for="processedUser">Processed User:</label>
            <input type="text" id="processedUser" name="processedUserId" required><br>

            <label for="verified">Verified:</label>
            <input type="text" id="verified" name="verified" required><br>

            <button type="submit">Insert</button>
        </form>
    </div>
</body>
</html>
