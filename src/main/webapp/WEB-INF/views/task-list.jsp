<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo Application</title>
<%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">--%>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/minimal-style.css">
</head>
<body>
<div class="container">
    <h1>📝 Todo Application</h1>
    
    <div class="add-task">
        <a href="${pageContext.request.contextPath}/tasks?action=add">
            <button>➕ Add New Task</button>
        </a>
    </div>
    
    <br>
    
    <div>
        <c:choose>
            <c:when test="${not empty taskList}">
                <table>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Completed?</th>
                        <th>Created at</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach var="task" items="${taskList}">
                        <tr>
                            <td>${task.title}</td>
                            <td>${task.description}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/tasks?action=toggle&id=${task.id}">
                                    <button>${task.completed ? "✅" : "⭕"}</button>
                                </a>
                            </td>
                            <td>${task.createdAt}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/tasks?action=update&id=${task.id}">
                                    <button>Edit</button>
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/tasks?action=delete&id=${task.id}"
                                   onclick="return confirm('Are you sure you want to delete this task?')">
                                   <button>Delete</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <div>
                    <p>No tasks yet. Add your first task!</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
   
</div>
</body>
</html>