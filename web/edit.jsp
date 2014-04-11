<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="${title}">
<!-- Page Content !-->
<div class="col-md-12">
    <div class="panel panel-default">
        <form role="form" action="edit" method="POST">
            <input type="hidden" name="id" value="${id}">
            <div class="panel-heading" id="title-edit">
                <h4 class="panel-title">
                    <c:if test="${priority >= 0}"><span class="badge">${priority}</span></c:if>
                    <button type="button" class="close" aria-hidden="true">&times;</button>
                    <input type="text" class="form-control" name="inputName" id="inputName" value="<c:out value='${name}' escapeXml='true'/>">
                </h4>
            </div>
            <div class="panel-body">
                <textarea id="inputContent" name="inputContent" class="form-control" rows="3"><c:out value='${content}' escapeXml='true'/></textarea>
                <br />
                <div class="col-sm-4">
                    <a href="index">
                        <button type="button" class="btn btn-warning btn-block">Peruuta</button>
                    </a>
                </div>
                <div class="col-sm-8">
                    <button type="submit" class="btn btn-primary btn-block">Tallenna Muutokset</button>
                </div>
            </div>
        </form>
    </div>
</div>
</t:base>