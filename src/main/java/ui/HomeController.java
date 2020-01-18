package ui;

import app.App;
import entities.Season;
import entities.SeasonTicket;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HomeController {
    public Pane ticketsPage;
    public TableView ticketsTable;
    public Pane newTicketPane;
    public ComboBox<String> buyTicketType;
    public ComboBox<Season> buyTicketSeason;
    public ComboBox<String> buyTicketSector;
    public Label priceLabel;


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
        //newTicketPane.setVisible(true);
        newTicketPane.setVisible(!newTicketPane.isVisible());
    }

    public void buyTicket(ActionEvent actionEvent) {
        SeasonTicket ticket = new SeasonTicket();
        ticket.setType(buyTicketType.getValue());
        ticket.setPrice(Float.valueOf(priceLabel.getText()));
        ticket.setSector(buyTicketSector.getValue());
        ticket.setSeason(buyTicketSeason.getValue());
    }
}
