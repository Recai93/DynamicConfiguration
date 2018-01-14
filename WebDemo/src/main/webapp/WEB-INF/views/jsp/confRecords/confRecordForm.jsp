<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp" />

<div class="container">

	<c:choose>
		<c:when test="${confRecord['new']}">
			<h1>Add Configuration Record</h1>
		</c:when>
		<c:otherwise>
			<h1>Update Configuration Record</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/confRecords" var="confRecordActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="confRecord" action="${confRecordActionUrl}">

		<form:hidden path="id" />

		<spring:bind path="name">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<form:input path="name" type="text" class="form-control " id="name" placeholder="Name" />
					<form:errors path="name" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="type">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Type</label>
				<div class="col-sm-5">
					<form:select path="type" items="${types}" size="4" class="form-control" />
					<form:errors path="type" class="control-label" />
				</div>
				<div class="col-sm-5"></div>
			</div>
		</spring:bind>

		<spring:bind path="value">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">Value</label>
				<div class="col-sm-10">
					<form:input path="value" type="text" class="form-control " id="value" placeholder="value" />
					<form:errors path="value" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="isActive">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">IsActive</label>
				<div class="col-sm-10">
					<label class="radio-inline">
						<form:radiobutton path="isActive" value="1" /> 1
					</label>
					<label class="radio-inline">
						<form:radiobutton path="isActive" value="0" /> 0
					</label> <br />
					<form:errors path="isActive" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="applicationName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-2 control-label">ApplicationName</label>
				<div class="col-sm-10">
					<form:input path="applicationName" type="text" class="form-control " id="applicationName" placeholder="applicationName" />
					<form:errors path="applicationName" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${confRecord['new']}">
						<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>

</div>

<jsp:include page="../fragments/footer.jsp" />

</html>