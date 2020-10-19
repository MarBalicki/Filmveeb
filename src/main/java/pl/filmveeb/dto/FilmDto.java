package pl.filmveeb.dto;

import pl.filmveeb.model.Film;
import pl.filmveeb.model.Rating;
import pl.filmveeb.model.User;

import java.util.Optional;

public class FilmDto {

    private Long id;
    private String title;
    private String productionYear;
    private String genre;
    private MemberDto directorDto;
    private String description;
    private String posterUrl;
    private String averageRating;
    private String userRating;

    public static FilmDto apply(Film film) {
        FilmDto filmDto = getFilmDto(film);
        filmDto.setAverageRating(film.getAverageRating());
        return filmDto;
    }

    public static FilmDto applyWithUserRating(Film film, User loggedUser) {
        FilmDto filmDto = getFilmDto(film);
        filmDto.setUserRating(getUserFilmRating(film, loggedUser));
        return filmDto;
    }

    private static FilmDto getFilmDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setTitle(film.getTitle());
        filmDto.setProductionYear(film.getProductionYear());
        filmDto.setGenre(String.valueOf(film.getGenre()));
        filmDto.setDirectorDto(getMemberDto(film));
        filmDto.setDescription(film.getDescription());
        filmDto.setPosterUrl(film.getPosterUrl());
        return filmDto;
    }

    public static String getUserFilmRating(Film film, User loggedUser) {
        Optional<Rating> ratingOptional = loggedUser.getRatings()
                .stream()
                .filter(rating -> rating.getUser().equals(loggedUser))
                .filter(rating -> rating.getFilm().equals(film))
                .findFirst();
        return ratingOptional.isPresent()
                ? String.format("%.2f", (double) ratingOptional.get().getRatingValue().getValue())
                : "brak";
    }

    private static MemberDto getMemberDto(Film film) {
        MemberDto directorDto = new MemberDto();
        directorDto.setFirstName(film.getDirector().getFirstName());
        directorDto.setLastName(film.getDirector().getLastName());
        return directorDto;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MemberDto getDirectorDto() {
        return directorDto;
    }

    public void setDirectorDto(MemberDto directorDto) {
        this.directorDto = directorDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
