<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.details" var="details" />
<fmt:message bundle="${loc}" key="local.loc.name.login" var="login" />
<fmt:message bundle="${loc}" key="local.loc.name.name" var="name" />
<fmt:message bundle="${loc}" key="local.loc.name.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.loc.name.email" var="email" />
<fmt:message bundle="${loc}" key="local.loc.name.back" var="back" />

<!DOCTYPE html>
<html>
<head>
<title>User personal account</title>
</head>
<body>
<div class="add-table-margin">
<table class="news_text_format">
<legend><b><div style="text-align:center;">${details}:</div></b></legend><br/>
     <div>			
			<strong>${login}:</strong><br/>${user.login}
			<br /><br />			
			<strong>${name}:</strong><br/>${user.userName}
			<br /><br />
			<strong>${surname}:</strong><br/>${user.userSurname}
			<br /><br />		
			<strong>${email}:</strong><br/>${user.email}			
			<br />
	</div>
</table>
</div>
<br /><br />
<div align="center" class="cancel-button">
	<form action="controller" method="post">
		 <input type="hidden" name="command" value="go_to_news_list" />
		 <button type="submit" class="btn btn-secondary" value="${back}">${back}</button>		
	</form>
</div>
</body>
</html>