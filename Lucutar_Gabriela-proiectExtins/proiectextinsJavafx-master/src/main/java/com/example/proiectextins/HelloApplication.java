package com.example.proiectextins;

import com.example.proiectextins.domain.*;
import com.example.proiectextins.domain.validators.AccountValidator;
import com.example.proiectextins.domain.validators.UserValidator;
import com.example.proiectextins.frontend.CreateAccountAndLoginDisplay;
import com.example.proiectextins.repository.*;
import com.example.proiectextins.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private static Stage primaryStage;
    private static Repository<Long, User> userRepo = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/proiectDatabase", "postgres", "postgres");
    private static Repository<Long, Message> messageRepo = new MessageDbRepository("jdbc:postgresql://localhost:5432/proiectDatabase", "postgres", "postgres");
    private static Repository<Long, Account> accountRepo = new AccountDbRepository("jdbc:postgresql://localhost:5432/proiectDatabase", "postgres", "postgres", (UtilizatorDbRepository) userRepo);
    private static Repository<Long, Friendship> friendshipRepo = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/proiectDatabase", "postgres", "postgres");
    private static Repository<Long, FriendshipRequest> friendshipRequestRepo = new FriendshipRequestDbRepository("jdbc:postgresql://localhost:5432/proiectDatabase", "postgres", "postgres");
    private static UserValidator userValidator = new UserValidator();
    private static FriendshipService friendshipService = new FriendshipService(userRepo, friendshipRepo);
    private static MessageService messageService = new MessageService(messageRepo);
    private static UserService userService = new UserService(userRepo, friendshipService,messageService ,userValidator);
    private static FriendshipRequestService friendshipRequestService = new FriendshipRequestService(userRepo, friendshipRequestRepo, friendshipService);
    private static AccountValidator accountValidator = new AccountValidator();
    private static AccountService accountService = new AccountService(userService, accountRepo, accountValidator);

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Log in and Create account");

        showLoginPage();

    }

    public static void showLoginPage() throws IOException{
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = loader.load();

        HelloController ctrl=loader.getController();
        ctrl.setService(accountService);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("Login.css").toExternalForm());
        primaryStage.show();
    }

    public static void showProfilePage(String username) throws IOException {
        Stage stage = (Stage) primaryStage.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("profile.fxml"));
        Parent root = loader.load();

        ProfileController profileController = loader.getController();
        profileController.setUsername(username);
        profileController.initialize(userService, friendshipRequestService);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("Login.css").toExternalForm());
        stage.centerOnScreen();
        stage.setTitle("My Profile");
    }

    public static void showFriendRequestPage(String username) throws IOException{
        Stage stage = (Stage) primaryStage.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("friendshiprequest.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);

        FriendshipRequestController friendshipRequestController = loader.getController();
        friendshipRequestController.setUsername(username);
        friendshipRequestController.initialize(userService, friendshipRequestService, accountService, friendshipService);

        scene.getStylesheets().add(HelloApplication.class.getResource("Login.css").toExternalForm());

        stage.centerOnScreen();
        stage.setTitle("My friendship requests");
    }

    public static void showInbox(String username) throws IOException{
        Stage stage = (Stage) primaryStage.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("messages-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("Login.css").toExternalForm());

        InboxController inboxController = loader.getController();
        inboxController.setUsername(username);
        inboxController.initialize(userService, messageService);

        stage.centerOnScreen();
        stage.setTitle("My inbox");
}
  
    public static void showAddFriendPage(String username) throws IOException{
        Stage stage = (Stage) primaryStage.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("addfriend.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        AddFriendController addFriendController = loader.getController();
        addFriendController.setUsername(username);
        addFriendController.initialize(userService, friendshipRequestService, accountService, friendshipService);

        scene.getStylesheets().add(HelloApplication.class.getResource("Login.css").toExternalForm());

        stage.centerOnScreen();
        stage.setTitle("Add new friend");
}

    public static void main(String[] args) {
        launch();


    }
}