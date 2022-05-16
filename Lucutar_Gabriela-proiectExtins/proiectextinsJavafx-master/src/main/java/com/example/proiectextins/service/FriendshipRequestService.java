package com.example.proiectextins.service;

import com.example.proiectextins.domain.Friendship;
import com.example.proiectextins.domain.FriendshipRequest;
import com.example.proiectextins.domain.User;
import com.example.proiectextins.repository.Repository;

import java.util.*;

public class FriendshipRequestService {
    private Repository<Long, User> userDbRepository;
    private Repository<Long, FriendshipRequest> repo;
    private FriendshipService friendshipService;

    public FriendshipRequestService(Repository<Long, User> userDbRepository, Repository<Long, FriendshipRequest> repo, FriendshipService friendshipService) {
        this.repo = repo;
        this.userDbRepository = userDbRepository;
        this.friendshipService = friendshipService;
    }

    public void sendFriendshipRequest(String username1, String username2) {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        FriendshipRequest friendship = new FriendshipRequest(username1, username2, date, "pending");
        repo.save(friendship);
    }

    public void updateFriendshipRequestStatus(String username1, String username2, String status) {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        FriendshipRequest friendship = new FriendshipRequest(username1, username2, date, status);
        repo.update(friendship);
        if(Objects.equals(status, "accepted")){
            friendshipService.addFriendship(username1,username2);
        }

    }

    public void unsendFriendshipRequest(String username1, String username2) {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        FriendshipRequest friendship = new FriendshipRequest(username1, username2, date, "pending");
        repo.save(friendship);
        for(FriendshipRequest f : repo.findAll()) {
            if (((Objects.equals(f.getFriend1(), friendship.getFriend1())) && (Objects.equals(f.getFriend2(), friendship.getFriend2()))) ||
                    (Objects.equals(f.getFriend1(), friendship.getFriend2())) && (Objects.equals(f.getFriend2(), friendship.getFriend1()))) {
                repo.delete(f.getID());
            }
        }
    }

    public Iterable<FriendshipRequest> getAll() {
        return repo.findAll();
    }

    public FriendshipRequest findOne(Long id){return repo.findOne(id);}

    public Iterable<FriendshipRequest> findOnesFriendshipRequests(String username){
        Set<FriendshipRequest> friendships = new HashSet<>();
        Iterable<FriendshipRequest> all = repo.findAll();
        for(FriendshipRequest f: all){
            if(Objects.equals(f.getFriend1(), username) || Objects.equals(f.getFriend2(), username))
                friendships.add(f);
        }
        return friendships;
    }

    public Iterable<FriendshipRequest> findOnesFriendshipRequestsReceived(String username){
        Set<FriendshipRequest> friendships = new HashSet<>();
        Iterable<FriendshipRequest> all = repo.findAll();
        for(FriendshipRequest f: all){
            if(Objects.equals(f.getFriend2(), username))
                friendships.add(f);
        }
        return friendships;
    }

    public Iterable<FriendshipRequest> findOnesFriendshipRequestsSent(String username){
        Set<FriendshipRequest> friendships = new HashSet<>();
        Iterable<FriendshipRequest> all = repo.findAll();
        for(FriendshipRequest f: all){
            if(Objects.equals(f.getFriend1(), username))
                friendships.add(f);
        }
        return friendships;
    }

    public int sizeOfIterable(Iterable<FriendshipRequest> friendships){
        int count = 0;
        for(FriendshipRequest f : friendships)
            count++;
        return count;
    }

    public boolean verifyIfFriendRequestSent(String username1,String username2) {
        for(FriendshipRequest fr : repo.findAll()) {
            if((Objects.equals(fr.getFriend1(), username1) && Objects.equals(fr.getFriend2(), username2) && !Objects.equals(fr.getFriendshipStatus(), "declined")) ||
                    (Objects.equals(fr.getFriend2(), username1) && Objects.equals(fr.getFriend1(), username2) && !Objects.equals(fr.getFriendshipStatus(), "declined")))
                return true;
        }
        return false;
    }

    public List<FriendshipRequest> friendshipRequestsPending(String username) {
        Iterable<FriendshipRequest> friendshipRequests = findOnesFriendshipRequestsReceived(username);
        List<FriendshipRequest> list = new ArrayList<>();
        for( FriendshipRequest fr : friendshipRequests){
            if(Objects.equals(fr.getFriendshipStatus(), "pending"))
                list.add(fr);
        }
        return list;
    }

    public List<FriendshipRequest> friendshipRequestsPendingSent(String username) {
        Iterable<FriendshipRequest> friendshipRequests = findOnesFriendshipRequestsSent(username);
        List<FriendshipRequest> list = new ArrayList<>();
        for( FriendshipRequest fr : friendshipRequests){
            if(Objects.equals(fr.getFriendshipStatus(), "pending"))
                list.add(fr);
        }
        return list;
    }
}
