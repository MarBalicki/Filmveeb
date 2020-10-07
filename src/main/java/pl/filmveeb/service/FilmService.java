package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Member;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.FilmRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final UserService userService;

    public FilmService(FilmRepository filmRepository, UserService userService) {
        this.filmRepository = filmRepository;
        this.userService = userService;
    }

    public void addFilm(FilmDto filmDto) {
        Member director = Member.apply(filmDto.getDirectorDto());
        if (filmRepository.existsByTitleAndDirector(filmDto.getTitle(), director)) {
            System.out.println("Film with that title exists in our data base!");
        } else {
            Film film = Film.apply(filmDto);
            filmRepository.save(film);
        }
    }

    public List<FilmDto> getAllFilms() {
        return filmRepository
                .findAll()
                .stream()
                .map(FilmDto::apply)
                .collect(Collectors.toList());
    }

    public FilmDto getFilmDtoById(Long id) {
        return filmRepository.findById(id)
                .map(FilmDto::apply)
                .orElseThrow(() -> new RuntimeException("Film not found!"));
    }

    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }

    public List<FilmDto> getAllFilmsByGenre(Genre genre) {
        return filmRepository
                .findAllByGenre(genre)
                .stream()
                .map(FilmDto::apply)
                .collect(Collectors.toList());
    }

    public void updateFilm(FilmDto filmDto) {
        Film currentFilm = filmRepository.getOne(filmDto.getId());
        currentFilm.setTitle(filmDto.getTitle());
        Member director = Member.apply(filmDto.getDirectorDto());
        currentFilm.setDirector(director);
        currentFilm.setGenre(Genre.valueOf(filmDto.getGenre()));
        currentFilm.setProductionYear(filmDto.getProductionYear());
        currentFilm.setDescription(filmDto.getDescription());
        currentFilm.setPosterUrl(filmDto.getPosterUrl());
        filmRepository.save(currentFilm);
    }

    public Film getFilmById(Long filmId) {
        return filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found!"));
    }

    public Set<FilmDto> getCurrentUserAllFilms() {
        return userService.getLoggedUser()
                .getFilms()
                .stream()
                .map(FilmDto::apply)
                .collect(Collectors.toSet());
    }

    public void addToFavorite(Long filmId) {
        User loggedUser = userService.getLoggedUser();
        Film filmById = getFilmById(filmId);
        filmById.getUsers().add(loggedUser);
        filmRepository.save(filmById);
    }

    public void removeFromFavorite(Long filmId) {
        User loggedUser = userService.getLoggedUser();
        Film filmById = getFilmById(filmId);
        filmById.getUsers().remove(loggedUser);
        filmRepository.save(filmById);
    }

    public Set<FilmDto> getAllUserFilmsByGenre(Genre genre) {
        return userService.getLoggedUser()
                .getFilms()
                .stream()
                .filter(film -> film.getGenre().equals(genre))
                .map(FilmDto::apply)
                .collect(Collectors.toSet());
    }


}
