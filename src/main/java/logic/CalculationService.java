package logic;

import entities.SeasonTicket;

/**
 * Service providing logic for different calculations
 *
 * @author TomasCh
 */
public class CalculationService {

    /**
     * calculates price of ticket in particular sector's price point
     *
     * @param pricePoint determines how much % of original ticket value costs ticket in sector
     * @param ticketTypePrice original ticket price
     *
     * @return calculated price of ticket in particular sector's price point
     *
     * @author TomasCh
     */
    public static Float calculateTicketPrice(Float pricePoint, Float ticketTypePrice) {
        return pricePoint * ticketTypePrice;
    }
}
