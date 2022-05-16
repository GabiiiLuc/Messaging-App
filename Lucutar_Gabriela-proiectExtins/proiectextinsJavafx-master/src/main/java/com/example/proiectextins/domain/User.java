package com.example.proiectextins.domain;

import java.util.Objects;

public class User extends Entity<Long>{
    private String username;
    private String firstName;
    private String lastName;

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {

        return  firstName + " " + lastName ;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getFirstName(),
                user.getFirstName()) && Objects.equals(getLastName(),
                user.getLastName());
    }

    public int hashCode() {
        return Objects.hash(getUsername(), getFirstName(),getLastName());
    }
}
