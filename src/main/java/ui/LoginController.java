package ui;

import app.App;
import db.DBService;
import entities.Customer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.util.concurrent.CompletableFuture;

public class LoginController {
    @FXML
    public TextField passwordField;
    @FXML
    public TextField loginField;
    @FXML
    public Label logInErrorLabel;
    @FXML
    public ProgressIndicator spinner;
    @FXML
    public Label loggingInLabel;
    @FXML
    public Button logInButton;

    public void handleLogInClick(ActionEvent actionEvent) {
        if (passwordField.getText().isBlank() || loginField.getText().isBlank()) {
            logInErrorLabel.setVisible(true);
            logInErrorLabel.setText("Both Login and Password has to be filled to log in.");
        } else {
            logInErrorLabel.setVisible(false);
            loginField.setDisable(true);
            passwordField.setDisable(true);
            logInButton.setDisable(true);
            spinner.setVisible(true);
            loggingInLabel.setVisible(true);
            spinner.setProgress(0.1);
            loginPromise();
        }
    }

    public void handleNewAccountClick(ActionEvent actionEvent) {
        App.getInstance().newAccountScreen();
    }

    private void loginPromise() {
        CompletableFuture<Customer> logInFutureQuery = CompletableFuture.supplyAsync(() -> {
            Customer c = null;
            spinner.setProgress(0.3);
            try {
                spinner.setProgress(0.5);
                c = DBService.logIn(loginField.getText(), passwordField.getText());
                spinner.setProgress(0.7);
            } catch (Exception e) {
                System.out.println("Couldn't log in");
            }
            return c;
        }).thenApply(customer -> {
            if (customer == null) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        logInErrorLabel.setText("Invalid password or login.");
                        logInErrorLabel.setVisible(true);
                        loginField.setDisable(false);
                        passwordField.setDisable(false);
                        logInButton.setDisable(false);
                        spinner.setVisible(false);
                        loggingInLabel.setVisible(false);
                    }
                });
            } else {
                spinner.setProgress(1);
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        App.getInstance().setLoggedInCustomer(customer);;
                        App.getInstance().homeScreen();
                    }
                });
            }
            return customer;
        });
    }
}
