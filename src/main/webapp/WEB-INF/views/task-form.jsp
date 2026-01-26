<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Form</title>
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/minimal-style.css">
</head>
<body>
<div class="container">
    <h1>📝 Todo Application</h1>
    <br>
    <br>
    <h3>✍🏻 Task Form</h3>
    <br>
    
    <form method="post" action="tasks">
        
        <c:choose>
            <c:when test="${not empty task}">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${task.id}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="add">
            </c:otherwise>
        </c:choose>
        
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${task.title}">
        </div>
        
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4">${task.description}</textarea>
        </div>
        
        <div class="form-group">
            <label>
                <input type="checkbox" id="completed" name="completed" value="${task.completed}" ${task.completed ? 'checked' : ''}>Mark as complete
            </label>
        </div>
        
        <div class="form-action">
            <button type="submit">Save</button>
            <a class="btn" href="tasks?action=list">Cancel</a>
        </div>
        
    </form>
    
</div>
</body>
</html>