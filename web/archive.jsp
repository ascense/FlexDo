<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Arkistoidut Tehtävät">
<!-- Page Content !-->
<div class="col-md-12">
    <table class="table table-striped">
        <tbody>
        <thead><tr><th>Nimi</th><th>Suljettu</th><th></th></tr></thead>
        <c:forEach var="memo" items="${memos}">
            <tr>
                <td>
                    <a href="/FlexDo/edit?id=${memo.id}">
                        <c:out value='${memo.name}' escapeXml='true'/>
                    </a>
                </td>
                <td>
                    <c:out value='${memo.task.closed}' escapeXml='true'/>
                </td>
                <td>
                    <a href="/FlexDo/delete?id=${memo.id}">
                    <button type="button" class="close" aria-hidden="true">&times;</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</t:base>