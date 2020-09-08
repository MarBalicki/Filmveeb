package pl.filmveeb.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rateValue;
    private Date date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Film film;

    public Rate() {
    }

    public Rate(int rateValue, Date date, User user, Film film) {
        this.rateValue = rateValue;
        this.date = date;
        this.user = user;
        this.film = film;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
