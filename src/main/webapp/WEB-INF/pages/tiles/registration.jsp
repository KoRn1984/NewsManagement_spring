<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.enterDetails" var="enter_details" />
<fmt:message bundle="${loc}" key="local.loc.name.enterLogin" var="login" />
<fmt:message bundle="${loc}" key="local.loc.name.enterPassword" var="password" />
<fmt:message bundle="${loc}" key="local.loc.name.enterName" var="name" />
<fmt:message bundle="${loc}" key="local.loc.name.enterSurname" var="surname" />
<fmt:message bundle="${loc}" key="local.loc.name.enterEmail" var="email" />
<fmt:message bundle="${loc}" key="local.loc.name.register" var="register" />
<fmt:message bundle="${loc}" key="local.loc.name.cancel" var="cancel" />
<fmt:message bundle="${loc}" key="local.loc.name.registrationError" var="registration_error" />

<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
</head>
<body>
<div class="add-table-margin">
<table class="news_text_format">
<legend><b><div style="text-align:center;">${enter_details}:</div></b></legend><br/>
     <form action="controller" method="post">
			<input type="hidden" name="command" value="do_registration" />
			${login}:<br/>
			<input type="text" name="login" value="" required pattern="^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$" size=35 maxlength=30/><br/>
			${password}:<br/>
			<input type="password" name="password" value="" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\w\s]).{6,}" size=35 maxlength=30/><br/>
			${name}:<br/>
			<input type="text" name="name" value="" required pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$" size=35 maxlength=30/><br/>
			${surname}:<br/>
			<input type="text" name="surname" value="" required pattern="^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$" size=35 maxlength=30/><br/>
			${email}:<br/>
			<input type="email" name="email" value="" required pattern="([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*@([A-zА-я])+([0-9\\-_\\+\\.])*([A-zА-я0-9\\-_\\+\\.])*[\\.]([A-zА-я])+" size=35 maxlength=30/><br/>
			<br />
			<c:if test="${not (param.RegistrationError eq null)}">					
				<font color="red">
					<c:out value="${registration_error}" />
				</font> 
			</c:if><br />
			<button type="submit" class="btn btn-success" value="${register}">${register}</button>
		</form>
</table>
</div>
<br /><br />
<div class="cancel-button">
		<form action="controller" method="post">
		     <input type="hidden" name="command" value="go_to_base_page" />
		     <button type="submit" class="btn btn-dark" value="${cancel}">${cancel}</button>		     
		</form>
</div>		
</body>
</html>