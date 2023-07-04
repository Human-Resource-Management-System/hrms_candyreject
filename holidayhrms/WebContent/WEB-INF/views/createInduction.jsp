<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.HrmsEmploymentOffer" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="models.input.output.OfferDiffModel" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Induction</title>
   
	 <link rel="stylesheet" type="text/css" href="./css/createInduction.css">

   
</head>
<body>
    <div class="container">
        <h1>Create Induction</h1>
        
            <%
                // Create a SimpleDateFormat object with the desired date format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Get the current date
                Date currentDate = new Date();

                // Format the current date as a string
                String IndcDate = dateFormat.format(currentDate);
                
                int IndcProcessedAusrId = 123;
            %>
		<h4>Induction Date: <%= IndcDate %></h2>
		<h4>Induction Date: <%= IndcProcessedAusrId %></h3>
      
        <form action="inductionsave" method="post">
<input type="hidden" id="IndcDate" name=IndcDate value="<%= IndcDate %>">
<input type="hidden" id="IndcProcessedAusrId" name="IndcProcessedAusrId" value="<%= IndcProcessedAusrId %>">
  <label for="IndcEmofId">Employee Offers:</label>
<select id="IndcEmofId" name="IndcEmofId" multiple required size="8" style="width: 400px;">
    <% List<OfferDiffModel> offerDiffList = (List<OfferDiffModel>) request.getAttribute("diffmodel"); %>
    <% if (offerDiffList != null && !offerDiffList.isEmpty()) { %>
        <% for (OfferDiffModel offerDiff : offerDiffList) { %>
            <option value="<%= offerDiff.getOfferId() %>" data-status="<%= offerDiff.getStatus() %>" >
                <%= offerDiff.getOfferId() %> ( Status :  <%= offerDiff.getStatus()%>)
            </option>
        <% } %>
    <% } %>
</select>
            <input type="submit" value="Save">
        </form>
    </div>
   
</body>
</html>