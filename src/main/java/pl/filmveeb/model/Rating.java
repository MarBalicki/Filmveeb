package pl.filmveeb.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Rating extends BaseEntity {

    private int stars;
    private LocalDate date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Film film;

    public int getStars() {
        return stars;
    }

    public void setStars(int rateValue) {
        this.stars = rateValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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