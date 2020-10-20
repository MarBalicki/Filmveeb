package pl.filmveeb.model;

import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.dto.MemberDto;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Film extends BaseEntity {

    private String title;
    private String productionYear;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    @Embedded
    private Member director;
    private String description;
    private String posterUrl;
    @ManyToMany
    @JoinTable(name = "film_user",
            joinColumns =
            @JoinColumn(name = "film_id"),
            inverseJoinColumns =
            @JoinColumn(name = "user_id"))
    private Set<User> users;
    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private Set<Rating> ratings;

    public static Film apply(FilmDto filmDto) {
        Film film = new Film();
        film.id = filmDto.getId();
        film.title = filmDto.getTitle();
        film.genre = Genre.valueOf(filmDto.getGenre());
        film.productionYear = filmDto.getProductionYear();
        film.director = Member.apply(
                new MemberDto(filmDto.getDirectorDtoFirstName(), filmDto.getDirectorDtoLastName()));
        film.description = filmDto.getDescription();
        film.posterUrl = filmDto.getPosterUrl();
        return film;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public Member getDirector() {
        return director;
    }

    public void setDirector(Member director) {
        this.director = director;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Rating> getRates() {
        return ratings;
    }

    public void setRates(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getAverageRating() {
        double averageRating = 0;
        for (Rating rating : ratings) {
            averageRating += rating.getRatingValue().getValue();
        }
        return averageRating != 0 ? String.format("%.2f", averageRating / ratings.size()) : "brak";
    }
}
