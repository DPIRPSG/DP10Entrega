<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="barters" requestURI="${requestURI}" id="row_Barter">
	<!-- Action links -->

	<!-- Attributes -->

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="barter.cancelled" var="cancelledHeader" />
		<display:column title="${cancelledHeader}" sortable="true">
			<jstl:out value="${row_Barter.cancelled}" />
		</display:column>
	</security:authorize>

	<spring:message code="barter.title" var="titleHeader" />
	<display:column title="${titleHeader}"
		sortable="true">
		<jstl:out value="${row_Barter.title}"/>
	</display:column>
	
	<spring:message code="barter.registerMoment" var="registerMomentHeader" />
	<display:column title="${registerMomentHeader}"
		sortable="true">
		<jstl:out value="${row_Barter.registerMoment}"/>
	</display:column>
	
</display:table>

<br/>
<br/>

<!-- Action links -->
