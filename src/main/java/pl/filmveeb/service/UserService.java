package pl.filmveeb.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.Film;
import pl.filmveeb.model.Genre;
import pl.filmveeb.model.Role;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FilmService filmService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FilmService filmService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.filmService = filmService;
    }

    public void addUser(UserDto userDto) {
        //todo
        String password = passwordEncoder.encode(userDto.getPassword());
        if (findByEmial(userDto.getEmail()).isEmpty()) {
            if (userDto.getEmail().equals("admin@admin.pl")) {
                userDto.setRole(Role.ADMIN);
            } else {
                userDto.setRole(Role.USER);
            }
            User userToSave = User.apply(userDto, password);
            userRepository.save(userToSave);
        } else {
            System.out.println("Sorry, that emial address is in our base!");
        }
    }

    public Optional<UserDto> findByEmial(String emial) {
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getEmail().equals(emial))
                .findFirst()
                .map(UserDto::apply);
    }

    public User getUserByEmial(String emial) {
        return userRepository.findUserByEmail(emial)
                .orElseThrow(() -> new RuntimeException("User not exists!"));
    }

    public void updateCurrentUser(User currentUser, User modelUser) {
        currentUser.setFirstName(modelUser.getFirstName());
        currentUser.setLastName(modelUser.getLastName());
        currentUser.setEmail(modelUser.getEmail());
        userRepository.save(currentUser);
    }

    public Set<Film> getAllUserFilmsByGenre(Genre genre) {
        return userRepository.getOne(getLoggedUser().getId())
                .getFilms()
                .stream()
                .filter(film -> film.getGenre().equals(genre))
                .collect(Collectors.toSet());
    }

    public boolean confirmCorrectPassword(String password) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = getUserByEmial(userEmail);
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    public User getLoggedUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmial(currentUserEmail);
    }

    public boolean sameEmails(User currentUser, User modelUser) {
        return currentUser.getEmail().equals(modelUser.getEmail());
    }

//    public void addToFavorite(Long filmId) {
//        User currentUser = getLoggedUser();
//        filmService.getFilmById(filmId).getUsers().add(currentUser);
//        userRepository.save(currentUser);
//    }
//
//    public void removeFromFavorite(Long filmId) {
//        User currentUser = getLoggedUser();
//        filmService.getFilmById(filmId).getUsers().remove(currentUser);
//        userRepository.save(currentUser);
//    }


//    public List<User> getAllUsers() {
//        return repository.findAll();
//    }
}
