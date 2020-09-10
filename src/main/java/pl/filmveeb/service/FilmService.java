package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.repository.FilmRepository;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final UserService userService;

    public FilmService(FilmRepository filmRepository, UserService userService) {
        this.filmRepository = filmRepository;
        this.userService = userService;
    }

    public void saveFilm(Film film) {
        filmRepository.save(film);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

//    public Optional<Film> findFilmById(Long id) {
//        return filmRepository.findById(id);
//    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found!"));
    }

    public void deleteFilmById(Long id) {
        Film filmById = getFilmById(id);
        filmById.getUsers()
                .forEach(user -> userService.getUserByEmial(user.getEmail())
                        .getFilms()
                        .remove(filmById));
        filmById.getUsers().removeAll(filmById.getUsers());
        filmRepository.deleteById(id);
    }

    public List<Film> getAllFilmsByGenre(Genre genre) {
        return filmRepository.findAllByGenre(genre);
    }


}
