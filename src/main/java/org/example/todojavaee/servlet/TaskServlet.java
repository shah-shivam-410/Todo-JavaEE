package org.example.todojavaee.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.todojavaee.dao.TaskDao;
import org.example.todojavaee.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskDao taskDao;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskDao = new TaskDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //log("-------Inside TaskServlet doGet method--------");

        String action = req.getParameter("action");
        if (action == null) action = "list";
//        if(action != null && action.equalsIgnoreCase("list")) {
//            listTasks(req, resp);
//        }
//
//        if(action == null) {
//            listTasks(req, resp);
//        }

        switch (action) {
            case "list" -> listTasks(req, resp);
            default -> listTasks(req, resp);
        }

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



}
