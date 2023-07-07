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
      <style>


    h2 {
        text-align: center;
        margin-top: 0;
    }

    p {
        margin-bottom: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
        margin-bottom: 20px;
    }

    th, td {
        text-align: left;
        padding: 8px;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .checkbox-cell {
        text-align: center;
    }
    
  

    #submitBtn {
   display: block;
        margin: 0 auto; /* Center align the button horizontally */
        margin-top: 10px; /* Add top margin as needed */
           background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
    }
    
    #label {
    display: block;
    text-align: center !important;
 font-style: italic;
    font-weight: bold;
    margin: 0 auto;}
    </style>
    
    


</head>
<body>
    <div class="container">
        <h1 align="center">Create Induction</h1>
          <!-- Add the spinner element -->
        <div id="spinner" class="spinner"></div>
        <%
            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Get the current date
            Date currentDate = new Date();

            // Format the current date as a string
            String IndcDate = dateFormat.format(currentDate);

            int IndcProcessedAusrId = 123;
        %>
        Induction Date: <%= IndcDate %>
        <br>
        Admin: Sowjanya

        <form action="inductionsave" method="post">
            <input type="hidden" id="IndcDate" name="IndcDate" value="<%= IndcDate %>">
            <input type="hidden" id="IndcProcessedAusrId" name="IndcProcessedAusrId" value="<%= IndcProcessedAusrId %>">
            <label for="IndcEmofId" id="label">Employee Offers</label>
            <br><br>

            <% List<OfferDiffModel> offerDiffList = (List<OfferDiffModel>) request.getAttribute("diffmodel"); %>
            <% if (offerDiffList != null && !offerDiffList.isEmpty()) { %>
                <table style="border-collapse: collapse; border: 1px solid black;">
                    <thead>
                        <tr>
                            <th>Offer ID</th>
                            <th>Name</th>
                            <th>Status</th>
                            <th>Select</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (OfferDiffModel offerDiff : offerDiffList) { %>
                            <tr onclick="selectRow(this)">
                                <td><%= offerDiff.getOfferId() %></td>
                                <td><%= offerDiff.getName() %></td>
                                <td><%= offerDiff.getStatus() %></td>
                                <td>
<input type="checkbox" name="IndcEmofId" value="<%= offerDiff.getOfferId() %>">
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>

                <input id = "submitBtn" type="submit" value="Save">

            <% } %>
        </form>
    </div>
   
      
<!-- JavaScript code to show the spinner during page load -->
<script>
    window.addEventListener('load', function() {
        var spinner = document.getElementById('spinner');
        spinner.style.display = 'block'; // Show the spinner
    });
</script>
</body>
</html>
