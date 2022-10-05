<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.error" var="error" />
<fmt:message bundle="${loc}" key="local.loc.name.page_error" var="page_error" />
<fmt:message bundle="${loc}" key="local.loc.name.link_404" var="link_404" />

<!DOCTYPE html>
<html>
<head>  
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Arvo'>
<style>
.page_404{ padding:40px 0; background:#fff; font-family: 'Arvo', serif;
}
.page_404  img{ width:100%;}
.four_zero_four_bg{ 
 background-image: url(images/dribbble_1.gif);
    height: 400px;
    background-position: center;
 } 
 .four_zero_four_bg h1{
 font-size:80px;
 } 
  .four_zero_four_bg h3{
       font-size:80px;
       }       
       .link_404{      
    color: #fff!important;
    padding: 10px 20px;
    background: #39ac31;
    margin: 20px 0;
    display: inline-block;}
  .contant_box_404{ margin-top:-50px;}
</style>
</head>
<body>
  <section class="page_404">
  <div class="container">
    <div class="row"> 
    <div class="col-sm-12 ">
    <div class="col-sm-10 col-sm-offset-1  text-center">
    <div class="four_zero_four_bg">
      <h1 class="text-center ">404</h1>   
    </div>    
    <div class="contant_box_404">
    <h3 class="h2">
    ${error}
    </h3>    
    <p>${page_error}</p>    
    <a href="index.jsp" class="link_404">${link_404}</a>
  </div>
    </div>
    </div>
    </div>
  </div>
</section>
</html>