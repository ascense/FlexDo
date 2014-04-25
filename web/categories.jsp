<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Luokat">
<!-- Page Content !-->
<div class="col-md-12">
    <div class="well well-sm">
        <form class="form-horizontal" role="form" action="category" method="POST">
            <div class="form-group">
                <label for="inputName" class="col-sm-2 control-label">Uusi Luokka</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="inputName" id="categoryname" placeholder="Luokan nimi">
                </div>
                <div class="col-sm-2">
                    <button type="submit" class="btn btn-primary btn-block">Luo</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="col-md-12">
    <table class="table table-striped">
        <tbody>
            <c:forEach var="category" items="${categories}">
            <tr>
                <td>
                    <a href="/FlexDo/category?id=${category.id}">
                        <c:out value='${category.name}' escapeXml='true'/>
                    </a>
                </td>
                <td>
                    <a href="/FlexDo/delete?catid=${category.id}">
                        <button type="button" class="close" aria-hidden="true" formaction="/FlexDo/delete?catid=${category.id}">&times;</button>
                    </a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</t:base>