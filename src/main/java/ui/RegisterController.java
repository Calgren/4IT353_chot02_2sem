package ui;

import app.App;
import db.DBService;
import entities.Customer;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.ValidationService;
import org.hibernate.exception.ConstraintViolationException;

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
    public Label updateSuccessLabel;


    public void handleBackClick() {
        App.getInstance().logInScreen();
    }

    public void registerNewCustomer() {
        try {
            if (birthDateInput.getValue() == null) {
                errorsLabel.setText("Fill all required field: login, password, first, last name, birthdate and email.");
                return;
            }
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
            if (ValidationService.customerValidForCreation(newCustomer)) {
                DBService.insertCustomer(newCustomer);
                updateSuccessLabel.setVisible(true);
                errorsLabel.setText("");
            } else {
                updateSuccessLabel.setVisible(false);
                errorsLabel.setText("Fill all required field: login, password, first, last name, birthdate and email.");
            }

        } catch (Exception e) {
            errorsLabel.setText("This user already exists.");
            errorsLabel.setVisible(true);
            System.out.println("Error during creating new Customer in database. " + e.getMessage() + " " + e.getClass());
        }

    }
}
