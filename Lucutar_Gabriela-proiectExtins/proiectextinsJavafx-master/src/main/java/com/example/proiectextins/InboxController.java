package com.example.proiectextins;

import com.example.proiectextins.domain.Message;
import com.example.proiectextins.domain.User;
import com.example.proiectextins.service.AccountService;
import com.example.proiectextins.service.MessageService;
import com.example.proiectextins.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InboxController {
    private String username;
    private UserService userService;
    private MessageService messageService;

    private User selectedFriend;

    ObservableList<User> conversations = FXCollections.observableArrayList();
    ObservableList<Message> messages = FXCollections.observableArrayList();

    @FXML
    private ListView<User> listViewConversations;

    @FXML
    private ListView<Message> listViewMessage;

    @FXML
    private Text activeConversationLabel;

    @FXML
    private TextArea typingMessageTextArea;

    @FXML
    private Button sendMessageButton;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    public void initialize(UserService userService, MessageService messageService){
        this.userService = userService;
        this.messageService = messageService;
        listConversations();
        refreshMessageList();
    }

    @FXML
    public void onFriendClick(){
        refreshMessageList();
        setActiveConversationLabel();
    }

    public List<User> listConversations() {
        conversations.clear();
        conversations.addAll(userService.getFriendsOfUser(this.username));
//        conversations.add(new User("test", "ion", "popescu"));
        listViewConversations.setItems(conversations);
        return conversations;
    }

    public void setActiveConversationLabel(){
        activeConversationLabel.setText("hello world");
        User friend = listViewConversations.getSelectionModel().getSelectedItem();
        String labelString;
        if(friend.getUsername() != null){
            labelString = "("+friend.getUsername()+") " + friend.getFirstName() + " " + friend.getLastName();
        }else{
            labelString = "Select a friend to view conversation";
        }
        activeConversationLabel.setText(labelString);
    }

    @FXML
    public void sendMessageButtonOnClick() {
        User friend = listViewConversations.getSelectionModel().getSelectedItem();
        String friendUsername = friend.getUsername();
        String messageText = typingMessageTextArea.getText();
        messageService.addMessage(username, friendUsername, messageText);

        typingMessageTextArea.setText("");

        refreshMessageList();
    }

    public List<Message> refreshMessageList(){
        messages.clear();

        User friend = listViewConversations.getSelectionModel().getSelectedItem();
        String friendUsername = "";
        if(friend != null) {
            friendUsername = friend.getUsername();
        }
        List<Message> messagesToShow = messageService.findConversation(username, friendUsername);

        for(Message message : messagesToShow) System.out.println(message);

        Collections.sort(messagesToShow, (a, b) -> comparator(a.getID(),b.getID()));
        messages.addAll(messagesToShow);

        listViewMessage.setItems(messages);

        return messages;
    }

    public void backToProfile_btn_onClick(ActionEvent e) throws IOException {
        HelloApplication.showProfilePage(username);
    }

    public int comparator(Long p1, Long p2){

            if (p1 > p2) {
                return 1;
            } else if (p1 < p2){
                return -1;
            } else {
                return 0;
            }
        }
}
