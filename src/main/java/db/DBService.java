package db;

import entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Database service provides methods to communicate with database
 *
 * @author TomasCh
 */
public class DBService{
    static final Logger LOG = LoggerFactory.getLogger(DBService.class);
    private static Session session;

    /**
     * tries to select customer with particular login and password from db
     *
     * @param login customer login
     * @param password customer password
     *
     * @return customer with particular login and password
     *
     * @author TomasCh
     */
    public static Customer logIn(String login, String password) {
        session = DB.getInstance().getSessionFactory().openSession();
        //Transaction tx = session.beginTransaction();
        String hql = "FROM Customer c WHERE c.login = ?1 AND c.password = ?2";
        Query q = session.createQuery(hql).setParameter(1, login).setParameter(2, password);
        Customer customer = (Customer) q.uniqueResult();
        session.close();
        LOG.info("Login try " + customer);
        return customer;
    }

    /**
     * inserts customer into db
     *
     * @param c customer to save
     *
     * @author TomasCh
     */
    public static void insertCustomer(Customer c) {
        session = DB.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
        LOG.info("Insert customer " + c);
        session.close();
    }

    /**
     * updates customer in db
     *
     * @param c customer to save
     *
     * @author TomasCh
     */
    public static void updateCustomer(Customer c) {
        session = DB.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(c);
        tx.commit();
        LOG.info("Update customer " + c);
        session.close();
    }

    /**
     * Ticket purchase - if particular ticket doesn't exist in db, inserts that ticket into db and links is according to
     * M:N customer - season ticket relation in relational table. If same ticket already exists, use it for relation.
     *
     * @param c customer to save
     * @param sT ticket for purchase
     *
     * @author TomasCh
     */
    public static void insertCustomerTicket(Customer c, SeasonTicket sT) {
        session = DB.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SeasonTicket existingTicket = selectTicket(sT);
        if (existingTicket == null) {
            session.save(sT);
            LOG.info("Insert new season ticket.");
        } else {
            sT =existingTicket;
        }
        c.addTicket(sT);
        session.update(c);
        tx.commit();
        LOG.info("Ticket purchase successful");
        session.close();
    }

    /**
     * selects all sectors from db
     *
     * @return list of all sectors
     *
     * @author TomasCh
     */
    public static ArrayList<Sector> selectSectors() {
        session = DB.getInstance().getSessionFactory().openSession();
        String hql = "FROM Sector";
        Query query = session.createQuery(hql);
        ArrayList<Sector> sectors = (ArrayList<Sector>) query.list();
        session.close();
        return sectors;
    }

    /**
     * selects all ticket types from db
     *
     * @return list of all ticket types
     *
     * @author TomasCh
     */
    public static ArrayList<TicketType> selectTicketTypes() {
        session = DB.getInstance().getSessionFactory().openSession();
        String hql = "FROM TicketType";
        Query query = session.createQuery(hql);
        ArrayList<TicketType> ticketTypes = (ArrayList<TicketType>) query.list();
        session.close();
        return ticketTypes;
    }

    /**
     * selects all seasons
     *
     * @return list of all seasons
     *
     * @author TomasCh
     */
    public static ArrayList<Season> selectSeasons() {
        session = DB.getInstance().getSessionFactory().openSession();
        String hql = "FROM Season";
        Query query = session.createQuery(hql);
        ArrayList<Season> seasons = (ArrayList<Season>) query.list();
        session.close();
        return seasons;
    }

    /**
     * tries to find ticket with same season, sector and type in db
     *
     * @param seasonTicket ticket for purchase
     *
     * @return ticket with same season, sector and type in db
     *
     * @author TomasCh
     */
    private static SeasonTicket selectTicket(SeasonTicket seasonTicket) {
        String hql = "FROM SeasonTicket st WHERE st.sector.sectorId = ?1 AND st.type.name = ?2 AND st.season.start = ?3 AND st.season.end = ?4";
        Query q = session.createQuery(hql).setParameter(1, seasonTicket.getSector().getSectorId()).setParameter(2, seasonTicket.getType().getName())
                .setParameter(3, seasonTicket.getSeason().getStart()).setParameter(4,seasonTicket.getSeason().getEnd());
        SeasonTicket sT = (SeasonTicket) q.uniqueResult();
        return sT;
    }
}
