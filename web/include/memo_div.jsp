<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="list-group">
    <c:forEach var="memo" items="${memos}">
        <div class="list-group-item">
            <h4 class="list-group-item-heading">
                <a href="/FlexDo/edit?id=${memo.id}"><c:out value='${memo.name}' escapeXml='true'/></a>
                <c:if test="${not empty memo.task.priority}">
                <span class="badge"><c:out value='${memo.task.priority}' escapeXml='true'/></span>
                </c:if>
                <small>
                    &nbsp;
                    <c:forEach var="category" items="${memo.categories}">
                    <a href="#" class="label label-info"><c:out value='${category.name}' escapeXml='true'/></a>
                    </c:forEach>
                </small>
                <a href="/FlexDo/delete?id=${memo.id}">
                <button type="button" class="close" aria-hidden="true">&times;</button>
                </a>
            </h4>
            <p class="list-group-item-text">
                <c:out value='${memo.content}' escapeXml='true'/>
            </p>
        </div>
    </c:forEach>
</div>