<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Ylläpito">

<!-- Page Content !-->
<div class="col-md-8">
    <table class="table table-striped">
        <thead><tr><th>Käyttäjät</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value='${user.name}' escapeXml='true'/></td>
                <td>
                    <c:if test="${user.id != 1}">
                    <a href="/FlexDo/admin?delete=${user.id}">
                        <button type="button" class="close" aria-hidden="true" formaction="/FlexDo/admin?delete=${user.id}">&times;</button>
                    </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Sidebar !-->
<div class="col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-target="#flexdo-task-collapse" href="#">
                    Uusi käyttäjä <span class="caret"></span>
                </a>
            </h4>
        </div>
        <div class="panel-body collapse in" id="flexdo-task-collapse">
            <form role="form" action="admin" method="POST">
                <div class="form-group">
                    <label for="inputName">Nimi</label>
                    <input type="text" id="inputName" name="inputName" class="form-control" placeholder="Nimi">
                </div>
                <div class="form-group">
                    <label for="inputPassword">Salasana</label>
                    <input id="inputPassword" name="inputPassword" class="form-control" placeholder="Salasana">
                </div>
                <button type="submit" class="btn btn-info btn-block">Luo</button>
            </form>
        </div>
    </div>
</div>
</t:base>