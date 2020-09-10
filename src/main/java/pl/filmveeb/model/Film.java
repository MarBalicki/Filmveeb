package pl.filmveeb.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String productionYear;
    private String director;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private String description;
    @ManyToMany
    @JoinTable(name = "film_user",
            joinColumns =
            @JoinColumn(name = "film_id"),
            inverseJoinColumns =
            @JoinColumn(name = "user_id"))
    private Set<User> users;
    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private Set<Rate> rates;

    public Film() {
    }

    public Film(Long id, String title, String productionYear, String director, Genre genre, String description, Set<Rate> rates) {
        this.id = id;
        this.title = title;
        this.productionYear = productionYear;
        this.director = director;
        this.genre = genre;
        this.description = description;
        this.rates = rates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
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

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }
}
