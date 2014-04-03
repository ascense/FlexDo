<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:base pageTitle="Kirjaudu Sisään">
<div class="col-md-12">
    <form class="form-horizontal" role="form" action="login" method="POST">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Käyttäjätunnus</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="inputName" id="username" placeholder="Käyttäjätunnus" value="${username}">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Salasana</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="inputPassword" id="password" placeholder="Salasana" value="${password}">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="remember"> Muista kirjautuminen
                    </label>
                </div>
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