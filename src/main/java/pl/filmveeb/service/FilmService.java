package pl.filmveeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.Film;
import pl.filmveeb.repository.FilmRepository;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository repository;

    public void save(Film film) {
        repository.save(film);
    }

    public List<Film> listAll() {
        return repository.findAll();
    }

    public Film get(Long id) {
        return repository.getOne(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
