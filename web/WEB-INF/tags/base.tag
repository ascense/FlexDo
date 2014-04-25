<%@tag description="Generic template for FlexDo pages" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle"%>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value='${pageTitle}' escapeXml='true'/> - FlexDo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#flexdo-navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/FlexDo/index">FlexDo</a>
            <c:if test="${username != null}">
                <p class="visible-xs visible-sm small navbar-text"><c:out value="${username}" escapeXml="true"/></p>
            </c:if>
        </div>
        <div class="collapse navbar-collapse" id="flexdo-navbar">
            <c:if test="${username != null}">
                <ul class="nav navbar-nav">
                    <li<c:if test="${pageTitle == 'Askareet'}"> class="active"</c:if>><a href="/FlexDo/index">Askareet</a></li>
                    <li<c:if test="${pageTitle == 'Muistiot'}"> class="active"</c:if>><a href="/FlexDo/index?memos">Muistiot</a></li>
                    <li<c:if test="${pageTitle == 'Luokat'}"> class="active"</c:if>><a href="/FlexDo/categories">Luokat</a></li>
                    <li<c:if test="${pageTitle == 'Arkistoidut Tehtävät'}"> class="active"</c:if>><a href="/FlexDo/index?archive">Arkisto</a></li>
                    <c:if test="${admin != null}">
                    <li<c:if test="${pageTitle == 'Ylläpito'}"> class="active"</c:if>><a href="/FlexDo/admin">Ylläpito</a></li>
                    </c:if>
                </ul>
                <form class="navbar-form navbar-left" role="search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Etsi..." disabled>
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-default" disabled>Hae</button>
                        </span>
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="login?logout">Kirjaudu Ulos</a></li>
                </ul>
                <p class="small navbar-text navbar-right"><c:out value="${username}" escapeXml="true"/></p>
            </c:if>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row col-md-12">
        <div class="col-md-12">
            <h2><c:out value='${pageTitle}' escapeXml='true'/></h2>
        </div>

        <c:if test="${errorMsg != null}">
        <div class="col-md-12">
            <div class="panel panel-danger">
                <div class="panel-heading"><h3 class="panel-title">${errorMsg}</h3></div>
            </div>
        </div>
        </c:if>
        <c:if test="${infoMsg != null}">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">${infoMsg}</div>
            </div>
        </div>
        </c:if>
    </div>
    <div class="row col-md-12">
    <jsp:doBody/>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>