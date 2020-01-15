package entities;

import javax.persistence.*;

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
    private String sector;

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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
