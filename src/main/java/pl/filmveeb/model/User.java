package pl.filmveeb.model;

import pl.filmveeb.dto.UserDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Embedded
    private Address address;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String password;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Film> films;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Rating> ratings;

    public static User apply(UserDto userDto, String password) {
        User user = new User();
        //todo id
        user.id = userDto.getId();
        user.firstName = userDto.getFirstName();
        user.lastName = userDto.getLastName();
        user.address = Address.apply(userDto);
        user.birthDate = LocalDate.parse(userDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.phoneNumber = userDto.getPhoneNumber();
        user.role = userDto.getRole();
        user.email = userDto.getEmail();
        user.password = password;
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> filmSet) {
        this.films = filmSet;
    }



//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return Objects.equals(email, user.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(email);
//    }
}
