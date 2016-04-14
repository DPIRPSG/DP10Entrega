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
			<jstl:if test="${row_Barter.cancelled == false}">
				<a href="barter/administrator/cancel.do?barterId=${row_Barter.id}"> <spring:message
					code="barter.cancel"/>
				</a>
			</jstl:if>
			<jstl:if test="${row_Barter.cancelled == true}">
				<jstl:out value="${row_Barter.cancelled}" />
			</jstl:if>
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

	<spring:message code="barter.offered" var="offeredHeader" />
	<display:column title="${offeredHeader}" sortable="true">
		<a href="item/display.do?itemId=${row_Barter.offered.id}">
			<jstl:out value="${row_Barter.offered.name}"/>
		</a>
	</display:column>
	
	<spring:message code="barter.requested" var="requestedHeader" />
	<display:column title="${requestedHeader}" sortable="true">
		<a href="item/display.do?itemId=${row_Barter.requested.id}">
			<jstl:out value="${row_Barter.requested.name}"/>
		</a>
	</display:column>

</display:table>

<br/>
<br/>

<form action="${requestURI}">
	<input type="text" name="keyword"> <input type="submit"
		value="<spring:message code="barter.search" />" />&nbsp;
</form>

<!-- Action links -->
