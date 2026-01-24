<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>📝 Todo Application</h1>
    
    <div class="add-task">
        <a href="${pageContext.request.contextPath}/tasks?action=add" class="btn btn-primary">+ Add New Task</a>
    </div>
    
    <div class="task-list">
        <c:choose>
            <c:when test="${not empty taskList}">
                <c:forEach var="task" items="${taskList}">
                    <div class="task-item ${task.completed ? 'completed' : ''}">
                        <div class="task-content">
                            <h3>${task.title}</h3>
                            <p>${empty task.description ? '' : task.description}</p>
                        </div>
                        <div class="task-actions">
                            <a href="${pageContext.request.contextPath}/tasks?action=toggle&id=${task.id}" class="btn btn-toggle">
                                    ${task.completed ? '✓ Completed' : '○ Mark Complete'}
                            </a>
                            <a href="${pageContext.request.contextPath}/tasks?action=edit&id=${task.id}" class="btn btn-edit">Edit</a>
                            <a href="${pageContext.request.contextPath}/tasks?action=delete&id=${task.id}"
                               class="btn btn-delete"
                               onclick="return confirm('Are you sure you want to delete this task?')">Delete</a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-tasks">
                    <p>No tasks yet. Add your first task!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>