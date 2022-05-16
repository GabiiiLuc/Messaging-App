package com.example.proiectextins.domain;

public class Account extends Entity<Long>{
    private User user;
    private String password;
    private String salt;

    public Account(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getSalt(){
        return salt;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
