package pl.filmveeb.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String surname;
    @NotNull
    @NotEmpty
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
    @ManyToMany(mappedBy = "userSet", fetch = FetchType.EAGER)
    private Set<Film> filmSet;

    public User() {
    }

    public User(Long id, String username, String surname, String email, Role role, String password, String matchingPassword, Set<Film> filmSet) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.filmSet = filmSet;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public Set<Film> getFilmSet() {
        return filmSet;
    }

    public void setFilmSet(Set<Film> filmSet) {
        this.filmSet = filmSet;
    }
}
