<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="models.EmployeeRefDocuments" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
        h1 {
            text-align: center;
        }
        
        .category-select {
            margin-bottom: 20px;
            text-align: center;
        }
        
        .category-select label {
            font-weight: bold;
        }
        
        .category-select select {
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        
        .category {
            margin-bottom: 20px;
        }
        
        .category-title {
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .document {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
        
        .document-name {
            flex-grow: 1;
            font-style: italic;
        }
        
        .document-actions {
            margin-left: 10px;
        }
        
        .view-button, .delete-button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .delete-button{
    background-color: #f44336; /* Dark red color */
        
        }
        .add-button {
            text-align: center;
            margin-top: 20px;
        }
        
        .add-button a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        #box {
  margin: 0 auto;
  width: 80%;
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); /* Add box shadow */
}
        
    </style>
            <script src="./js/documentlist.js"></script>
   
</head>
<body>
  <h1>Reference Documents</h1>
  <br><br>
  <div class="category-select">
    <label for="category">Select Category:</label>
    <select id="category" name="category" onchange="filterDocumentsByCategory()">
      <option value="all">All Categories</option>
      <option value="employee-handbook">Employee Handbook</option>
      <option value="code-of-conduct">Code of Conduct</option>
      <option value="Anti-Harassment-Policy">Anti Harassment Policy</option>
      <option value="IT-Security-Policy">IT Security Policy</option>
      <option value="Travel-and-Expense-Policy">Travel and Expense Policy</option>
    </select>
  </div>

<%
  List<EmployeeRefDocuments> document = (List<EmployeeRefDocuments>) request.getAttribute("refdocs");
%>
<div id="box">
  <div class="category" id="employee-handbook">
    <div class="category-title">Employee Handbook</div>
    <% 
    String m = "Employee Handbook";
    for (EmployeeRefDocuments doc : document) {
      if (doc != null && doc.getCategory().equals(m)) { %>
        <div class="document">      
      <div class="document-name">
            <%= doc.getDocName() %>
          </div>
          <div class="document-actions">
            <button class="view-button" onclick="location.href='OpenDocument?docname=<%= doc.getDocName() %>'">View</button>
            <button class="delete-button" onclick="location.href='deleteReferenceDocument?docname=<%= doc.getDocName() %>'">Delete</button>
          </div>
        </div>
      <% } 
    } %>
  </div>

  <div class="category" id="code-of-conduct">
    <div class="category-title">Code of Conduct</div>
    <% 
    String n = "Code of Conduct";
    for (EmployeeRefDocuments doc : document) {
      if (doc != null && doc.getCategory().equals(n)) { %>
        <div class="document">
               <div class="document-name">
            <%= doc.getDocName() %>
          </div>
          <div class="document-actions">
            <button class="view-button" onclick="location.href='OpenDocument?docname=<%= doc.getDocName() %>'">View</button>
            <button class="delete-button" onclick="location.href='deleteReferenceDocument?docname=<%= doc.getDocName() %>'">Delete</button>
          </div>
        </div> <% } 
    } %>
  </div>

 <div class="category" id="Anti-Harassment-Policy"> 
  <div class="category-title">Anti Harassment Policy</div>
  <% 
  String p = "Anti Harassment Policy";
  for (EmployeeRefDocuments doc : document) {
    if (doc != null && doc.getCategory().trim().equals(p)) { %>
      <div class="document">
         <div class="document-name">
            <%= doc.getDocName() %>
          </div>
          <div class="document-actions">
            <button class="view-button" onclick="location.href='OpenDocument?docname=<%= doc.getDocName() %>'">View</button>
            <button class="delete-button" onclick="location.href='deleteReferenceDocument?docname=<%= doc.getDocName() %>'">Delete</button>
          </div>
        </div>
    <% } 
  } %>
</div>

  <div class="category" id="IT-Security-Policy">
    <div class="category-title">IT Security Policy</div>
    <% 
    String q = "IT Security Policy";
    for (EmployeeRefDocuments doc : document) {
      if (doc != null && doc.getCategory().equals(q)) { %>
        <div class="document">
          <div class="document-name">
            <%= doc.getDocName() %>
          </div>
          <div class="document-actions">
            <button class="view-button" onclick="location.href='OpenDocument?docname=<%= doc.getDocName() %>'">View</button>
            <button class="delete-button" onclick="location.href='deleteReferenceDocument?docname=<%= doc.getDocName() %>'">Delete</button>
          </div>
        </div>
      <% } 
    } %>
  </div>

  <div class="category" id="Travel-and-Expense-Policy">
    <div class="category-title">Travel and Expense Policy</div>
    <% 
    String r = "Travel and Expense Policy";
    for (EmployeeRefDocuments doc : document) {
      if (doc != null && doc.getCategory().equals(r)) { %>
        <div class="document">
          <div class="document-name">
            <%= doc.getDocName() %>
          </div>
          <div class="document-actions">
            <button class="view-button" onclick="location.href='OpenDocument?docname=<%= doc.getDocName() %>'">View</button>
            <button class="delete-button" onclick="location.href='deleteReferenceDocument?docname=<%= doc.getDocName() %>'">Delete</button>
          </div>
        </div>
      <% } 
    } %>
  </div>
  </div>

  <div class="add-button">
    <a href="addReferenceDocument">Add Document</a>
  </div>

 
</body>

</html>