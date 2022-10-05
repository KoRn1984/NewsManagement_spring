<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.loc.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.loc.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.loc.name.newsManagement" var="news_management" />
<fmt:message bundle="${loc}" key="local.loc.name.signIn" var="sign_in" />
<fmt:message bundle="${loc}" key="local.loc.name.signOut" var="sign_out" />
<fmt:message bundle="${loc}" key="local.loc.name.enterLogin" var="enter_login" />
<fmt:message bundle="${loc}" key="local.loc.name.enterPassword" var="enter_password" />
<fmt:message bundle="${loc}" key="local.loc.name.registration" var="registration" />
<fmt:message bundle="${loc}" key="local.loc.name.authenticationError" var="authentication_error" />
<fmt:message bundle="${loc}" key="local.loc.name.personalAccount" var="personalAccount" />

<div class="wrapper">   
	<div class="newstitle">${news_management}</div>
	<div class="local-link">	
		<div align="right">
		<form action="controller" method="post">		    
			<a href="controller?command=do_change_local&local=en">${en_button}</a>&nbsp;&nbsp;
			<a href="controller?command=do_change_local&local=ru">${ru_button}</a><br /><br />
		</form>
		</div>		
		<c:if test="${not (sessionScope.user_status eq 'active')}">
			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_in" />					
					${enter_login}:<input type="text" name="login" value="" required pattern="^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$"/><br />
					${enter_password}:<input type="password" name="password" value="" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\w\s]).{6,}"/><br />
					<c:if test="${not (param.AuthenticationError eq null)}">					
						<font color="red">
						   <c:out value="${authentication_error}" />
						</font> 
					</c:if>
					<input type="hidden" name="command" value="do_registration" />
					<a href="controller?command=go_to_registration_page">${registration}</a>
					<button type="submit" class="badge text-bg-info" value="${sign_in}">${sign_in}</button>					
				</form>
			</div>
		</c:if>			
		<c:if test="${sessionScope.user_status eq 'active'}">
			<div align="right">			
			<font color="blue">${user.userName}</font>&nbsp;&nbsp;<font color="blue">${user.userSurname}</font>
			<form action="controller" method="post">
			<div align="right">			
				<a href="controller?command=go_to_user_personal_account">${personalAccount}</a>
			</div>								    
				<input type="hidden" name="command" value="do_sign_out" />
				<button type="submit" class="badge text-bg-info" value="${sign_out}">${sign_out}</button>
			</form>
			</div>
		</c:if>		
	</div>
</div>
</body>
</html>