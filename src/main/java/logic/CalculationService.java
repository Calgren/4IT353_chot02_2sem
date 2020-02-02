package logic;

import entities.SeasonTicket;

public class CalculationService {

    public static Float calculateTicketPrice(Float pricePoint, Float ticketTypePrice) {
        return pricePoint * ticketTypePrice;
    }
}
