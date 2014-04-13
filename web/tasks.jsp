<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Askareet">
<!-- Page Content !-->
<div class="col-md-8">
    <div class="list-group">
        <c:forEach var="memo" items="${memos}">
            <div class="list-group-item">
                <h4 class="list-group-item-heading">
                    <a href="/FlexDo/edit?id=${memo.id}"><c:out value='${memo.name}' escapeXml='true'/></a>
                    <span class="badge"><c:out value='${memo.task.priority}' escapeXml='true'/></span>
                    <small>
                        &nbsp;
                        <c:forEach var="category" items="${memo.categories}">
                        <a href="#" class="label label-info"><c:out value='${category.name}' escapeXml='true'/></a>
                        </c:forEach>
                    </small>
                    <a href="/FlexDo/delete?id=${memo.id}">
                    <button type="button" class="close" aria-hidden="true" formaction="/FlexDo/delete?id=${memo.id}">&times;</button>
                    </a>
                </h4>
                <p class="list-group-item-text">
                    <c:out value='${memo.content}' escapeXml='true'/>
                </p>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Sidebar !-->
<div class="col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-target="#flexdo-task-collapse" href="#">
                    Uusi askare <span class="caret"></span>
                </a>
            </h4>
        </div>
        <div class="panel-body collapse in" id="flexdo-task-collapse">
            <form role="form" action="edit" method="POST">
                <div class="form-group">
                    <label for="inputName">Nimi</label>
                    <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Nimi">
                </div>
                <div class="form-group">
                    <label for="inputContent">Kuvaus</label>
                    <textarea id="inputContent" name="inputContent" class="form-control" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <label for="inputPriority">Prioriteetti</label>
                    <input type="number" id="inputPriority" name="inputPriority" class="form-control">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Luo</button>
            </form>
        </div>
    </div>
    <%@include file="include/category_pane.jsp"%>
</div>
</t:base>