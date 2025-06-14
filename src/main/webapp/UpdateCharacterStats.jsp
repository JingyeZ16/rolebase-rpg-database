<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Update Character Stats</title>
</head>
<body>
<h1>Update Character Stats</h1>

<form action="updatecharacter" method="post">
        <p>
            <label for="firstname">First Name</label>
            <input id="firstname" name="firstname" value="${fn:escapeXml(param.firstname)}">
        </p>
        <p>
            <label for="lastname">Last Name</label>
            <input id="lastname" name="lastname" value="${fn:escapeXml(param.lastname)}">
        </p>
        <input type="hidden" id="characterid" name="characterid" value="${fn:escapeXml(param.characterid)}">

        <c:forEach items="${characterstats}" var="stats">
            <p>
                <label for="${stats.statistics.name}">${stats.statistics.name}: </label>
                <input type="text" id="${stats.statistics.name}" name="${stats.statistics.name}"
                       value="${fn:escapeXml(not empty param[stats.statistics.name]
                                  ? param[stats.statistics.name]
                                  : stats.value)}">
            </p>
        </c:forEach>
    <p>

        <input type="submit">
    </p>
    <br/><br/>
    <p>
        <span id="responseMessage"><b>${messages.response}</b></span>
    </p>

</form>

</body>
</html>
