<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="/WEB-INF/tlds/functions" %>
<t:base pageTitle="${title}">
<!-- Page Content !-->
<div class="col-md-12">
    <div class="panel panel-default">
        <form role="form" action="edit" method="POST">
            <input type="hidden" name="id" value="${id}">
            <div class="panel-heading" id="title-edit">
                <h4 class="panel-title">
                    <input type="text" class="form-control" name="inputName" id="inputName" value="<c:out value='${inputName}' escapeXml='true'/>">
                </h4>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <textarea id="inputContent" name="inputContent" class="form-control" rows="3"><c:out value='${inputContent}' escapeXml='true'/></textarea>
                </div>
                <c:if test="${not empty inputPriority}">
                    <div class="form-group">
                        <label class="control-label" for="inputPriority">Prioriteetti</label>
                        <input type="number" id="inputPriority" name="inputPriority" class="form-control" value="${inputPriority}">
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="control-label" for="inputCategory">Luokka</label><br />
                    <c:forEach var="category" items="${categories}">
                        <label class="checkbox-inline">
                            <input type="checkbox" id="inputCategory${category.id}" value="${category.id}" <c:if test="${f:has_category(memo, category)}">checked=""</c:if>>
                            <c:out value='${category.name}' escapeXml='true'/>
                        </label>
                    </c:forEach>
                </div>
                <div class="col-sm-6">
                    <button type="submit" class="btn btn-primary btn-block">Tallenna Muutokset</button>
                </div>
                <div class="col-sm-4">
                    <a href="index">
                        <button type="button" class="btn btn-warning btn-block">Peruuta</button>
                    </a>
                </div>
                <div class="col-sm-2">
                    <a href="delete?id=${id}">
                        <button type="button" class="btn btn-danger btn-block">Poista</button>
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>
</t:base>