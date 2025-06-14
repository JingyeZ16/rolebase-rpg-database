<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Create Player</title>
</head>
<body>
<h1>Character Details</h1>
<table border="1">
    <tr>
        <th>UserName</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Race</th>
        <th>Clan</th>
        <c:forEach items="${stats}" var="stat">
            <th>${stat.statistics.name}</th>
        </c:forEach>
        <th>Update Stats</th>

    </tr>
    <tr>
        <td>${character.player.fullName}</td>
        <td>${character.firstName}</td>
        <td>${character.lastName}</td>
        <td>${character.race.name}</td>
        <td>${character.clan.clan}</td>
        <c:forEach items="${stats}" var="stat">
            <td>${stat.value}</td>
        </c:forEach>
        <td><a href="updatecharacter?firstname=${character.firstName}&lastname=${character.lastName}&characterid=${character.characterID}">Update</a></td>
    </tr>
</table>
<br><br>
<h1>Character Equipments</h1>
<table border="1">
    <tr>
        <th>Slot</th>
        <th>Item Name</th>
        <c:forEach items="${allstats}" var="statName">
            <th>${statName}</th>
        </c:forEach>
    </tr>

    <c:forEach items="${equipmentStatRows}" var="row">
        <tr>
            <td>${row.slot}</td>
            <td>${row.itemName}</td>
            <c:forEach items="${allstats}" var="statName">
                <td>${row.statValues[statName]}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

</body>
</html>
