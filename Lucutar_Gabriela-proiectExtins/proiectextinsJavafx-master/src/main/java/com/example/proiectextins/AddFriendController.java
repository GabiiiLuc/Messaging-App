package com.example.proiectextins;

import com.example.proiectextins.domain.User;
import com.example.proiectextins.service.AccountService;
import com.example.proiectextins.service.FriendshipRequestService;
import com.example.proiectextins.service.FriendshipService;
import com.example.proiectextins.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddFriendController {
    private String username;
    private UserService userService;
    private FriendshipRequestService friendshipRequestService;
    private AccountService accountService;
    private FriendshipService friendshipService;

    ObservableList<User> modelusers = FXCollections.observableArrayList();

    @FXML
    private TextField textFieldSearch;

    @FXML
    private ListView<User> listViewUsers;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize(UserService userService, FriendshipRequestService friendshipRequestService, AccountService accountService, FriendshipService friendshipService){
        this.userService = userService;
        this.friendshipRequestService = friendshipRequestService;
        this.accountService = accountService;
        this.friendshipService = friendshipService;
        listOfUsersNotFriends();
        textFieldSearch.textProperty().addListener(o -> handleFilter());
    }

    private void handleFilter() {
        Predicate<User> p1 = n -> n.getFirstName().toLowerCase().startsWith(textFieldSearch.getText().toLowerCase());
        Predicate<User> p2 = n -> n.getLastName().toLowerCase().startsWith(textFieldSearch.getText().toLowerCase());

        modelusers.setAll(listOfUsersNotFriends()
                .stream()
                .filter(p1.or(p2))
                .collect(Collectors.toList()));
    }

    public List<User> listOfUsersNotFriends() {
        modelusers.clear();
        for(User u : userService.getAll()) {
            if (!(friendshipService.verifyIfFriends(u.getUsername(), username)) &&
                    accountService.verifyIfUserHasAccount(u.getUsername()) &&
                    !(friendshipRequestService.verifyIfFriendRequestSent(username,u.getUsername())) &&
                    !Objects.equals(u.getUsername(), username)) {
                modelusers.add(u);
            }
        }
        listViewUsers.setItems(modelusers);
        return modelusers;
    }

    public void sendFriendRequest_btn_onClick(ActionEvent e) {
        User notfriend = listViewUsers.getSelectionModel().getSelectedItem();
        friendshipRequestService.sendFriendshipRequest(username, notfriend.getUsername());
        listOfUsersNotFriends();

    }

    public void backToProfile_btn_onClick(ActionEvent e) throws IOException {
        HelloApplication.showProfilePage(username);
    }
}
