<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Askareet">
<!-- Page Content !-->
<div class="col-md-8">
    <%@include file="include/memo_div.jsp"%>
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