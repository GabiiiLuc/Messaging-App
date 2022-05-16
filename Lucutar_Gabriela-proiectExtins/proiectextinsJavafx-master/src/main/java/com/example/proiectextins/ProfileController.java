package com.example.proiectextins;

import com.example.proiectextins.domain.User;
import com.example.proiectextins.service.FriendshipRequestService;
import com.example.proiectextins.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;


import java.io.IOException;

public class ProfileController {
    private String username;
    private UserService userService = new UserService();
    private FriendshipRequestService friendshipRequestService;

    ObservableList<User> modelfriends = FXCollections.observableArrayList();

    @FXML
    private Label LabelText;

    @FXML
    private ListView<User> listViewFriends;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize(UserService userService, FriendshipRequestService friendshipRequestService){
        this.userService = userService;
        this.friendshipRequestService = friendshipRequestService;
        setName();
        listOfFriends();
    }

    public void setName(){
        LabelText.setText(userService.findUserAfterUsername(username).getFirstName()+ " " + userService.findUserAfterUsername(username).getLastName());
    }

    public void listOfFriends() {
        modelfriends.clear();
        modelfriends.addAll(userService.getFriendsOfUser(username));

        listViewFriends.setItems(modelfriends);
    }

    public void deleteFriend_btn_onSelect(ActionEvent e) {
        User friend = listViewFriends.getSelectionModel().getSelectedItem();
        userService.deleteUserFriend(friend, username);
        if(friendshipRequestService.verifyIfFriendRequestSent(friend.getUsername(), username)) {
            friendshipRequestService.updateFriendshipRequestStatus(friend.getUsername(), username, "declined");
            friendshipRequestService.updateFriendshipRequestStatus(username, friend.getUsername(), "declined");
        }
        listOfFriends();
    }

    public void FriendshipRequest_btn_onClick(ActionEvent e) throws IOException  {
        HelloApplication.showFriendRequestPage(username);
    }

    public void Inbox_btn_onClick(ActionEvent e) throws IOException {
        HelloApplication.showInbox(username);
    }

    public void logOut_btn_onClick(ActionEvent e) throws IOException{
        HelloApplication.showLoginPage();
    }

    public void addFriend_btn_onClick(ActionEvent e) throws IOException{
        HelloApplication.showAddFriendPage(username);
    }
}
