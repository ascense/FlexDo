<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Luokka: ${category.name}">
<!-- Page Content !-->
<div class="col-md-8">
    <%@include file="include/memo_div.jsp"%>
</div>

<!-- Sidebar !-->
<div class="col-md-4">
    <%@include file="include/category_pane.jsp"%>
</div>
</t:base>