package com.example.proiectextins;

import com.example.proiectextins.domain.FriendshipRequest;
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

public class FriendshipRequestController {
    private String username;
    private UserService userService;
    private FriendshipRequestService friendshipRequestService;
    private AccountService accountService;
    private FriendshipService friendshipService;

    ObservableList<String> pendingrequests = FXCollections.observableArrayList();
    ObservableList<String> sentrequests = FXCollections.observableArrayList();


    @FXML
    private ListView<String> listViewFriendshipRequestsPending;

    @FXML
    private ListView<String> listViewFriendshipRequestsSent;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize(UserService userService, FriendshipRequestService friendshipRequestService, AccountService accountService, FriendshipService friendshipService){
        this.userService = userService;
        this.friendshipRequestService = friendshipRequestService;
        this.accountService = accountService;
        this.friendshipService = friendshipService;
        listFriendRequestPending();
        listFriendRequestSent();
    }


    public List<FriendshipRequest> listFriendRequestPending() {
        pendingrequests.clear();
        List<FriendshipRequest> formattedFriendshipRequest = friendshipRequestService.friendshipRequestsPending(username);
        for(FriendshipRequest fr : formattedFriendshipRequest){
            User user = userService.findUserAfterUsername(fr.getFriend1());
            pendingrequests.add(user.getFirstName() + " " + user.getLastName() + " | status: " + fr.getFriendshipStatus() + " on: " + fr.getFriendshipDate());
        }
        listViewFriendshipRequestsPending.setItems(pendingrequests);
        return formattedFriendshipRequest;
    }

    public List<FriendshipRequest> listFriendRequestSent() {
        sentrequests.clear();
        List<FriendshipRequest> formattedFriendshipRequest = friendshipRequestService.friendshipRequestsPendingSent(username);
        for(FriendshipRequest fr : formattedFriendshipRequest){
            User user = userService.findUserAfterUsername(fr.getFriend2());
            sentrequests.add(user.getFirstName() + " " + user.getLastName() + " | status: " + fr.getFriendshipStatus() + " on: " + fr.getFriendshipDate());
        }
        listViewFriendshipRequestsSent.setItems(sentrequests);
        return formattedFriendshipRequest;
    }

    public void unsendFriendRequest_btn_onClick(ActionEvent e) {
        String selected = listViewFriendshipRequestsSent.getSelectionModel().getSelectedItem();
        FriendshipRequest fr = getFriendshipRequestByNamesS(selected, listFriendRequestSent());
        friendshipRequestService.unsendFriendshipRequest(fr.getFriend1(), fr.getFriend2());
        refresh();
    }

    public void acceptFriendRequest_btn_onClick(ActionEvent e) {
        String selected = listViewFriendshipRequestsPending.getSelectionModel().getSelectedItem();
        FriendshipRequest fr = getFriendshipRequestByNames(selected, listFriendRequestPending());
        friendshipRequestService.updateFriendshipRequestStatus(fr.getFriend1(), fr.getFriend2(), "accepted");
        refresh();

    }

    public void declineFriendRequest_btn_onClick(ActionEvent e) {
        String selected = listViewFriendshipRequestsPending.getSelectionModel().getSelectedItem();
        FriendshipRequest fr = getFriendshipRequestByNames(selected, listFriendRequestPending());
        friendshipRequestService.updateFriendshipRequestStatus(fr.getFriend1(), fr.getFriend2(), "declined");
        refresh();

    }

    public void refresh() {
        listFriendRequestPending();
        listFriendRequestSent();
    }

    public void refresh_btn_onClick(ActionEvent e) {
        listFriendRequestPending();
        listFriendRequestSent();
    }

    public void backToProfile_btn_onClick(ActionEvent e) throws IOException {
        HelloApplication.showProfilePage(username);
    }

    public FriendshipRequest getFriendshipRequestByNames(String string, List<FriendshipRequest> fr) {
        int index = string.indexOf(" ");
        String firstName = string.substring(0,index);

        string = string.substring(index+1);
        int index1 = string.indexOf(" ");
        String lastName = string.substring(0, index1);

        for(FriendshipRequest frr : fr){
            if(Objects.equals(userService.findUserAfterUsername(frr.getFriend1()).getFirstName(), firstName) &&
                    Objects.equals(userService.findUserAfterUsername(frr.getFriend1()).getLastName(), lastName))
                return frr;
        }
        return  null;
    }
    public FriendshipRequest getFriendshipRequestByNamesS(String string, List<FriendshipRequest> fr) {
        int index = string.indexOf(" ");
        String firstName = string.substring(0,index);

        string = string.substring(index+1);
        int index1 = string.indexOf(" ");
        String lastName = string.substring(0, index1);

        for(FriendshipRequest frr : fr){
            if(Objects.equals(userService.findUserAfterUsername(frr.getFriend2()).getFirstName(), firstName) &&
                    Objects.equals(userService.findUserAfterUsername(frr.getFriend2()).getLastName(), lastName))
                return frr;
        }
        return  null;
    }
}
