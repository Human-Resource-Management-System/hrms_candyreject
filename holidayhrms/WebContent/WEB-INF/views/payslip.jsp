<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="models.input.output.EmployeePayRollOutputModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Payslip</title>

<style>

    h1 {
        text-align: center;
        color: #333;
    }

    h2 {
        margin-bottom: 10px;
        color: #666;
    }

    .container {
        display: flex;
        height: 100%;
    }

    .container1 {
        flex: 1;
        display: flex;
        flex-direction: column;
        border-right: 1px solid #ccc;
        padding-right: 20px;
        margin-right: 20px;
    }

    .container2 {
        flex: 1;
        display: flex;
        flex-direction: column;
    }

    .sub-container {
        flex: 1;
        margin-bottom: 20px;
    }

    p {
        margin: 5px 0;
    }

    strong {
        font-weight: bold;
    }

    .details {
        color: #333;
    }

    .deductions {
        color: #cc0000;
    }

    .pay {
        color: #009933;
    }

    .total {
        margin-top: 20px;
        color: #333;
        font-weight: bold;
    }

    .netpay {
        margin-top: 10px;
        color: #009933;
        font-size: 20px;
    }

    hr {
        border-top: 1px solid #ccc;
        margin-top: 20px;
    }

    .center-align {
        display: flex;
        justify-content: center;
        margin-top: 30px;
    }

    .button {
        padding: 10px 20px;
        background-color: #009933;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }

    .button:hover {
        background-color: #008026;
    }
</style>
     
</head>
<body>
    <h1>Employee Payslip</h1>
    <% EmployeePayRollOutputModel pay = (EmployeePayRollOutputModel) request.getAttribute("pay"); %>
    <div class="container">
        <div class="container1">
            <div class="sub-container">
                <h2>Details</h2>
                <p class="details"><strong>Employee ID:</strong> <%= pay.getId() %></p>
                <p class="details"><strong>Employee Name:</strong> <%= pay.getFirstName() + " " + pay.getLastName() %></p>
                <p class="details"><strong>Designation:</strong> <%= pay.getDesignation() %></p>
                <p class="details"><strong>Month:</strong> <%= pay.getMonthYear() %></p>
                <p class="details"><strong>Basic Pay:</strong> <%= pay.getBasicPay() %></p>
                <p class="details"><strong>HRA:</strong> <%= pay.getHra() %></p>
                <p class="details"><strong>TA:</strong> <%= pay.getTa() %></p>
                <p class="details"><strong>DA:</strong> <%= pay.getDa() %></p>
            </div>
            <div class="sub-container">
                <h2>Deductions</h2>
                <p class="deductions"><strong>Gratuity:</strong> <%= pay.getGratuity() %></p>
                <p class="deductions"><strong>ESI:</strong> <%= pay.getEsi() %></p>
                <p class="deductions"><strong>PF:</strong> <%= pay.getPf() %></p>
                <p class="deductions"><strong>PTax:</strong> 200</p>
                <p class="deductions"><strong>TDS:</strong> <%= pay.getTds() %></p>
            </div>
        </div>
        <div class="container2">
            <h2>Pay</h2>
            <p class="pay"><strong>Additions:</strong> <%= pay.getAdditions() %></p>
            <p class="pay"><strong>Total Deductions:</strong> <%= pay.getDeduction() %></p>
            <hr>
            <p class="total"><strong>Total:</strong> <%= pay.getTotal() %></p>
            <p class="netpay"><strong>Net Pay:</strong> <%= pay.getNetpay() %></p>
        </div>
    </div>
    <div class="center-align">
        
    </div>
</body>
</html>
