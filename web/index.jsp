<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Askareet">
<!-- Page Content !-->
<div class="col-md-8">
    <div class="list-group">
        <c:forEach var="memo" items="${memos}">
            <div class="list-group-item">
                <h4 class="list-group-item-heading">
                    <a href="#">{memo.name}</a>
                    <small>
                        &nbsp;
                        <a href="#" class="label label-info">Luokka A</a>
                    </small>
                    <button type="button" class="close" aria-hidden="true">&times;</button>
                </h4>
                <p class="list-group-item-text">
                    {memo.content}
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
            <jsp:include page="include/newtask.jsp" />
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
                <li>Luokka A</li>
                <li>Luokka B</li>
            </ul>
        </div>
    </div>
</div>
</t:base>