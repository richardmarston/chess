<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>
        Login
    </h2>

    <form:form modelAttribute="user" method="post"
               class="form-horizontal">
        <div class="control-group" id="command">
            <label>Login</label>
        <div class="controls">
            <form:input path="name" action="submit"/>
        </div>
        <div class="controls">
            <label ><c:out value="${message}"/><label>
        </div>
    </form:form>
</div>
</body>

</html>