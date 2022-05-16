package com.example.proiectextins.repository;

import com.example.proiectextins.domain.Message;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MessageDbRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;

    public MessageDbRepository(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Message findOne(Long id) {
        // for (Friendship f : (Iterable<Friendship>) findAll()) {
        //    if (Objects.equals(f.getFriend1(), id) || Objects.equals(f.getFriend2(), id))
        //         return f;
        // }
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String from = resultSet.getString("from_user");
                String to = resultSet.getString("to_user");
                String text = resultSet.getString("message_content");
                Date date = resultSet.getDate("send_date");

                Message message = new Message(from, to, text, date);
                message.setID(id);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message save(Message entity) {

        String sql = "insert into messages (from_user, to_user, message_content, send_date) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFrom());
            ps.setString(2, entity.getTo());
            ps.setString(3, entity.getMessage());
            ps.setDate(4, entity.getDate());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long id) {
        String sql = "delete from messages where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }
}
