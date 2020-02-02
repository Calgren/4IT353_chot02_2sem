package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketType {
    @Id
    private String name;
    private Float defaultPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String toString() {
        return this.name;
    }
}
