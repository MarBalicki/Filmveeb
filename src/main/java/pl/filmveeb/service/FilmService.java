package pl.filmveeb.service;

import org.springframework.stereotype.Service;
import pl.filmveeb.dto.FilmDto;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Member;
import pl.filmveeb.repository.FilmRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void addFilm(FilmDto filmDto) {
        if (findByTitle(filmDto.getTitle()).isEmpty()) {
            Film film = Film.apply(filmDto);
            Member director = Member.apply(filmDto.getDirectorDto());
            film.setDirector(director);
            filmRepository.save(film);
        } else {
            System.out.println("Film with that title exists in our data base!");
        }
    }

    private Optional<FilmDto> findByTitle(String title) {
        return filmRepository.findAll()
                .stream()
                .filter(film -> film.getTitle().equals(title))
                .findFirst()
                .map(FilmDto::apply);
    }

    public List<FilmDto> getAllFilms() {
        return filmRepository
                .findAll()
                .stream()
                .map(FilmDto::apply)
                .collect(Collectors.toList());
    }

    public FilmDto getFilmById(Long id) {
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

    public void updateFilm(Long id, FilmDto filmDto) {
        Film currentFilm = filmRepository.getOne(id);
        currentFilm.setTitle(filmDto.getTitle());
        Member director = Member.apply(filmDto.getDirectorDto());
        currentFilm.setDirector(director);
        currentFilm.setGenre(Genre.valueOf(filmDto.getGenre()));
        currentFilm.setProductionYear(filmDto.getProductionYear());
        currentFilm.setDescription(filmDto.getDescription());
        filmRepository.save(currentFilm);
    }
}
