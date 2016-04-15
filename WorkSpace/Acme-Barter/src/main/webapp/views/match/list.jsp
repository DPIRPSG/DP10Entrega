<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="matches" requestURI="${requestURI}" id="row_match">

		<display:column>
			<a href="match/auditor/write-report.do?matchId=${row_match.id}"> 
				<spring:message code="match.edit" />
			</a>
		</display:column>

		<!-- Attributes -->
		<spring:message code="match.report" var="reportHeader" />
		<display:column title="${reportHeader}"
			sortable="true" >
			<jstl:out value="${row_match.report}"/>
		</display:column>
		
		<spring:message code="match.creationMoment" var="creationMomentHeader" />
		<display:column title="${creationMomentHeader}"
			sortable="true" format="{0,date,yyyy/MM/dd }" >
			<jstl:out value="${row_match.creationMoment}"/>
		</display:column>

	</display:table>
	
	<!-- Action links -->


