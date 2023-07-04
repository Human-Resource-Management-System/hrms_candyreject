<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet">
    <title>Pennant HRMS</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="style.css">
    <!---we had linked our css file----->
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        
        
.container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 100vh;
  padding-bottom: 100px;
}


.slideshow {
margin-top:150px;
margin-left:50px;
  width: 600px;
  height: 300px;
  position: relative;
  overflow: hidden;
  border: 1px solid #ccc;
  float: left;
  border-radius: 10px; /* Add border radius */
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1); /* Add box shadow */
}

.slides {
  display: flex;
  width: 100%;
  height: 100%;
}

.slide {
  flex: 0 0 100%;
  opacity: 0;
  transition: opacity 1s ease;
  position: absolute;
  border-radius: 10px; /* Add border radius */
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1); /* Add box shadow */
}

.slide.active {
  opacity: 1;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px; /* Add border radius */
}




*
{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
 
 body {
  background-color: #E6F1FF;
}
 
.full-page
{
    height: 100%;
	width: 100%;
	background-position: center;
	background-size: cover;
	position: absolute;
}
.navbar
{
    display: flex;
    align-items: center;
    padding: 20px;
    padding-left: 50px;
    padding-right: 30px;
    padding-top: 50px;
    font-family: 'Oswald', sans-serif;
}
nav
{
    flex: 1;
    text-align: right;
}
nav ul 
{
    display: inline-block;
    list-style: none;
}
nav ul li
{
    display: inline-block;
    margin-right: 70px;
}
nav ul li a
{
    text-decoration: none;
    font-size: 20px;
    color:black;
    font-family: sans-serif;
}
nav ul li button {
    font-size: 20px;
    color: white;
    outline: none;
    border: none;
    background: linear-gradient(to right, #3BC59A, #2C8EBB);
    cursor: pointer;
    font-family: sans-serif;
    padding: 10px 20px;
    border-radius: 5px;
    transition: background 0.3s ease;
}

nav ul li button:hover {
    background: linear-gradient(to right, #2C8EBB, #3BC59A);
}


nav ul li a:hover
{
    color: #3BC59A;
}
a
{
    text-decoration: none;
    color:black;
    font-size: 28px;
    font-family: Oswald, sans-serif;
}
#login-form
{
    display: none;
    padding-left: 900px;
    padding-top: 40px;
    
}
.form-box {
    width: 340px;
    height: 400px;
    position: relative;
    margin: 2% auto;
    margin: 66px 15px 30px -2px;
    background: linear-gradient(to bottom, #e0f5ff, #b3e6ff); /* Lighter blue gradient */
    padding: 20px;
    overflow: hidden;
    border-radius: 50px;
    border:0.5px solid black;
}

.button-box
{
	width:220px;
	margin:35px auto;
	position:relative;
	box-shadow: 0 0 20px 9px #ff61241f;
	border-radius: 30px;
}
.toggle-btn
{
	padding:10px 30px;
	cursor:pointer;
	background:transparent;
	border:0;
	outline: none;
	position: relative;
}
#btn
{
	top: 0;
	left:0;
	position: absolute;
	width: 110px;
	height: 100%;
    background: linear-gradient(to right, #85d7ff, #0088ff); /* Lighter blue gradient */
	border-radius: 30px;
	transition: .5s;
}
.input-group-login
{
	top: 150px;
	position:absolute;
	width:280px;
	transition:.5s;
}
.input-group-register
{
    top: 120px;
	position:absolute;
	width:280px;
	transition:.5s;
}
.input-field
{
	width: 100%;
	padding:10px 0;
	margin:5px 0;
	border-left:0;
	border-top:0;
	border-right:0;
	border-bottom: 1px solid #999;
	outline:none;
	background: transparent;
}
.submit-btn
{
	width: 85%;
	padding: 10px 30px;
	cursor: pointer;
	display: block;
	margin: auto;
    background: linear-gradient(to right, #85d7ff, #0088ff); /* Lighter blue gradient */
	border: 0;
	outline: none;
	border-radius: 30px;
}
.check-box
{
	margin: 30px 10px 34px 0;
}
span
{
	color:#777;
	font-size:12px;
	bottom:68px;
	position:absolute;
}
#login
{
	left:50px;
}
#login input
{
	color:white;
	font-size:15;
}
#register
{
	left:450px;
}
#register input
{
	color:white;
	font-size: 15;
}
 .forgot-password {
     text-align: center;
     margin-top: 10px;
     margin-bottom: 10px;
        }
        
.forgot-password a {
      color: navy;
      font-size:15px; /* Dark Gray */
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
   .full-page {
            height: 100%;
            width: 100%;
            background: linear-gradient(to bottom, #E6F1FF, #D8E7FF, #CBe3FF, #BDDFFF, #B0DBFF);
            background-size: cover;
            position: absolute;
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
.input-field {
    color: black !important;
}
        
    </style>
</head>
<body>
    <div class="full-page">
        <div class="navbar">
            <div>
                <a href='https://www.pennanttech.com/'>PENNANT HRMS</a>
            </div>
            <nav>
                <ul id='MenuItems'>
                    <li><a href='#'>About Us</a></li>
                    <li><a href='#'>Contact</a></li>
                    <li><button class='loginbtn' onclick="document.getElementById('login-form').style.display='block'" style="width:auto;">Login</button></li>
                </ul>
            </nav>
        </div>
       
        <div id='login-form' class='login-page'>
            <div class="form-box">
                <div class='button-box'>
                    <div id='btn'></div>
                    <button type='button'onclick='login()'class='toggle-btn'>Emp</button>
                    <button type='button'onclick='register()'class='toggle-btn'>Admin</button>
                </div>
                <form id='login' class='input-group-login' action ="employee" method="POST">
                    <input type='text' id="empl_email" name ="empl_email" class='input-field'placeholder='Email Id' required >
		    <input type='password'id="empl_password" name="empl_password" class='input-field'placeholder='Enter Password' required>
		    		    <button type='submit'class='submit-btn' >Log in</button>
		    
		      <div class="forgot-password"> <a href="forgot">Forgot Password?</a></div>
		 </form>
		 <form id='register' class='input-group-register' action ="admin" method="POST">
                    <input type='text'id="admin_email" name ="admin_email" class='input-field'placeholder='Email Id' required >
		    <input type='password' id="admin_password"  name ="admin_password" class='input-field'placeholder='Enter Password' required>
		    <button type='submit'class='submit-btn' >Log in</button>
		    <div class="forgot-password a"> <a href="forgot">Forgot Password?</a></div>
		    
	         </form>
            </div>
        </div>
    </div>
     <div class="container">
        <div class="slideshow">
            <div class="slides">
                <div class="slide active">
                    <img src="<c:url value='/images/image1.jpg' />" alt="Image 1">
                </div>
                <div class="slide">
                    <img src="<c:url value='/images/image2.jpg' />" alt="Image 1">
                </div>
                <div class="slide">
                    <img src="<c:url value='/images/image3.jpg' />" alt="Image 1">
                </div>
            </div>
        </div>
    </div>
    <script>
        var x=document.getElementById('login');
		var y=document.getElementById('register');
		var z=document.getElementById('btn');
		function register()
		{
			x.style.left='-400px';
			y.style.left='50px';
			z.style.left='110px';
		}
		function login()
		{
			x.style.left='50px';
			y.style.left='450px';
			z.style.left='0px';
		}
	</script>
	<script>
        var modal = document.getElementById('login-form');
        window.onclick = function(event) 
        {
            if (event.target == modal) 
            {
                modal.style.display = "none";
            }
        }
    </script>
    
        <script>
        // Slideshow functionality
        const slides = document.querySelectorAll('.slide');
        let currentSlide = 0;

        function showSlide(slideIndex) {
            slides.forEach((slide) => {
                slide.classList.remove('active');
            });
            slides[slideIndex].classList.add('active');
        }

        function nextSlide() {
            showSlide(currentSlide);
            currentSlide = (currentSlide + 1) % slides.length;
        }

        nextSlide(); // Show the initial slide

        setInterval(nextSlide, 3000); // Change slide every 3 seconds
    </script>
    <script>
    let popup = document.getElementById("popup");
    
    function openPopup(){
    	popup.classList.add("open-popup");
    }
    
    function closePopup(){
    	popup.classList.remove("open-popup");
    }
    
    </script>
    <script>
        var x=document.getElementById('login');
		var y=document.getElementById('register');
		var z=document.getElementById('btn');
		function register()
		{
			x.style.left='-400px';
			y.style.left='50px';
			z.style.left='110px';
		}
		function login()
		{
			x.style.left='50px';
			y.style.left='450px';
			z.style.left='0px';
		}
	</script>
	<script>
        var modal = document.getElementById('login-form');
        window.onclick = function(event) 
        {
            if (event.target == modal) 
            {
                modal.style.display = "none";
            }
        }
    </script>
    
    
</body>
</html>