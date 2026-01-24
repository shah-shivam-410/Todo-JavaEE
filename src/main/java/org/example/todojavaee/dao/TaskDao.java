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
        String sql = "select id, title, description, completed, created_at from tasks order by created_at desc";

        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
        ) {
            while(rs.next() != false) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                tasks.add(task);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;

    }

}
