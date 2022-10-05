<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />    
<fmt:message bundle="${loc}" key="local.loc.name.newses" var="newses" />
<fmt:message bundle="${loc}" key="local.loc.name.newsList" var="news_list" />
<fmt:message bundle="${loc}" key="local.loc.name.addNews" var="add_news" />

<div class="menu-wrapper">
	<div class="menu-title-wrapper">
		<div class="menu-title">${newses}</div>
	</div>
	<div class="list-menu-invisible-wrapper">
		<div class="list-menu-wrapper" style="float: right;">
			<ul style="list-style-image: url(images/img.jpg); text-align: left;">
				<li style="padding-left: 15px;">				
				<a href="controller?command=go_to_news_list">${news_list}</a><br />
				</li>
				<c:if test="${sessionScope.role eq 'admin'}">
				<li style="padding-left: 15px;">				
				<a href="controller?command=go_to_add_news_page">${add_news}</a><br />					
				</li>
				</c:if>
			</ul>
		</div>		
		<div class="clear"></div>
	</div>
	<!--  grey free space at the bottom of menu -->
	<div style="height: 25px;"></div>
</div>