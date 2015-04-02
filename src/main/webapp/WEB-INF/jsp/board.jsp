<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <style>
            td { background-color: #FFFFFF }
            .initial { background-color: #DDDDDD; color:#000000 }
            .normal { background-color: #CCCCCC }
            .highlight { background-color: #8888FF }
        </style>

        <script>
            var highlighted=0;
            var from=[0,0];
            var letters=['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
            var digits=['8', '7', '6', '5', '4', '3', '2', '1'];
            function clickMe(element, x,y) {
                if (element.className=='highlight') {
                    highlighted--;
                    element.className='initial';
                }
                else {
                    element.className='highlight';
                    if (highlighted==1) {
                        document.getElementById("commandFromUser").value = letters[from[0]]+digits[from[1]]+letters[x]+digits[y];
                        document.getElementById("move").submit();
                    }
                    else {
                        highlighted++;
                        from[0]=x;
                        from[1]=y;
                    }
                }
            }
        </script>

        <table modelAttribute="board" >
            <c:forEach var="row" items="${board}" varStatus="county">
            <tr>
                 <c:forEach var="item" items="${row}" varStatus="countx">
                 <td style="width:20px" align="center" class="initial" onClick="clickMe(this, ${countx.index}, ${county.index})" >
                    <c:out value="${item}"/>
                 </td>
                 </c:forEach>
            </tr>
            </c:forEach>
        </table>