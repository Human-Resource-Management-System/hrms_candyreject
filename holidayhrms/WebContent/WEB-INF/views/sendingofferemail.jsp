<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="models.Candidate" %>
<%@page import="models.OfferModel" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Offer Letter</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }
        
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        
        p {
            margin-bottom: 10px;
        }
        
        ul {
            list-style-type: disc;
            margin-left: 20px;
        }
        
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        
        .send-button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 4px;
        }
               
 .popup{
	width:400px;
	background:#fff;
	border-radius:6px;
	position:absolute;
	top:0;
	left:50%;
	transform:translate(-50%,-50%) scale(0.1);
	text-align:center;
	padding:0 30px 30px;
	color:#333;	
	visibility: hidden;
	transition:transform 0.4s,top 0.4s;
}

.open-popup{
	visibility: visible;
	top:50%;
	transform:translate(-50%,-50%) scale(1);
}

.popup img{
	width:100px;
	margin-top:-50px;
	border-radius:50%;
	box-shadow: 0 2px 5px rgba(0,0,0,0.2);
}

.popup h2{
	font-size:38px;
	font-weight:500;
	margin:30px 0px 10px;
	
}

.popup button{
	width:100%;
	margin-top:50px;
	padding:10px 0;
	background:#6fd649;
	color:#fff;
	border:0;
	outline:none;
	font-size:18px;
	border-radius:4px;
	cursor:pointer;
	box-shadow:0 5px 5px rgba(0,0,0,0.2);
}       
        
    </style>
</head>
<body>

 <% OfferModel offerModel = (OfferModel) request.getAttribute("offerModel"); %>
   
Dear <%= offerModel.getCandidateName() %>,

We are delighted to offer you employment for the position of "<%= offerModel.getOfferedJob() %>". As part of this offer, you will be required to undergo a Pre-Employment Internship . During this internship, you will receive comprehensive training in various domains, including technical, functional, and product framework domains. 

Upon successful completion of your training and assessment, your allocation to the compensation packages will be determined based on your performance.

Further Information will be provided after successful join into our organisation.


With best wishes and looking forward to a mutually fruitful association,



<h2>Details:</h2>
<p>HR Mobile Number: <%= offerModel.getAdminMobile() %></p>
<p>HR Email: <%= offerModel.getAdminEmail() %></p>
<p>Offer Date: <%= offerModel.getOfferDate() %></p>
<p>Offered Job: <%= offerModel.getOfferedJob() %></p>
<p>Reporting Date: <%= offerModel.getReportingDate() %></p>
<p>Documents to Bring While Coming:</p>
Please note that the information provided in this offer letter is subject to the specific details and terms mentioned in the formal letter of appointment that you will receive upon joining our organization.
      <ul>
        <c:forEach var="document" items="${offerModel.getDocuments()}">
            <li>${document}</li>
        </c:forEach>
    </ul>
</body>
</html>
