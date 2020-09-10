package pl.filmveeb.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.repository.FilmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final UserService userService;

    public FilmService(@Lazy FilmRepository filmRepository, @Lazy UserService userService) {
        this.filmRepository = filmRepository;
        this.userService = userService;
    }

    public void addFilm(Film film) {
        if (findByTitle(film.getTitle()).isEmpty()) {
            filmRepository.save(film);
        } else {
            System.out.println("Film with that title exists in our data base!");
        }
    }

    private Optional<Film> findByTitle(String title) {
        return filmRepository.findAll()
                .stream()
                .filter(film -> film.getTitle().equals(title))
                .findFirst();
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found!"));
    }

    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }

    public List<Film> getAllFilmsByGenre(Genre genre) {
        return filmRepository.findAllByGenre(genre);
    }

    public void updateFilm(Long id, Film filmModel) {
        Film currentFilm = filmRepository.getOne(id);
        currentFilm.setTitle(filmModel.getTitle());
        currentFilm.setDirector(filmModel.getDirector());
        currentFilm.setGenre(filmModel.getGenre());
        currentFilm.setProductionYear(filmModel.getProductionYear());
        currentFilm.setDescription(filmModel.getDescription());
        filmRepository.save(currentFilm);
    }
}
