package com.example.proiectextins.repository;

import com.example.proiectextins.domain.FriendshipRequest;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FriendshipRequestDbRepository implements Repository<Long, FriendshipRequest>{
    private String url;
    private String username;
    private String password;


    public FriendshipRequestDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    @Override
    public FriendshipRequest findOne(Long id) {
        // for (Friendship f : (Iterable<Friendship>) findAll()) {
        //    if (Objects.equals(f.getFriend1(), id) || Objects.equals(f.getFriend2(), id))
        //         return f;
        // }
        return null;
    }

    @Override
    public Iterable<FriendshipRequest> findAll() {
        Set<FriendshipRequest> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendship_requests");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String friend1 = resultSet.getString("from_user");
                String friend2 = resultSet.getString("to_user");
                Date friendshipDate = resultSet.getDate("date");
                String status = resultSet.getString("status");

                FriendshipRequest friendship = new FriendshipRequest(friend1, friend2, friendshipDate, status);
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
    public FriendshipRequest save(FriendshipRequest entity) {

        String sql = "insert into friendship_requests (from_user, to_user, date, status) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFriend1());
            ps.setString(2, entity.getFriend2());
            ps.setDate(3, entity.getFriendshipDate());
            ps.setString(4, entity.getFriendshipStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FriendshipRequest delete(Long id) {
        String sql = "delete from friendship_requests where id=?";

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
    public FriendshipRequest update(FriendshipRequest entity) {
        String sql = "update friendship_requests set status=? where from_user=? and to_user=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFriendshipStatus());
            ps.setString(2, entity.getFriend1());
            ps.setString(3, entity.getFriend2());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

