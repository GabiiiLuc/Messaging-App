package com.example.proiectextins.repository;

import com.example.proiectextins.domain.User;


import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UtilizatorDbRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;

    public UtilizatorDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public User findOne(Long id) {
        for (User f : (Iterable<User>) findAll()) {
            if (Objects.equals(f.getID(), id))
                return f;
        }
        return null;
    }

    public User findAfterUsername(String username) {
        for (User u : (Iterable<User>) findAll()) {
            if (Objects.equals(u.getUsername(), username))
                return u;
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User utilizator = new User(username, firstName, lastName);
                utilizator.setID(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {

        String sql = "insert into users (username, first_name, last_name ) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long aLong) {
        String sql = "delete from users where id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, aLong);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User entity) {
        String sql = "update users set first_name=?, last_name=? where username=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


