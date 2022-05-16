package com.example.proiectextins.service;

import com.example.proiectextins.domain.Friendship;
import com.example.proiectextins.domain.Message;
import com.example.proiectextins.domain.User;
import com.example.proiectextins.domain.validators.UserValidator;
import com.example.proiectextins.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserService {
    private Repository<Long, User> repo;
    private FriendshipService friendshipService = new FriendshipService();
    private MessageService messageService = new MessageService();
    private UserValidator userValidator;

    public UserService(Repository<Long, User> repo, FriendshipService friendshipService, MessageService messageService, UserValidator userValidator) {
        this.repo = repo;
        this.friendshipService = friendshipService;
        this.messageService = messageService;
        this.userValidator = userValidator;
    }

    public UserService() {
    }

    public User addUser(User user) {
            userValidator.validate(user);
            return repo.save(user);
    }

    public User updateUser(User user) {
            userValidator.validate(user);
            return repo.update(user);
    }

    public Iterable<User> getAll() {
        return repo.findAll();
    }

    public User deleteUser(String username) {

        Iterable<Friendship> friendships = friendshipService.findOnesFriendships(username);
        if (friendshipService.sizeOfIterable(friendships) > 0) {
            for (Friendship f : friendships) {
                friendshipService.deleteFriendship(f.getFriend1(), f.getFriend2());
            }
        }
        for (User u : repo.findAll()) {
            if (Objects.equals(u.getUsername(), username)) {
                repo.delete(u.getID());
            }
        }
        return null;
    }

    public User findOne(Long id) {
        return repo.findOne(id);
    }

    public User findUserAfterUsername(String username){
        for(User u : repo.findAll()){
            if(Objects.equals(u.getUsername(),username)){
                return u;
            }
        }
        return null;
    }

    public List<User> getFriendsOfUser(String username) {
        List<User> list = new ArrayList<>();
        for(Friendship f : friendshipService.findOnesFriendships(username)){
            if(Objects.equals(f.getFriend1(), username))
                list.add(findUserAfterUsername(f.getFriend2()));
            else
                list.add(findUserAfterUsername(f.getFriend1()));
        }
        return list;
    }

    public void deleteUserFriend(User user, String username){
        friendshipService.deleteFriendship(username, user.getUsername());
    }

    public List<User> getActiveConversations(String username) {
        List<User> list = new ArrayList<>();
        for(Message m : messageService.findOnesMessages(username)){
            if(Objects.equals(m.getFrom(), username)){
                list.add(findUserAfterUsername(m.getTo()));
            }else{
                list.add(findUserAfterUsername(m.getFrom()));
            }
        }
        return list;
    }
}