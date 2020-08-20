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
    @ManyToMany(mappedBy = "films", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    public Film() {
    }

    public Film(Long id, String title, String productionYear, String director, Genre genre, String description) {
        this.id = id;
        this.title = title;
        this.productionYear = productionYear;
        this.director = director;
        this.genre = genre;
        this.description = description;
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


}
