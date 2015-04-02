<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>
        Chess
    </h2>

    <form:form modelAttribute="move" method="post"
               class="form-horizontal">
        <div class="control-group" id="displayUserRequest">
            <label><c:out value="${move.requestToUser}"/>
            </label>
        <div class="controls">
            <form:hidden path="commandFromUser" action="submit"/>
        </div>
        <div class="container">
            <jsp:include page="board.jsp"/>
        </div>
    </form:form>
</div>
</body>

</html>