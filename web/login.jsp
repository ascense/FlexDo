<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Kirjaudu Sisään">
<div class="col-md-12">
    <form class="form-horizontal" role="form" action="login" method="POST">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Käyttäjätunnus</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="inputName" id="username" placeholder="Käyttäjätunnus" value="<c:out value='${inputName}' escapeXml='true'/>">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Salasana</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="inputPassword" id="password" placeholder="Salasana" value="<c:out value='${inputPassword}' escapeXml='true'/>">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Kirjaudu Sisään</button>
            </div>
        </div>
    </form>
</div>
</t:base>