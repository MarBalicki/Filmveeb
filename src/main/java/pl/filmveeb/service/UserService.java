package pl.filmveeb.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.filmveeb.dto.UserDto;
import pl.filmveeb.model.Address;
import pl.filmveeb.model.Country;
import pl.filmveeb.model.Role;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserDto getUserByEmial(String emial) {
        return userRepository
                .findUserByEmail(emial)
                .map(UserDto::apply)
                .orElseThrow(() -> new RuntimeException("User not exists!"));
    }

    public boolean sameEmailAsCurrentUser(UserDto modelUser) {
        UserDto currentUserDto = getLoggedUserDto();
        return currentUserDto.getEmail().equals(modelUser.getEmail());
    }

    public boolean passwordMatchToCurrentUser(String password) {
        User loggedUser = getLoggedUser();
        return passwordEncoder.matches(password, loggedUser.getPassword());
    }

    public void updateCurrentUser(UserDto modelUser) {
        User loggedUser = getLoggedUser();
        loggedUser.setFirstName(modelUser.getFirstName());
        loggedUser.setLastName(modelUser.getLastName());
        loggedUser.setBirthDate(LocalDate.parse(modelUser.getBirthDate()));
        loggedUser.setPhoneNumber(modelUser.getPhoneNumber());
        Address address = new Address();
        address.setCity(modelUser.getCity());
        address.setStreet(modelUser.getStreet());
        address.setCountry(Country.fromSymbol(modelUser.getCountry()));
        address.setZipCode(modelUser.getZipCode());
        loggedUser.setAddress(address);
        loggedUser.setEmail(modelUser.getEmail());
        userRepository.save(loggedUser);
    }

    public UserDto getLoggedUserDto() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmial(currentUserEmail);
    }

    public User getLoggedUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findUserByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public UserDto getUserDtoById(Long id) {
        return UserDto.apply(userRepository.getOne(id));
    }

    public boolean isUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && SecurityContextHolder.getContext().getAuthentication() != null
                && !(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken);
    }

//    public List<User> getAllUsers() {
//        return repository.findAll();
//    }
}
