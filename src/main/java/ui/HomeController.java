package ui;

import app.App;
import db.DB;
import db.DBService;
import entities.Season;
import entities.SeasonTicket;
import entities.Sector;
import entities.TicketType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logic.CalculationService;
import logic.ValidationService;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeController {
    private static final String[] TICKET_TYPES = new String[] {"Adult", "Junior", "Kid"};
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

    public HomeController() {

    }


    public void handleLogOutClick(MouseEvent mouseEvent) {
        App.getInstance().logInScreen();
        App.getInstance().setLoggedInCustomer(null);
    }

    public void showTickets(MouseEvent mouseEvent) {
        ticketsPage.setVisible(true);
        initCustomerTicketsTable();
        refreshTicketsTable();
    }

    public void showAccount(MouseEvent mouseEvent) {
    }

    public void showStatistics(MouseEvent mouseEvent) {
    }

    public void newTicket(MouseEvent mouseEvent) {
        errorText.setText("");
        buyTicketSector.setItems(FXCollections.observableArrayList(DBService.selectSectors()));
        buyTicketSeason.setItems(FXCollections.observableArrayList(DBService.selectSeasons()));
        buyTicketType.setItems(FXCollections.observableArrayList(DBService.selectTicketTypes()));
        newTicketPane.setVisible(!newTicketPane.isVisible());
    }

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
        System.out.println("FALSE");
    }

    public void recalculatePrice(ActionEvent actionEvent) {
        if (buyTicketType.getValue() != null && buyTicketSector.getValue() != null) {
            priceLabel.setText(CalculationService.calculateTicketPrice(buyTicketSector.getValue().getPricePoint(),
                    buyTicketType.getValue().getDefaultPrice()).toString());
        }
    }

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
    private void refreshTicketsTable() {
        ticketsTable.getItems().clear();
        for (SeasonTicket ticket : App.getInstance().getLoggedInCustomer().getTickets()) {
            System.out.println("TTTT adding ticket");
            ticketsTable.getItems().add(ticket);
        }
    }
}
