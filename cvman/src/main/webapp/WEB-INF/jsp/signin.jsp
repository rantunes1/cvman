<%@ page language="java" contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
	<%@ include file="head.jsp" %>
	<title>CVManager</title>
	
	<style>
		#header {
			width:25%; min-width:25%; height:40%; float:left; height:auto; clear:both; margin:0.5em; 		
 		} 		
 		#details{
 			width:98%; height:auto; float:left; clear:both; padding:0.2em; margin:0.5em;		
 		}
	 </style>
	
</head>
<body>
	<%@ include file="header.jsp" %>
	
	<div id="container">
		<form id="signin" action="<c:url value="/signin/authenticate" />" method="post">
			<div class="formInfo">
		  		<c:if test="${param.error eq 'bad_credentials'}">
		  		<div class="error">
		  			Your sign in information was incorrect.
		  			Please try again or <a href="<c:url value="/signup" />">sign up</a>.
		  		</div>
		 	 	</c:if>
		  		<c:if test="${param.error eq 'multiple_users'}">
		  		<div class="error">
		  			Multiple local accounts are connected to the provider account.
		  			Try again with a different provider or with your username and password.
		  		</div>
		 	 	</c:if>
			</div>
	
			<fieldset>
				<label for="username"><spring:message code="UI.login.label.username"/></label>
				<input id="username" name="j_username" type="text" size="25" <c:if test="${not empty signinErrorMessage}">value="${SPRING_SECURITY_LAST_USERNAME}"</c:if> />
				<label for="password"><spring:message code="UI.login.label.password"/></label>
				<input id="password" name="j_password" type="password" size="25" />	
			</fieldset>
	
			<button type="submit"><spring:message code="UI.login.action.signin"/></button>
		</form>
	
		<!-- LinkedIn signin -->
		<form name="linked_signin" id="linkedin_signin" action="<c:url value="/signin/linkedin"/>" method="POST">
			<button type="submit"><img src="<c:url value="/images/linkedin-small.png"/>" /></button>
		</form>
	</div>
	
	<%@ include file="footer.jsp" %>
</body>
</html>