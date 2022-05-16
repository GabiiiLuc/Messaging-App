package com.example.proiectextins.domain;

import java.sql.Date;
import java.util.AbstractMap;
import java.util.Objects;

public class FriendshipRequest extends Entity<Long>{
    private AbstractMap.SimpleEntry<String, String> friendship;
    private Date friendshipDate;
    private String friendshipStatus;

    public FriendshipRequest(String id1, String id2, Date friendshipDate, String friendshipStatus) {
        friendship = new AbstractMap.SimpleEntry<String, String>(id1,id2);
        this.friendshipDate = friendshipDate;
        this.friendshipStatus = friendshipStatus;
    }

    public AbstractMap.SimpleEntry<String, String> getFriendship() {
        return this.friendship;
    }

    public String getFriend1(){
        return friendship.getKey();
    }

    public String getFriend2(){
        return friendship.getValue();
    }

    public Date getFriendshipDate(){ return friendshipDate; }

    public String getFriendshipStatus(){
        return friendshipStatus;
    }

    public void setFriendshipStatus(String friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public void setFriendshipDate(Date friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    @Override
    public String toString() {
        return "Username friend 1: " + friendship.getKey() + " Username friend 2: " + friendship.getValue() + " on: " + friendshipDate + " status: " + friendshipStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;

        FriendshipRequest friendship = (FriendshipRequest) o;

        return Objects.equals(getFriend1(),
                friendship.getFriend1()) && Objects.equals(getFriend2(),
                friendship.getFriend2());
    }

    public int hashCode() {
        return Objects.hash(getFriend1(),getFriend2());
    }
}
