package pl.filmveeb.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Rating extends BaseEntity {

    @Column(name = "rating_value")
    @Enumerated(value = EnumType.STRING)
    //todo maybe some collection with sort by values, then changing enum position will not have an effect
    private RatingValue ratingValue;
    private LocalDate date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Film film;

    public RatingValue getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(RatingValue ratingValue) {
        this.ratingValue = ratingValue;
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
