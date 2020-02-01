package db;

import entities.Customer;
import entities.Season;
import entities.SeasonTicket;
import entities.Sector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class DBService{
    private static Session session;

    public static Customer logIn(String login, String password) {
        session = DB.getInstance().getSessionFactory().openSession();
        //Transaction tx = session.beginTransaction();
        String hql = "FROM Customer c WHERE c.login = ?1 AND c.password = ?2";
        Query q = session.createQuery(hql).setParameter(1, login).setParameter(2, password);
        Customer customer = (Customer) q.uniqueResult();
        session.close();
        return customer;
    }

    public static void insertCustomer(Customer c) {
        session = DB.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
        session.close();
    }

    public static void insertCustomerTicket(Customer c, SeasonTicket sT) {
        session = DB.getInstance().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        SeasonTicket existingTicket = selectTicket(sT);
        if (existingTicket == null) {
            session.save(sT);
        }
        c.addTicket(sT);
        session.update(c);
        tx.commit();
        session.close();
    }

    private static SeasonTicket selectTicket(SeasonTicket seasonTicket) {
        //Transaction tx = session.beginTransaction();
        String hql = "FROM SeasonTicket sT WHERE sT.sector = ?1 AND sT.type = ?2 AND st.season.start = ?3 AND st.season.end = ?4";
        Query q = session.createQuery(hql).setParameter(1, seasonTicket.getSector()).setParameter(2, seasonTicket.getType())
                .setParameter(3, seasonTicket.getSeason().getStart()).setParameter(4,seasonTicket.getSeason().getEnd());
        SeasonTicket sT = (SeasonTicket) q.uniqueResult();
        return sT;
    }

    public static ArrayList<Sector> selectSectors() {
        session = DB.getInstance().getSessionFactory().openSession();
        //Transaction tx = session.beginTransaction();
        String hql = "FROM Sector";
        Query query = session.createQuery(hql);
        ArrayList<Sector> sectors = (ArrayList<Sector>) query.list();
        session.close();
        System.out.println("SELECTED: " + sectors.size());
        return sectors;
    }

    public static ArrayList<Season> selectSeasons() {
        session = DB.getInstance().getSessionFactory().openSession();
        //Transaction tx = session.beginTransaction();
        String hql = "FROM Season";
        Query query = session.createQuery(hql);
        ArrayList<Season> seasons = (ArrayList<Season>) query.list();
        session.close();
        System.out.println("SELECTED: " + seasons.size());
        return seasons;
    }

}
