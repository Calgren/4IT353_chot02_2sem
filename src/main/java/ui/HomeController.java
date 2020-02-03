package ui;

import app.App;
import db.DBService;
import entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logic.CalculationService;
import logic.ValidationService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Controller for main page of app
 *
 * @author TomasCh
 */
public class HomeController {
    public Pane ticketsPage;
    public TableView<SeasonTicket> ticketsTable;
    public TableColumn<SeasonTicket, String> ticketsTypeCol;
    public TableColumn<SeasonTicket, String> ticketsSectorCol;
    public TableColumn<SeasonTicket, String> ticketsStartCol;
    public TableColumn<SeasonTicket, String> ticketsEndCol;
    public Pane newTicketPane;
    public ComboBox<TicketType> buyTicketType;
    public ComboBox<Season> buyTicketSeason;
    public ComboBox<Sector> buyTicketSector;
    public Label priceLabel;
    public Label errorText;
    public Pane accountPage;
    public TextField loginInput;
    public TextField passwordInput;
    public TextField firstNameInput;
    public TextField lastNameInput;
    public DatePicker birthDateInput;
    public TextField emailInput;
    public TextField phoneInput;
    public Label updateSuccessLabel;
    public Pane statsPage;
    public ComboBox<String> entityFilter;
    public TableView statsTable;

    /**
     * init fetches information about logged in customer
     *
     * @author TomasCh
     */
    @FXML
    public void initialize() {
        entityFilter.setItems(FXCollections.observableArrayList(new String[]{"Customers", "Bought Tickets"}));
        refreshCustomerInformation();
    }


    /**
     * handles log out, returns to login screen and nulls logged in customer
     *
     * @author TomasCh
     */
    public void handleLogOutClick(MouseEvent mouseEvent) {
        App.getInstance().logInScreen();
        App.getInstance().setLoggedInCustomer(null);
    }

    /**
     * switches to tickets page
     *
     * @author TomasCh
     */
    public void showTickets(MouseEvent mouseEvent) {
        accountPage.setVisible(false);
        statsPage.setVisible(false);
        initCustomerTicketsTable();
        refreshTicketsTable();
        ticketsPage.setVisible(true);
    }

    /**
     * switches to account overview page
     *
     * @author TomasCh
     */
    public void showAccount(MouseEvent mouseEvent) {
        errorText.setText("");
        ticketsPage.setVisible(false);
        statsPage.setVisible(false);
        refreshCustomerInformation();
        updateSuccessLabel.setVisible(false);
        accountPage.setVisible(true);
    }

    /**
     * switches to statistics page
     *
     * @author TomasCh
     */
    public void showStatistics(MouseEvent mouseEvent) {
        errorText.setText("");
        ticketsPage.setVisible(false);
        accountPage.setVisible(false);
        statsPage.setVisible(true);
    }

    /**
     * shows new ticket creation modal, fetches available options
     *
     * @author TomasCh
     */
    public void newTicket(MouseEvent mouseEvent) {
        errorText.setText("");
        buyTicketSector.setItems(FXCollections.observableArrayList(DBService.selectSectors()));
        buyTicketSeason.setItems(FXCollections.observableArrayList(DBService.selectSeasons()));
        buyTicketType.setItems(FXCollections.observableArrayList(DBService.selectTicketTypes()));
        newTicketPane.setVisible(!newTicketPane.isVisible());
    }

    /**
     * creates nwe ticket in database if possible
     *
     * @author TomasCh
     */
    public void buyTicket(ActionEvent actionEvent) {
        if (priceLabel.getText() == null) {
            return;
        }
        SeasonTicket ticket = new SeasonTicket();
        ticket.setType(buyTicketType.getValue());
        ticket.setPrice(Float.valueOf(priceLabel.getText()));
        ticket.setSector(buyTicketSector.getValue());
        ticket.setSeason(buyTicketSeason.getValue());
        if (ValidationService.ticketValidForCreation(ticket)) {
            String error = ValidationService.ticketCanBePurchased(ticket, App.getInstance().getLoggedInCustomer());
            if (error == null) {
                DBService.insertCustomerTicket(App.getInstance().getLoggedInCustomer(), ticket);
                newTicketPane.setVisible(false);
                refreshTicketsTable();
                errorText.setText("");
            } else {
                errorText.setText(error);
            }
        }
    }

    /**
     * if possible, recalculates price and displays it
     *
     * @author TomasCh
     */
    public void recalculatePrice(ActionEvent actionEvent) {
        if (buyTicketType.getValue() != null && buyTicketSector.getValue() != null) {
            priceLabel.setText(CalculationService.calculateTicketPrice(buyTicketSector.getValue().getPricePoint(),
                    buyTicketType.getValue().getDefaultPrice()).toString());
        }
    }

