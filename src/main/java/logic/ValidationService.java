package logic;

import entities.Customer;
import entities.SeasonTicket;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Service providing logic for validations
 *
 * @author TomasCh
 */
public class ValidationService {

    /**
     * validates whether ticket contains all needed values for creation
     *
     * @param st ticket to validate
     *
     * @return true/false whether ticket is valid for creation
     *
     * @author TomasCh
     */
    public static Boolean ticketValidForCreation(SeasonTicket st) {
        return (st.getSector() != null && st.getSeason() != null && st.getType() != null && st.getPrice() != null);
    }

    /**
     * validates whether customer contains all needed values for creation
     *
     * @param c customer to validate
     *
     * @return true/false whether customer is valid for creation
     *
     * @author TomasCh
     */
    public static Boolean customerValidForCreation(Customer c) {
        return (!c.getLogin().isBlank() && !c.getPassword().isBlank() && !c.getEmail().isBlank()
                && !c.getFirstName().isBlank() && !c.getLastName().isBlank() && c.getBirthDate() != null);
    }

    /**
     * validates whether ticket can be purchased
     *
     * @param st ticket to be purchased
     * @param c customer purchasing ticket
     *
     * @return error message why ticket cannot be purchased, null when no error
     *
     * @author TomasCh
     */
    public static String ticketCanBePurchased(SeasonTicket st, Customer c) {
        switch (st.getType().getName()) {
            case "Adult" : return validatePurchaseForAdultTicket(st, c);
            case "Junior" : return  validatePurchaseForJuniorTicker(st, c);
            case "Kid" : return validatePurchaseForKidTicket(st, c);
        }
        throw new InvalidParameterException("Ticket type is not of valid type.");
    }

    /**
     * validates whether adult ticket can be purchased
     *
     * @param ticket ticket to be purchased
     * @param c customer purchasing ticket
     *
     * @return error message why ticket cannot be purchased, null when no error
     *
     * @author TomasCh
     */
    private static String validatePurchaseForAdultTicket(SeasonTicket ticket, Customer c) {
        if (ChronoUnit.YEARS.between(c.getBirthDate().toLocalDate(), LocalDate.now()) < 18) {
            return "Only person older than 18 years can purchase adult ticket.";
        }
        for (SeasonTicket st: c.getTickets()) {
            if (st.getType().getName().equals("Adult") && st.getSeason().getSeasonId().equals(ticket.getSeason().getSeasonId())) {
                return "Adult ticket for this season already purchased.";
            }
        }
        return null;
    }

    /**
     * validates whether junior ticket can be purchased
     *
     * @param ticket ticket to be purchased
     * @param c customer purchasing ticket
     *
     * @return error message why ticket cannot be purchased, null when no error
     *
     * @author TomasCh
     */
    private static String validatePurchaseForJuniorTicker(SeasonTicket ticket, Customer c) {
        if (ChronoUnit.YEARS.between(c.getBirthDate().toLocalDate(), LocalDate.now()) >= 18) {
            return "Person over 18 years cannot purchase junior ticket.";
        }
        if (ticket.getSector().getAdultOnly()) {
            return "Junior ticket cannot be purchased into adult only sector.";
        }
        return null;
    }

    /**
     * validates whether kid ticket can be purchased
     *
     * @param ticket ticket to be purchased
     * @param c customer purchasing ticket
     *
     * @return error message why ticket cannot be purchased, null when no error
     *
     * @author TomasCh
     */
    private static String validatePurchaseForKidTicket(SeasonTicket ticket, Customer c) {
        if (ticket.getSector().getAdultOnly()) {
            return "Kid ticket cannot be purchased into adult only sector.";
        }
        SeasonTicket adultTicket = null;
        for (SeasonTicket st: c.getTickets()) {
            if (st.getType().getName().equals("Adult") && st.getSeason().getSeasonId().equals(ticket.getSeason().getSeasonId())) {
                adultTicket = st;
            }
        }
        if (adultTicket != null) {
            if (!adultTicket.getSector().getSectorId().equals(ticket.getSector().getSectorId())) {
                return "Kids ticket has to be purchased to same sector as adult ticket.";
            }
        } else {
            return "Adult ticket for corresponding season has to be purchased before purchasing kids ticket.";
        }
        return null;
    }
}
