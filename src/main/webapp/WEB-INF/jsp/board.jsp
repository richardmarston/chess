<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <table modelAttribute="board" >
            <tr>
                <td style="width:20px"></th>
                <th style="width:20px">a</th>
                <th style="width:20px">b</th>
                <th style="width:20px">c</th>
                <th style="width:20px">d</th>
                <th style="width:20px">e</th>
                <th style="width:20px">f</th>
                <th style="width:20px">g</th>
                <th style="width:20px">h</th>
            </tr>
            <c:forEach var="row" items="${board}" varStatus="count">
            <tr>
                <th><c:out value="${9-(count.index+1)}" /></th>
                 <c:forEach var="item" items="${row}">
                 <td align="center">
                    <c:out value="${item}"/>
                 </td>
                 </c:forEach>
                <th><c:out value="${9-(count.index+1)}" /></th>
            </tr>
            </c:forEach>
            <tr>
                <td style="width:20px"></th>
                <th style="width:20px">a</th>
                <th style="width:20px">b</th>
                <th style="width:20px">c</th>
                <th style="width:20px">d</th>
                <th style="width:20px">e</th>
                <th style="width:20px">f</th>
                <th style="width:20px">g</th>
                <th style="width:20px">h</th>
            </tr>
        </table>