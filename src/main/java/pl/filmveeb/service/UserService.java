package pl.filmveeb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.Role;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public Optional<User> getByEmial(String emial) {
        return repository
                .findAll()
                .stream()
                .filter(user -> user.getEmail().equals(emial))
                .findFirst();
    }


    public User getUserByEmial(String emial) {
        Optional<User> user = getByEmial(emial);
        return user.orElse(null);
    }
}
