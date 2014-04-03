<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Askareet">
<!-- Page Content !-->
<div class="col-md-8">
    <div class="list-group">
        <c:forEach var="task" items="${tasks}">
            <div class="list-group-item">
                <h4 class="list-group-item-heading">
                    <a href="/FlexDo/edit?id=${task.memo.id}">${task.memo.name}</a>
                    <small>
                        &nbsp;
                        <c:forEach var="category" items="${task.memo.categories}">
                        <a href="#" class="label label-info">${category.name}</a>
                        </c:forEach>
                    </small>
                    <a href="/FlexDo/delete?id=${task.memo.id}">
                    <button type="button" class="close" aria-hidden="true" formaction="/FlexDo/delete?id=${task.memo.id}">&times;</button>
                    </a>
                </h4>
                <p class="list-group-item-text">
                    ${task.memo.content}
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
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-target="#flexdo-class-collapse" href="#">
                    Luokat <span class="caret"></span>
                </a>
            </h4>
        </div>
        <div class="panel-body collapse in" id="flexdo-class-collapse">
            <ul class="list-unstyled">
                <c:forEach var="category" items="${categories}">
                <li><a href="#">${category.name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</t:base>