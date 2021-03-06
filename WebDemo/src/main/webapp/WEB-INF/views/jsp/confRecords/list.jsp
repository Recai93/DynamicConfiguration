<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../fragments/header.jsp"/>

<body>

<div class="container">

    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <h1>All Records</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>#ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Value</th>
            <th>IsActive</th>
            <th>ApplicationName</th>
            <th>Action</th>
        </tr>
        </thead>

        <c:forEach var="confRecord" items="${confRecords}">
            <tr>
                <td>
                        ${confRecord.id}
                </td>
                <td>${confRecord.name}</td>
                <td>${confRecord.type}</td>
                <td>${confRecord.value}</td>
                <td>${confRecord.isActive}</td>
                <td>${confRecord.applicationName}</td>
                <td>
                    <spring:url value="/confRecords/${confRecord.id}/delete" var="deleteUrl"/>
                    <spring:url value="/confRecords/${confRecord.id}/update" var="updateUrl"/>

                    <button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
                    <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>

    <spring:url value="/confRecords/search" var="searchUrl"/>
    <form:form class="form-horizontal" method="get" action="${searchUrl}">
        <div class="form-group">
            <label class="col-sm-2 control-label">Search</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="searchText" placeholder="searchText" name="searchText"/>
                <br/>
                <button class="btn btn-primary" onclick="location.href='${searchUrl}'">Search</button>
            </div>
        </div>
    </form:form>

</div>

<jsp:include page="../fragments/footer.jsp"/>

</body>
</html>