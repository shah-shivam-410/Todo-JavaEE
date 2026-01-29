package org.example.todojavaee.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.todojavaee.dao.TaskDao;
import org.example.todojavaee.dao.TaskJPADao;
import org.example.todojavaee.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    // private TaskDao taskDao;
    private TaskJPADao taskDao;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskDao = new TaskJPADao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list" -> listTasks(req, resp);
            case "toggle" -> toogleTask(req, resp);
            case "delete" -> deleteTask(req, resp);
            case "add", "update" -> showTaskForm(req, resp);
            default -> listTasks(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equalsIgnoreCase("add")) {
            saveTask(req, resp);
        } else if (action.equalsIgnoreCase("update")) {
            updateTask(req, resp);
        }
    }

    private void showTaskForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equalsIgnoreCase("update")) {
            Integer id = Integer.valueOf(req.getParameter("id"));
            try {
                Task task = taskDao.getTaskById(id);
                req.setAttribute("task", task);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/task-form.jsp").forward(req, resp);
    }

    private void listTasks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = null;
        try {
            tasks = taskDao.getAllTask();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("pageTitle", "Todo List");
        req.setAttribute("taskList", tasks);
        req.getRequestDispatcher("/WEB-INF/views/task-list.jsp").forward(req, resp);
    }

    private void saveTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Boolean completed = req.getParameter("completed") != null ? true : false;

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        try {
            taskDao.addTask(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    private void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        Boolean completed = req.getParameter("completed") != null ? true : false;

        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        try {
            taskDao.updateTask(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    private void toogleTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            taskDao.toggleTask(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            taskDao.deleteTask(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }


}
