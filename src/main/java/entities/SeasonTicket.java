package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SeasonTicket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idSeasonTicket;
    private String type;
    @ManyToOne
    @JoinColumn(name = "seasonId")
    private Season season;
    private Float price;
    @ManyToOne
    @JoinColumn(name = "sectorId")
    private Sector sector;

    @ManyToMany(mappedBy = "tickets")
    private Set<Customer> employees = new HashSet<>();

    public Integer getIdSeasonTicket() {
        return idSeasonTicket;
    }

    public void setIdSeasonTicket(Integer idSeasonTicket) {
        this.idSeasonTicket = idSeasonTicket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
