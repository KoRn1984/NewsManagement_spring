<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.newses" var="newses" />
<fmt:message bundle="${loc}" key="local.loc.name.latestNews" var="latest_news" />
<fmt:message bundle="${loc}" key="local.loc.name.noNews" var="no_news" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guest Info</title>
</head>
<body>
<div class="body-title">    
	<a href="controller?command=go_to_news_list">${newses} >></a> ${latest_news}
</div>
<form action="command.do?method=delete" method="post">
	<c:forEach var="news" items="${requestScope.news}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
				<div class="news-title">
				    <strong>
					    <c:out value="${news.title}" />
				    </strong>
				</div>
				<div class="news-date">
				    <fmt:parseDate value="${news.date}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
				    <fmt:formatDate value="${parsedDate}" var="newsDate" type="date" pattern="MM/dd/yyyy" />
					<c:out value="${newsDate}" />
				</div>
				<div class="news-content">
					<c:out value="${news.brief}" />
				</div>
			</div>
		</div>
	</c:forEach>
	<c:if test="${requestScope.showNews eq 'not_show'}">
	<div class="no-news">
		<c:if test="${sessionScope.news eq null}">
		<font color="red">
        ${no_news}!
        </font>
	    </c:if>
	</div>
	</c:if>
</form>
</body>
</html>