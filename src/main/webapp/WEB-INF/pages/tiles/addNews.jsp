<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.newses" var="newses" />
<fmt:message bundle="${loc}" key="local.loc.name.addNews" var="add_news" />
<fmt:message bundle="${loc}" key="local.loc.name.enterText" var="enter_text" />
<fmt:message bundle="${loc}" key="local.loc.name.title" var="title" />
<fmt:message bundle="${loc}" key="local.loc.name.date" var="date" />
<fmt:message bundle="${loc}" key="local.loc.name.brief" var="brief" />
<fmt:message bundle="${loc}" key="local.loc.name.content" var="content" />
<fmt:message bundle="${loc}" key="local.loc.name.save" var="save" />
<fmt:message bundle="${loc}" key="local.loc.name.cancel" var="cancel" />
<fmt:message bundle="${loc}" key="local.loc.name.addNewsError" var="add_news_error" />
<fmt:message bundle="${loc}" key="local.loc.name.placeholderTitle" var="placeholderTitle" />
<fmt:message bundle="${loc}" key="local.loc.name.placeholderBrief" var="placeholderBrief" />
<fmt:message bundle="${loc}" key="local.loc.name.placeholderContent" var="placeholderContent" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add News</title>
<style>
   textarea {
    background: #fce9c0;
    border: 2px solid #a9c358;
    padding: 10px;
    width: 100%;
    height: 200px;
    box-sizing: border-box;
    font-size: 14px;
   }
  </style>
</head>
<body>
<div class="body-title">
	<a href="controller?command=go_to_news_list">${newses} >> </a>${add_news}
</div>
<div class="add-table-margin">
	<table class="news_text_format">
<legend><b><div style="text-align:center;">${enter_text}:</div></b></legend><br />
<form action="controller" method="post">		
		<input type="hidden" name="command" value="do_add_news" />		              						
		<label>${title}:<br />
		<p><textarea type="text" name="title" placeholder="${placeholderTitle}" value="" style="width: 670px; height: 40px;"></textarea></p></label>        
        <label>${brief}:<br />
        <p><textarea type="text" name="brief" placeholder="${placeholderBrief}" value="" style="width: 670px; height: 60px;"></textarea></p></label>					
		<label>${content}:<br />
		<p><textarea type="text" name="content" placeholder="${placeholderContent}" value="" style="width: 670px; height: 140px;"></textarea></p></label>			
		<br />
		<c:if test="${not (param.AddNewsError eq null)}">					
			<font color="red">
				<c:out value="${add_news_error}" />
			</font> 
		</c:if><br />
		<button type="submit" class="btn btn-info" value="${save}">${save}</button>
</form>
</table>
</div>
<div class="cancel-button">
		<form action="controller" method="post">
		    <input type="hidden" name="command" value="go_to_news_list" />		    
		    <button type="submit" class="btn btn-dark" value="${cancel}">${cancel}</button>
		</form>
</div>
</body>
</html>