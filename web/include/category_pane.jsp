<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-target="#flexdo-class-collapse" href="#">
                Luokat <span class="caret"></span>
            </a>
        </h4>
    </div>
    <div class="panel-body collapse<c:if test="${not empty categories}"> in</c:if>" id="flexdo-class-collapse">
        <ul class="list-unstyled">
            <c:forEach var="category" items="${categories}">
            <li><a href="#"><c:out value='${category.name}' escapeXml='true'/></a></li>
            </c:forEach>
        </ul>
    </div>
</div>