
package com.example.proiectextins.repository;

        import com.example.proiectextins.domain.Account;
        import com.example.proiectextins.domain.User;


        import java.sql.*;
        import java.util.HashSet;
        import java.util.Objects;
        import java.util.Set;

 public class AccountDbRepository implements Repository<Long, Account> {
    private String url;
    private String username;
    private String password;
    private UtilizatorDbRepository userRepo;

    public AccountDbRepository(String url, String username, String password, UtilizatorDbRepository userRepo) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userRepo = userRepo;
    }
    @Override
    public Account findOne(Long id) {
        for (Account f : (Iterable<Account>) findAll()) {
            if (Objects.equals(f.getID(), id))
                return f;
        }
        return null;
    }

    @Override
    public Iterable<Account> findAll() {
        Set<Account> accounts = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from accounts");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");

                Account account = new Account(userRepo.findAfterUsername(username), password);
                account.setID(id);
                account.setSalt(salt);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account save(Account entity) {

        String sql = "insert into accounts (username, password, salt) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getUser().getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getSalt());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account delete(Long aLong) {
        String sql = "delete from accounts where id = ?";

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
    public Account update(Account entity) {
        return null;
    }
}



