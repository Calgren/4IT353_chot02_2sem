package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton Database instance, provides connection to database a session opening
 *
 * @author TomasCh
 */
public class DB {
    static final Logger LOG = LoggerFactory.getLogger(DB.class);
    private static DB dB = null;
    private Configuration con;
    private SessionFactory sf;

    // private constructor restricted to this class itself
    private DB() {
        con = new Configuration().configure();
        sf = con.buildSessionFactory();
        LOG.info("Connected to database");
    }

    /**
     * static method to create instance of Singleton class
     *
     * @return DB singleton instance
     *
     * @author TomasCh
     */
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
