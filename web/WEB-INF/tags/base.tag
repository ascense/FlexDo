<%@tag description="Generic template for FlexDo pages" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle} - FlexDo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#flexdo-navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/FlexDo/index">FlexDo</a>
        </div>
        <div class="collapse navbar-collapse" id="flexdo-navbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Askareet</a></li>
                <li><a href="#">Muistiot</a></li>
                <li><a href="#">Luokat</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Etsi...">
                    <button type="submit" class="btn btn-default">Hae</button>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="login?logout">Kirjaudu Ulos</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>${pageTitle}</h2>
        </div>

        <c:if test="${errorMsg != null}">
        <div class="alert alert-danger">Virhe! ${errorMsg}</div>
        </c:if>

        <jsp:doBody/>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>