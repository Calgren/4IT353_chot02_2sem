package app;

import entities.Customer;
import entities.Season;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ui.LoginController;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class Main extends Application {
    public static void main(String[] args) {
        /*Season c = new Season();
        c.setStart(new Date(Calendar.getInstance().getTime().getTime()));
        c.setEnd(new Date(Calendar.getInstance().getTime().getTime()));
        Configuration con = new Configuration().configure();
        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();*/
        //System.out.println("TTTTT 1");
        launch();

    }


    @Override
    public void start(Stage stage) throws Exception {
        App.getInstance().setStage(stage);
        App.getInstance().logInScreen();
    }
}
