package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents database entity customer
 *
 * @author TomasCh
 */
@Entity
public class Customer {
    @Id
    private String login;
    private String type;
    private String password;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date registerDate;
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "customerticket",
            joinColumns = { @JoinColumn(name = "customerId") },
            inverseJoinColumns = { @JoinColumn(name = "ticketId") }
    )
    private Set<SeasonTicket> tickets = new HashSet<>();


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Set<SeasonTicket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<SeasonTicket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(SeasonTicket sT) {
        if (tickets == null) {
            tickets = new HashSet<>();
        }
        tickets.add(sT);
    }

    public String toString() {
        return this.getLogin();
    }
}
