package com.example.proiectextins.frontend;

import com.example.proiectextins.service.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountAndLoginDisplay {
    private AccountService accountService = new AccountService();
    /*@FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldLastName;*/
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    protected void login_btn_onClick(ActionEvent e) throws IOException {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        System.out.println(username + password);
        if(accountService.login(username, password)) {
            System.out.println("Succesful login");
            /*Stage stage = (Stage) textFieldUsername.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGUI.fxml")));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.setTitle("Sapply - Administrator");*/
        }else {
            System.out.println("Wrong username or password");
        }
    }

    public CreateAccountAndLoginDisplay(AccountService accountService) {
        this.accountService = accountService;
    }
}
