<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Find a User</title>
</head>
<body>
<form action="findcharacters" method="post">
    <h1>Search for Characters by Email</h1>
    <p>
        <label for="email">Email</label>
        <input id="email" name="email" value="${fn:escapeXml(param.email)}">
    </p>
    <p>
        <label for="name">Name(Partial Match)</label>
        <input id="name" name="name" placeholder="e.g. 'Al', 'Bob'" value="${fn:escapeXml(param.name)}">
    </p>
    <p>
        <label for="sortBy">Sort by:</label>
        <select id="sortBy" name="sortBy">
            <option value="firstName">First Name</option>
            <option value="lastName">Last Name</option>
            <option value="race">Race</option>
            <option value="clan">Clan</option>
        </select>
    </p>
    <p>
        <input type="submit">
        <br/><br/><br/>
        <span id="responseMessage"><b>${messages.response}</b></span>
    </p>
</form>
<br/>
<div id="userCreate"><a href="createplayer">Create Player</a></div>
<br/>
<h1>Matching Characters</h1>
<table border="1">
    <tr>
        <th>User Name</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Race</th>
        <th>Clan</th>
        <th>Detail</th>
        <th>Delete Character</th>
    </tr>
    <c:forEach items="${Characters}" var="Characters">
        <tr>
            <td><c:out value="${Characters.getPlayer().getFullName()}"/></td>
            <td><c:out value="${Characters.getFirstName()}"/></td>
            <td><c:out value="${Characters.getLastName()}"/></td>
            <td><c:out value="${Characters.getRace().getName()}"/></td>
            <td><c:out value="${Characters.getClan().getClan()}"/></td>
            <td>
                <a href="detail?characterid=<c:out value="${Characters.getCharacterID()}"/>">Details</a>
            </td>

            <td>
                <a href="deletecharacter?firstname=<c:out value="${Characters.getFirstName()}"/>&lastname=<c:out value="${Characters.getLastName()}"/>">Delete</a>
            </td>


        </tr>
    </c:forEach>
</table>
</body>
</html>
