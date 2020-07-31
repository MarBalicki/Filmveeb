package pl.filmveeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.repository.FilmRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmRepository repository;

    @Autowired
    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    public void save(Film film) {
        repository.save(film);
    }

    public List<Film> allFilms() {
        return repository.findAll();
    }

    public Optional<Film> findById(Long id) {
        return repository.findById(id);
    }

    public Film getById(Long id) {
        if (findById(id).isPresent()) {
            return repository.getOne(id);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        if (findById(id).isPresent()) {
            repository.deleteById(id);
        }
    }

    public List<Film> getAllFilmsByGenre(Genre genre) {
        return repository.findAll().stream()
                .filter(film -> film.getGenre() == genre)
                .collect(Collectors.toList());
    }
}
