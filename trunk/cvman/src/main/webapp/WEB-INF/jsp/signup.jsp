<%@ page language="java" contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
	<%@ include file="header.jsp" %>
	<title>CVManager</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<div id="container">
		<c:url value="/signup/internal" var="internalSignupUrl" />
		<form:form id="internal_user_account" action="${internalSignupUrl}" method="post" modelAttribute="signupForm">
			<div class="formInfo">
		  		<s:bind path="*">
					<c:choose>
						<c:when test="${status.error}">
							<div class="error">Unable to sign up. Please fix the errors below and resubmit.</div>
						</c:when>
					</c:choose>                     
				</s:bind>
			</div>
	
			<fieldset>
				<form:label path="username"><s:message code="UI.login.label.username"/><form:errors path="username" cssClass="error" /></form:label>
				<form:input path="username" />		
				<form:label path="password"><s:message code="UI.login.label.password"/><form:errors path="password" cssClass="error" /></form:label>
				<form:password path="password" />
			</fieldset>
	
			<button type="submit"><s:message code="UI.login.action.signin"/></button>
		</form:form>
	
		<c:url value="/signup/external" var="externalSignupUrl" />
		<form:form id="new_user_account" action="${externalSignupUrl}" method="post" modelAttribute="signupForm">
			<div class="formInfo">
		  		<s:bind path="*">
					<c:choose>
						<c:when test="${status.error}">
							<div class="error">Unable to sign up. Please fix the errors below and resubmit.</div>
						</c:when>
					</c:choose>                     
				</s:bind>
			</div>
	
			<fieldset>
				<form:label path="username"><s:message code="UI.login.label.username"/><form:errors path="username" cssClass="error" /></form:label>
				<form:input path="username" />		
				<form:label path="password"><s:message code="UI.login.label.password"/><form:errors path="password" cssClass="error" /></form:label>
				<form:password path="password" />
				
				<form:label path="name"><s:message code="UI.user.label.name"/><form:errors path="name" cssClass="error" /></form:label>
				<form:input path="name" />		
				<form:label path="email"><s:message code="UI.user.label.email"/><form:errors path="email" cssClass="error" /></form:label>
				<form:password path="email" />
			</fieldset>
	
			<button type="submit"><s:message code="UI.login.action.signin"/></button>
		</form:form>
	</div>
	
	<%@ include file="footer.jsp" %>
</body>
</html>