package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sector {
    @Id
    private String sectorId;
    private Float pricePoint;

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public Float getPricePoint() {
        return pricePoint;
    }

    public void setPricePoint(Float pricePoint) {
        this.pricePoint = pricePoint;
    }

    public String toString() {
        return sectorId;
    }
}