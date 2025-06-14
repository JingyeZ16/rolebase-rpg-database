<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Create Player</title>
</head>
<body>
<h1>Create Player</h1>
<form action="createplayer" method="post">
    <p>
        <label for="fullname">FullName</label>
        <input id="fullname" name="fullname" value="">
    </p>

    <p>
        <label for="email">Email</label>
        <input id="email" name="email" value="">
    </p>
    <p>
        <input type="submit">
    </p>
</form>
<br/><br/>
<p>
    <span id="responseMessage"><b>${messages.response}</b></span>
</p>
</body>
</html>
