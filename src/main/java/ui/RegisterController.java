package ui;

import app.App;
import db.DBService;
import entities.Customer;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.util.Calendar;

public class RegisterController {
    public TextField loginInput;
    public TextField passwordInput;
    public TextField firstNameInput;
    public TextField lastNameInput;
    public DatePicker birthDateInput;
    public TextField emailInput;
    public TextField phoneInput;
    public Label errorsLabel;


    public void handleBackClick() {
        App.getInstance().logInScreen();
    }

    public void registerNewCustomer() {
        try {
            Customer newCustomer = new Customer();
            newCustomer.setLogin(loginInput.getText());
            newCustomer.setPassword(passwordInput.getText());
            newCustomer.setFirstName(firstNameInput.getText());
            newCustomer.setLastName(lastNameInput.getText());
            newCustomer.setBirthDate(Date.valueOf(birthDateInput.getValue()));
            newCustomer.setEmail(emailInput.getText());
            newCustomer.setPhone(phoneInput.getText());
            newCustomer.setType("Customer");
            newCustomer.setRegisterDate(new Date(Calendar.getInstance().getTime().getTime()));
            DBService.insertCustomer(newCustomer);
        } catch (Exception e) {
            errorsLabel.setText("Error during creating new Customer in database. " + e.getMessage() + " " + e.getClass());
            errorsLabel.setVisible(true);
            System.out.println("Error during creating new Customer in database. " + e.getMessage() + " " + e.getClass());
        }

    }
}
