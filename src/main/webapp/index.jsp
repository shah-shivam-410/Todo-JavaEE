<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo App - Welcome</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #989898 0%, #000000 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        .welcome-box {
            background: white;
            padding: 50px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            text-align: center;
        }
        h1 { color: #333; margin-bottom: 20px; }
        .btn {
            display: inline-block;
            padding: 15px 30px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            margin-top: 20px;
            transition: background 0.3s;
        }
        .btn:hover { background: #5568d3; }
    </style>
</head>
<body>
<div class="welcome-box">
    <h1>📝 Todo Application</h1>
    <p>Manage your tasks efficiently with Keycloak authentication</p>
    <a href="${pageContext.request.contextPath}/login" class="btn">Login with Keycloak</a>
</div>
</body>
</html>