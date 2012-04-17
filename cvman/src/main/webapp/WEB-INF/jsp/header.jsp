<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<span style="float: right"><a href="?lang=en">en</a>|<a href="?lang=pt">pt</a></span>

<c:if test="${not empty message}">
	<div class="${message.type.cssClass}">${message.text}</div>
</c:if>
	