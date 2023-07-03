<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="models.EmployeeRefDocuments" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="./css/emprefdoclist.css">
            <script src="./js/emprefdoclist.js"></script>
   
</head>
<body>
  <h1>Reference Documents</h1>
  <div class="category-select">
    <label for="category">Select Category:</label>
    <select id="category" name="category" onchange="filterDocumentsByCategory()">
      <option value="all">All Categories</option>
      <option value="employee-handbook">Employee Handbook</option>
      <option value="code-of-conduct">Code of Conduct</option>
      <option value="Anti-Harrasement-Policy">Anti Harassment Policy</option>
      <option value="IT-Security-Policy">IT Security Policy</option>
      <option value="Travel-and-Expense-Policy">Travel and Expense Policy</option>
    </select>
  </div>

<%
  List<EmployeeRefDocuments> document = (List<EmployeeRefDocuments>) request.getAttribute("refdocs");
%>

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
          </div>
        </div>
      <% } 
    } %>
  </div>
  

</body>

</html>
