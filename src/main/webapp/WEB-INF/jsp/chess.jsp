<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
            <c:set var="cssGroup" value="control-group ${status.error ? 'error' : '' }"/>
            <div class="${cssGroup}">
                <label class="control-label">${label}</label>

                <div class="controls">
                    <form:input path="command" action="submit"/>
                    <span class="help-inline">${status.errorMessage}</span>
                </div>
            </div>
        </spring:bind>
    </form:form>
</div>

</body>

</html>