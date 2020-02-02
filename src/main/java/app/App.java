package app;

import db.DB;
import entities.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Singleton Application instance, holds information about logged in customer and switches screens
 *
 * @author TomasCh
 */
public class App {
    private static App app = null;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Stage stage;

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public void setLoggedInCustomer(Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
        if (loggedInCustomer != null) {
            System.out.println("Logged in customer set to: " + this.loggedInCustomer.getLogin());
        }
    }

    private Customer loggedInCustomer;

    // private constructor restricted to this class itself
    private App() {

    }

    // static method to create instance of Singleton class
    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public void newAccountScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("New Account");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            this.setStage(stage);
        } catch (IOException e) {
            System.out.println("Error during switching to register screen. " + e.getMessage());
        }
    }

    public void logInScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            this.setStage(stage);
        } catch (IOException e) {
            System.out.println("Error during switching to login screen. " + e.getMessage());
        }

    }

    public void homeScreen() {
        try {
            System.out.println("Setting home screen.");
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Home");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            this.setStage(stage);
        } catch (IOException e) {
            System.out.println("Error during switching to Home screen. " + e.getMessage());
        }

    }
}
