<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Form -->
<form:form action="barter/administrator/edit.do" modelAttribute="barter">
	<!-- Hidden Attributes -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<!-- Editable Attributes -->
	<acme:textbox code="barter.title" path="title"/>
	
	<fieldset>
		<legend align="left">
			<spring:message code = "barter.offered"/>
		</legend>
		
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code = "barter.requested"/>
		</legend>
		
		<acme:textbox code="item.title" path="offered.title"/>
		<acme:textbox code="item.description" path="offered.description"/>
		<acme:textarea code="item.pictures" path="offered.pictures"/>
		
	</fieldset>
	
	<!-- Action buttons -->
	<acme:submit name="save" code="barter.save"/>
	
	<acme:cancel code="barter.cancel" url="barter/user/list.do"/>
	
</form:form>