package pl.filmveeb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Role;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.FilmRepository;
import pl.filmveeb.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        if (findByEmial(user.getEmail()).isPresent()) {
            System.out.println("Sorry, that emial address is in our base!");
            return;
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByEmial(String emial) {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getEmail().equals(emial))
                .findFirst();
    }


    public User getUserByEmial(String emial) {
        Optional<User> user = findByEmial(emial);
        if (user.isPresent()) {
            User presentUser = user.get();
            return userRepository.getOne(presentUser.getId());
        }
        return null;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Set<Film> getAllUserFilms(User user) {
        return userRepository.getOne(user.getId()).getFilms();
    }

    public Set<Film> getAllUserFilmsByGenre(User user, Genre genre) {
        return userRepository.getOne(user.getId())
                .getFilms()
                .stream()
                .filter(film -> film.getGenre() == genre)
                .collect(Collectors.toSet());
    }


//    public List<User> getAllUsers() {
//        return repository.findAll();
//    }
}
