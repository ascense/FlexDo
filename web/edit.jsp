<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="${title}">
<!-- Page Content !-->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                ${name}
                <c:if test="${priority >= 0}"><span class="badge">${priority}</span></c:if>
                <button type="button" class="close" aria-hidden="true">&times;</button>
            </h4>
        </div>
        <div class="panel-body">
            ${content}
        </div>
    </div>
</div>
</t:base>