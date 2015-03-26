<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">

<!-- jsp:include page="../fragments/staticFiles.jsp"/> -->
<body>

<script>
    $(function () {
        $("#birthDate").datepicker({ dateFormat: 'yy/mm/dd'});
    });
</script>
<div class="container">
    <!-- <jsp:include page="header.jsp"/> -->

    <h2>
        Chess
    </h2>

    <form:form modelAttribute="move" method="post"
               class="form-horizontal">
        <div class="control-group" id="command">
            <label class="control-label">Command</label>
        </div>
        <spring:bind path="command">
            <div>
                <div class="controls">
                    <form:input path="command" action="submit"/>
                </div>
            </div>
        </spring:bind>
        <table >
            <tr>
                <td style="width:20px">*</th>
                <th style="width:20px">a</th>
                <th style="width:20px">b</th>
                <th style="width:20px">c</th>
                <th style="width:20px">d</th>
                <th style="width:20px">e</th>
                <th style="width:20px">f</th>
                <th style="width:20px">g</th>
                <th style="width:20px">h</th>
            </tr>
            <c:forEach var="row" items="${board}">
            <tr>
                <th>1</td>
                 <c:forEach var="item" items="${row}">
                 <td>
                    <c:out value="${item}"/>
                 </td>
                 </c:forEach>
            </tr>
            </c:forEach>
        </table>
    </form:form>
</div>
</body>

</html>