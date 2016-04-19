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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<h3><spring:message code="message.folder"/>: <jstl:out value="${folder.name}" /></h3>
	<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag"
		name="messa" requestURI="${requestURI}" id="row_messa">

		<spring:message code="message.display" var="displayHeader" />
		<display:column>
			<a href="message/actor/display.do?messageId=${row_messa.id}"> 
				<spring:message code="message.display" />
			</a>
		</display:column>
		
		<spring:message code="message.delete" var="deleteHeader" />
		<display:column>
			<a href="message/actor/delete.do?messageId=${row_messa.id}&folderId=${folder.id}" onclick="return confirm('<spring:message code="message.confirm.delete" />')"> 
				<spring:message code="message.delete" />
			</a>
		</display:column>
		
		<!-- flaged As Spam -->
		<jstl:forEach items="${row_messa.folders}" var="actFolder">
			<jstl:if test="${actFolder.name=='SpamBox' && actFolder.isSystem==true}">
				<jstl:set var="isSpam" value="true"/>
			</jstl:if>
		</jstl:forEach>
		
		<spring:message code="message.flagspam" var="flagspamHeader" />
		<display:column>
			<jstl:if test="${isSpam != 'true' }">
				<a href="message/actor/flag-as-spam.do?messageId=${row_messa.id}&redirectUri=${requestURI}" onclick="return confirm('<spring:message code="message.confirm.flagspam" />')"> 
				<spring:message code="message.flagspam" />
				</a>
			</jstl:if>
		</display:column>

		<!-- Attributes -->
		<spring:message code="message.priority" var="priorityHeader" />
		<display:column title="${priorityHeader}"
			sortable="true">
			<acme:messagePriority priority="${row_messa.priority}"/>
		</display:column>

		<spring:message code="message.sentMoment" var="sentMomentHeader" />
		<display:column title="${sentMomentHeader}"
			sortable="true" format="{0,date,yyyy/MM/dd }" >
			<jstl:out value="${row_messa.sentMoment}"/>
		</display:column>
		
		<spring:message code="message.subject" var="subjectHeader" />
		<display:column title="${subjectHeader}"
			sortable="true" >
			<jstl:out value="${row_messa.subject}"/>
		</display:column>
			
	</display:table>
	
	<!-- Action links -->
	<div>
		<b><a href="message/actor/create.do"> 
			<spring:message code="message.create" />
		</a></b>
	</div>

