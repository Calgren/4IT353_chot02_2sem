package ui;

import app.App;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegisterController {
    public TextField loginInput;
    public TextField passwordInput;
    public TextField firstNameInput;
    public TextField lastNameInput;
    public DatePicker birthDateInput;
    public TextField emailInput;
    public TextField phoneInput;


    public void handleBackClick() {
        App.getInstance().logInScreen();
    }

    public void registerNewCustomer() {

    }
}
