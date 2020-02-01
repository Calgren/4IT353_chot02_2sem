package ui;

import app.App;
import db.DB;
import db.DBService;
import entities.Season;
import entities.SeasonTicket;
import entities.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeController {
    private static final String[] TICKET_TYPES = new String[] {"Adult", "Junior", "Kid"};
    public Pane ticketsPage;
    public TableView ticketsTable;
    public Pane newTicketPane;
    public ComboBox<String> buyTicketType;
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
        ArrayList<Sector> sectors = DBService.selectSectors();
        ArrayList<Season> seasons = DBService.selectSeasons();
        System.out.println("SELECTED: " + sectors);
        buyTicketSector.setItems(FXCollections.observableArrayList(sectors));
        buyTicketSeason.setItems(FXCollections.observableArrayList(seasons));
        buyTicketType.setItems(FXCollections.observableArrayList(TICKET_TYPES));
        newTicketPane.setVisible(!newTicketPane.isVisible());
    }

    public void buyTicket(ActionEvent actionEvent) {

        SeasonTicket ticket = new SeasonTicket();
        ticket.setType(buyTicketType.getValue());
        ticket.setPrice(Float.valueOf(priceLabel.getText()));
        //ticket.setSector(buyTicketSector.getValue());
        ticket.setSeason(buyTicketSeason.getValue());
    }
}
