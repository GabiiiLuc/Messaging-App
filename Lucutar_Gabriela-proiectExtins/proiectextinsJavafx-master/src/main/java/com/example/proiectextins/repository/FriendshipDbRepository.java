package com.example.proiectextins.repository;

import com.example.proiectextins.domain.Friendship;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDbRepository implements Repository<Long, Friendship>{
    private String url;
    private String username;
    private String password;


    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    @Override
    public Friendship findOne(Long id) {
       // for (Friendship f : (Iterable<Friendship>) findAll()) {
        //    if (Objects.equals(f.getFriend1(), id) || Objects.equals(f.getFriend2(), id))
       //         return f;
       // }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String friend1 = resultSet.getString("username_1");
                String friend2 = resultSet.getString("username_2");
                Date friendshipDate = resultSet.getDate("date");

                Friendship friendship = new Friendship(friend1, friend2, friendshipDate);
                friendship.setID(id);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship entity) {

        String sql = "insert into friendships (username_1, username_2, date) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFriend1());
            ps.setString(2, entity.getFriend2());
            ps.setDate(3, entity.getFriendshipDate());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship delete(Long id) {
        String sql = "delete from friendships where id=?";

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
    public Friendship update(Friendship entity) {
        return null;
    }
}

