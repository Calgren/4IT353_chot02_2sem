package ui;

import app.App;
import db.DB;
import db.DBService;
import entities.Season;
import entities.SeasonTicket;
import entities.Sector;
import entities.TicketType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logic.CalculationService;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeController {
    private static final String[] TICKET_TYPES = new String[] {"Adult", "Junior", "Kid"};
    public Pane ticketsPage;
    public TableView ticketsTable;
    public Pane newTicketPane;
    public ComboBox<TicketType> buyTicketType;
    public ComboBox<Season> buyTicketSeason;
    public ComboBox<Sector> buyTicketSector;
    public Label priceLabel;

    public HomeController() {

    }


    public void handleLogOutClick(MouseEvent mouseEvent) {
        App.getInstance().logInScreen();
        App.getInstance().setLoggedInCustomer(null);
    }

    public void showTickets(MouseEvent mouseEvent) {
        ticketsPage.setVisible(true);
    }

    public void showAccount(MouseEvent mouseEvent) {
    }

    public void showStatistics(MouseEvent mouseEvent) {
    }

    public void newTicket(MouseEvent mouseEvent) {
        buyTicketSector.setItems(FXCollections.observableArrayList(DBService.selectSectors()));
        buyTicketSeason.setItems(FXCollections.observableArrayList(DBService.selectSeasons()));
        buyTicketType.setItems(FXCollections.observableArrayList(DBService.selectTicketTypes()));
        newTicketPane.setVisible(!newTicketPane.isVisible());
    }

    public void buyTicket(ActionEvent actionEvent) {
        SeasonTicket ticket = new SeasonTicket();
        ticket.setType(buyTicketType.getValue());
        ticket.setPrice(Float.valueOf(priceLabel.getText()));
        ticket.setSector(buyTicketSector.getValue());
        ticket.setSeason(buyTicketSeason.getValue());
        DBService.insertCustomerTicket(App.getInstance().getLoggedInCustomer(), ticket);
        newTicketPane.setVisible(false);
        System.out.println("FALSE");
    }

    public void recalculatePrice(ActionEvent actionEvent) {
        if (buyTicketType.getValue() != null && buyTicketSector.getValue() != null) {
            priceLabel.setText(CalculationService.calculateTicketPrice(buyTicketSector.getValue().getPricePoint(),
                    buyTicketType.getValue().getDefaultPrice()).toString());
        }

    }
}
