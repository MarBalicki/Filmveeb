package pl.filmveeb.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
    @ManyToMany
    @JoinTable(name = "user_film",
            joinColumns =
            @JoinColumn(name = "user_id"),
            inverseJoinColumns =
            @JoinColumn(name = "film_id"))
    private Set<Film> films;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Rate> rates;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, Role role, String password, String matchingPassword, Set<Film> films, Set<Rate> rates) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.films = films;
        this.rates = rates;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> filmSet) {
        this.films = filmSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