    /**
     * inits customer tickets table columns
     *
     * @author TomasCh
     */
    private void initCustomerTicketsTable() {
        ticketsTypeCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getType().getName());
            }
        });
        ticketsSectorCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getSector().getSectorId());
            }
        });
        ticketsStartCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getSeason().getStart().toString());
            }
        });
        ticketsEndCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getSeason().getEnd().toString());
            }
        });
    }

    /**
     * refreshes customer tickets table
     *
     * @author TomasCh
     */
    private void refreshTicketsTable() {
        ticketsTable.getItems().clear();
        for (SeasonTicket ticket : App.getInstance().getLoggedInCustomer().getTickets()) {
            ticketsTable.getItems().add(ticket);
        }
    }

    /**
     * refreshes customer infromation
     *
     * @author TomasCh
     */
    private void refreshCustomerInformation() {
        loginInput.setText(App.getInstance().getLoggedInCustomer().getLogin());
        firstNameInput.setText(App.getInstance().getLoggedInCustomer().getFirstName());
        lastNameInput.setText(App.getInstance().getLoggedInCustomer().getLastName());
        passwordInput.setText(App.getInstance().getLoggedInCustomer().getPassword());
        birthDateInput.setValue(App.getInstance().getLoggedInCustomer().getBirthDate().toLocalDate());
        emailInput.setText(App.getInstance().getLoggedInCustomer().getEmail());
        phoneInput.setText(App.getInstance().getLoggedInCustomer().getPhone());
    }

    /**
     * if possible, updates customer information in database
     *
     * @author TomasCh
     */
    public void updateCustomer(ActionEvent actionEvent) {
        Customer c = App.getInstance().getLoggedInCustomer();
        c.setLogin(App.getInstance().getLoggedInCustomer().getLogin());

        c.setRegisterDate(App.getInstance().getLoggedInCustomer().getRegisterDate());
        c.setPassword(passwordInput.getText());
        c.setFirstName(firstNameInput.getText());
        c.setLastName(lastNameInput.getText());
        c.setBirthDate(Date.valueOf(birthDateInput.getValue()));
        c.setEmail(emailInput.getText());
        c.setPhone(phoneInput.getText());
        if (ValidationService.customerValidForCreation(c)) {
            App.getInstance().setLoggedInCustomer(c);
            DBService.updateCustomer(App.getInstance().getLoggedInCustomer());
            updateSuccessLabel.setVisible(true);
            errorText.setText("");
        } else {
            updateSuccessLabel.setVisible(false);
            errorText.setText("Fill all required field: login, password, first, last name, birthdate and email.");
        }
    }

    /**
     * refreshes filtered data
     *
     * @author TomasCh
     */
    public void refreshFilter() {
        switch (entityFilter.getValue()) {
            case "Customers" : showCustomers();
            case "Bought Tickets" : showBoughtTickets();
        }
    }

    /**
     * inits stats table for customers
     *
     * @author TomasCh
     */
    private void showCustomers() {
        statsTable.getItems().clear();
        statsTable.getColumns().clear();
        ArrayList<Customer> customers = DBService.selectAllCustomers();
        TableColumn<String, Customer> column1 = new TableColumn<>("Login");
        column1.setCellValueFactory(new PropertyValueFactory<>("login"));
        TableColumn<String, Customer> column2 = new TableColumn<>("First Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<String, Customer> column3 = new TableColumn<>("Last Name");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<String, Customer> column4 = new TableColumn<>("Birth Date");
        column4.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        TableColumn<String, Customer> column5 = new TableColumn<>("Registered");
        column5.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        TableColumn<String, Customer> column6 = new TableColumn<>("Email");
        column6.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<String, Customer> column7 = new TableColumn<>("Phone");
        column7.setCellValueFactory(new PropertyValueFactory<>("phone"));
        statsTable.getColumns().add(column1);
        statsTable.getColumns().add(column2);
        statsTable.getColumns().add(column3);
        statsTable.getColumns().add(column4);
        statsTable.getColumns().add(column5);
        statsTable.getColumns().add(column6);
        statsTable.getColumns().add(column7);
        statsTable.getItems().addAll(customers);
    }

    /**
     * inits stats table for tickets
     *
     * @author TomasCh
     */
    private void showBoughtTickets() {
        statsTable.getItems().clear();
        statsTable.getColumns().clear();
        ArrayList<Customer> customers = DBService.selectAllCustomers();
        TableColumn<SeasonTicket, String> column1 = new TableColumn<>("Type");
        column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType().getName()));
        TableColumn<SeasonTicket, String> column2 = new TableColumn<>("Sector");
        column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSector().getSectorId()));
        TableColumn<SeasonTicket, String> column3 = new TableColumn<>("Start");
        column3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getSeason().getStart().toString());
            }
        });
        TableColumn<SeasonTicket, String> column4 = new TableColumn<>("End");
        column4.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SeasonTicket,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SeasonTicket, String> data) {
                return new SimpleStringProperty(data.getValue() // the RelationManager
                        .getSeason().getEnd().toString());
            }
        });
        statsTable.getColumns().add(column1);
        statsTable.getColumns().add(column2);
        statsTable.getColumns().add(column3);
        statsTable.getColumns().add(column4);
        ArrayList<SeasonTicket> tickets = new ArrayList<>();
        for (Customer c : customers) {
            tickets.addAll(c.getTickets());
        }
        statsTable.getItems().addAll(tickets);
    }

    public void deleteAccount(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DBService.deleteCustomer(App.getInstance().getLoggedInCustomer());
            App.getInstance().setLoggedInCustomer(null);
            App.getInstance().logInScreen();
        } else {
            alert.close();
        }

    }
}
