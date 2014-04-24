<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Luokat">
<!-- Page Content !-->
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
                    <a href="/FlexDo/category?delete=${user.id}">
                        <button type="button" class="close" aria-hidden="true" formaction="/FlexDo/category?delete=${user.id}">&times;</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</t:base>