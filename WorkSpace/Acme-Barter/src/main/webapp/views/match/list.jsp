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

<security:authorize access="hasRole('USER')">

	<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="matches" requestURI="${requestURI}" id="row_Match">
		
		<!-- Attributes -->
		<spring:message code="match.creationMoment" var="creationMomentHeader" />
		<acme:displayColumn title="${creationMomentHeader}" sorteable="true" value="${row_Match.creationMoment}" format="{0,date,yyyy/MM/dd}"/>
		
		<spring:message code="match.offerSignsDate" var="offerSignsDateHeader" />
		<acme:displayColumn title="${offerSignsDateHeader}" sorteable="true" value="${row_Match.offerSignsDate}" format="{0,date,yyyy/MM/dd}"/>
		
		<spring:message code="match.requestSignsDate" var="requestSignsDateHeader" />
		<acme:displayColumn title="${requestSignsDateHeader}" sorteable="true" value="${row_Match.requestSignsDate}" format="{0,date,yyyy/MM/dd}"/>
			
		<spring:message code="match.report" var="reportHeader" />
		<acme:displayColumn title="${reportHeader}" sorteable="false" value="${row_Match.report}"/>
		
		<spring:message code="match.legalText" var="legalTextHeader" />
		<acme:displayColumn title="${legalTextHeader}" sorteable="true" value="${row_Match.legalText.text}"/>
		
		<spring:message code="match.creatorBarter" var="creatorBarterHeader" />
		<acme:displayColumn title="${creatorBarterHeader}" sorteable="true" value="${row_Match.creatorBarter.title}"/>
		
		<spring:message code="match.receiverBarter" var="receiverBarterHeader" />
		<acme:displayColumn title="${receiverBarterHeader}" sorteable="true" value="${row_Match.receiverBarter.title}"/>
		
		<!-- Action links -->
		<display:column>
			<a href="match/user/cancel.do?matchId=${row_Match.id}"><spring:message code="match.cancel" /></a>
		</display:column>
		
		<display:column>
			<a href="match/user/sign.do?matchId=${row_Match.id}"><spring:message code="match.sign" /></a>
		</display:column>
		
	</display:table>
	
	<a href="match/user/create.do"><spring:message code="match.create"/></a>
	
</security:authorize>

<security:authorize access="hasRole('ADMIN')">

	<a href="match/administrator/cancel.do"><spring:message code="match.cancelNotSigned"/></a>

</security:authorize>
