package com.example.proiectextins;

import com.example.proiectextins.service.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class HelloController {
    private AccountService accountService = new AccountService();

    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername1;

    @FXML
    private PasswordField textFieldPassword1;

    @FXML
    private Label LabelText;

    @FXML
    private Label LabelTextCreate;


    public void setService(AccountService accountService){
       this.accountService = accountService;
   }


    @FXML
    protected void login_btn_onClick(ActionEvent e) throws IOException {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        if(accountService.login(username, password)) {
            HelloApplication.showProfilePage(username);
        } else {
            LabelText.setText("Wrong username or password");
        }
    }


    @FXML
    protected void createacc_btn_onClick(ActionEvent e) throws IOException {
        String username = textFieldUsername1.getText();
        String password = textFieldPassword1.getText();
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String string = accountService.createAccount(firstName, lastName, username, password);
        LabelTextCreate.setText(string);
    }

}