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

<jstl:if test="${!noDisplayHeader}">
	<security:authorize access="hasRole('USER')">
		<h3><spring:message code="match.userList" /></h3>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<h3><spring:message code="match.adminList" /></h3>
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<h3><spring:message code="match.auditorList" /></h3>
	</security:authorize>
</jstl:if>

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
		
		<spring:message code="match.legalText" var="legalTextHeader" />
		<acme:displayColumn title="${legalTextHeader}" sorteable="true" value="${row_Match.legalText.text}"/>
		
		<spring:message code="match.creatorBarter" var="creatorBarterHeader" />
		<acme:displayColumn title="${creatorBarterHeader}" sorteable="true" value="${row_Match.creatorBarter.title}"/>
		
		<spring:message code="match.receiverBarter" var="receiverBarterHeader" />
		<acme:displayColumn title="${receiverBarterHeader}" sorteable="true" value="${row_Match.receiverBarter.title}"/>
		
		<spring:message code="match.report" var="reportHeader" />
		<acme:displayColumn title="${reportHeader}" sorteable="false" value="${row_Match.report}"/>
		
		<security:authorize access="hasAnyRole('ADMIN', 'AUDITOR')">
			<spring:message code="match.cancelled" var="cancelledHeader" />
			<acme:displayColumn title="${cancelledHeader}" sorteable="true" value="${row_Match.cancelled}"/>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<!-- Action links -->
			<spring:message code="match.cancel" var="cancelHeader" />
			<display:column title="${cancelHeader}" sortable="true">
			<jstl:if test="${row_Match.creatorBarter.user.id == userId || row_Match.receiverBarter.user.id == userId}">
				<a href="match/user/cancel.do?matchId=${row_Match.id}"><spring:message code="match.cancel" /></a>
			</jstl:if>
			</display:column>
			
			<spring:message code="match.sign" var="signHeader" />
			<display:column title="${signHeader}" sortable="true">
				<jstl:if test="${row_Match.creatorBarter.user.id == userId && row_Match.offerSignsDate == null || row_Match.receiverBarter.user.id == userId && row_Match.requestSignsDate == null}">
					<a href="match/user/sign.do?matchId=${row_Match.id}"><spring:message code="match.sign" /></a>
				</jstl:if>
			</display:column>
			
		</security:authorize>

	<security:authorize access="hasRole('AUDITOR')">
		<spring:message code="match.edit" var="editHeader" />
		<display:column title="${editHeader}" sortable="true">
			<jstl:if test="${row_Match.auditor == null}">
				<a
					href="match/auditor/self-assign.do?matchId=${row_Match.id}&redirectUri=${requestURI}">
					<spring:message code="match.self.assign" />
				</a>
			</jstl:if>
			<jstl:if test="${row_Match.auditor != null}">
				<jstl:if test="${row_Match.auditor.id == auditor_id}">
					<a href="match/auditor/write-report.do?matchId=${row_Match.id}">
						<spring:message code="match.edit" />
					</a>
				</jstl:if>
			</jstl:if>

		</display:column>
	</security:authorize>

</display:table>
	
	<security:authorize access="hasRole('USER')">
		<a href="match/user/create.do"><spring:message code="match.create"/></a>
	</security:authorize>
	
<security:authorize access="hasRole('ADMIN')">

	<a href="match/administrator/cancel.do"><spring:message code="match.cancelNotSigned"/></a>

</security:authorize>