package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DB {

    private static DB dB = null;
    private Configuration con;
    private SessionFactory sf;

    // private constructor restricted to this class itself
    private DB() {
        con = new Configuration().configure();
        sf = con.buildSessionFactory();
        System.out.println("DB Object created.");
    }

    // static method to create instance of Singleton class
    public static DB getInstance() {
        if (dB == null) {
            dB = new DB();
        }
        return dB;
    }

    public SessionFactory getSessionFactory() {
        return sf;
    }
}
