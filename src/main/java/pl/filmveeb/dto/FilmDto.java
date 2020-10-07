package pl.filmveeb.dto;

import pl.filmveeb.model.Film;

public class FilmDto {

    private Long id;
    private String title;
    private String productionYear;
    private String genre;
    private MemberDto directorDto;
    private String description;
    private String posterUrl;

    public static FilmDto apply(Film film) {
        MemberDto directorDto = new MemberDto();
        directorDto.setFirstName(film.getDirector().getFirstName());
        directorDto.setLastName(film.getDirector().getLastName());

        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setTitle(film.getTitle());
        filmDto.setProductionYear(film.getProductionYear());
        filmDto.setGenre(String.valueOf(film.getGenre()));
        filmDto.setDirectorDto(directorDto);
        filmDto.setDescription(film.getDescription());
        filmDto.setPosterUrl(film.getPosterUrl());
        return filmDto;
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

}
