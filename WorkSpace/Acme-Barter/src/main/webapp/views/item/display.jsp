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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<!-- Listing grid -->
	<div>
	<table>

	<acme:display code="item.name" value="${item.name}"/>
	<acme:display code="item.description" value="${item.description}"/>
	<tr>
		<th><spring:message code="item.pictures" /> :</th>
		<jstl:forEach items="${item.pictures}" var="picture">
			<td><img src="${picture}" style="width:204px;"/></td>
		</jstl:forEach>
	</tr>
	
	</table>
	</div>
	<!-- Action links -->