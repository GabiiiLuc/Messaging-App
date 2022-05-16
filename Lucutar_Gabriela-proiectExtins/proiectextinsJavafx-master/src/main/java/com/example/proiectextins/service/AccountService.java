package com.example.proiectextins.service;

import com.example.proiectextins.domain.Account;
import com.example.proiectextins.domain.Friendship;
import com.example.proiectextins.domain.User;
import com.example.proiectextins.domain.validators.AccountValidator;
import com.example.proiectextins.repository.Repository;
import com.example.proiectextins.utils.HashPassword;
/*import com.example.proiectextins.context.annotation.Bean;
import com.example.proiectextins.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.proiectextins.security.crypto.password.PasswordEncoder;*/


import java.util.Locale;
import java.util.Objects;

public class AccountService {
    private UserService userService;
    private Repository<Long, Account> repo;
    private AccountValidator accountValidator;

    public AccountService(UserService userService, Repository<Long, Account> repo, AccountValidator accountValidator) {
        this.repo = repo;
        this.userService = userService;
        this.accountValidator = accountValidator;
    }

    public AccountService() {
    }

        public Account addAccount(Account account) {
        String salt = HashPassword.generateSalt(512).get();
        String key = HashPassword.hashPassword(account.getPassword(), salt).get();
        account.setPassword(key);
        account.setSalt(salt);
        return repo.save(account);
    }

    public Iterable<Account> getAll() {
        return repo.findAll();
    }

    public Account deleteAccount(String username, String password) {

        for (Account a : repo.findAll()) {
            if (Objects.equals(a.getUser().getUsername(), username) && HashPassword.verifyPassword(password, a.getPassword(), a.getSalt())) {
                repo.delete(a.getID());
            }
        }
        return null;
    }

    public Account findOne(Long id) {
        return repo.findOne(id);
    }

    public boolean login(String username, String password){
       for(Account a : repo.findAll()){
           if(Objects.equals(a.getUser().getUsername(), username) && HashPassword.verifyPassword(password, a.getPassword(), a.getSalt()))
               return true;
       }
       return false;
    }

    public String createAccount(String firstName, String lastName, String username, String password) {
       try {
           boolean alreadyexists = false;
           accountValidator.validate(username, repo);
           firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
           lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
           User user = new User(username, firstName, lastName);
           for(User u : userService.getAll()){
               if(Objects.equals(u.getUsername(), username)) {
                   alreadyexists= true;
                   userService.updateUser(user);
               }
           }
           if(!alreadyexists) {
               userService.addUser(user);
           }
           Account account = new Account(user, password);
           addAccount(account);
       } catch (Exception e){
           return e.getMessage();
       }
       return "Account created!";
    }

    public boolean verifyIfUserHasAccount(String username) {
        for(Account a : repo.findAll()) {
            if(Objects.equals(a.getUser().getUsername(), username))
                return true;
        }
        return false;
    }
}
