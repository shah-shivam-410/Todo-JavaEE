package org.example.todojavaee.dao;

import org.example.todojavaee.model.Task;
import org.example.todojavaee.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

    public List<Task> getAllTask() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "select id, title, description, completed, created_at from tasks order by created_at desc, id asc";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery();
        ) {
            while (rs.next() != false) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public Task getTaskById(Integer id) throws SQLException {
        Task task = null;
        String sql = "select id, title, description, completed, created_at from tasks where id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next() != false) {
                task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    public boolean addTask(Task task) throws SQLException {
        int flag = 0;
        String sql = "insert into tasks(title, description, completed) values  (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setBoolean(3, task.getCompleted());
            flag = pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }

    public boolean updateTask(Task task) throws SQLException {
        int flag = 0;
        String sql = "update tasks set title = ?, description = ?, completed = ? where id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setBoolean(3, task.getCompleted());
            pst.setInt(4, task.getId());
            flag = pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }

    public boolean deleteTask(Integer id) throws SQLException {
        int flag = 0;
        String sql = "delete from tasks where id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setInt(1, id);
            flag = pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }

    public boolean toggleTask(Integer id) throws SQLException {
        int flag = 0;
        String sql = "update tasks set completed = not completed where id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setInt(1, id);
            flag = pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }

}
