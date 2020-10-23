package pl.filmveeb.dto;

import pl.filmveeb.model.Role;
import pl.filmveeb.model.User;
import pl.filmveeb.validation.PasswordMatches;
import pl.filmveeb.validation.PasswordsMatchValue;
import pl.filmveeb.validation.UniqueEmail;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

    private Long id;
    @Size(max = 50, message = "Maximum {max} characters")
    @Pattern(regexp = "[A-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*", message = "First name should contain only letters")
    private String firstName;
    @Size(max = 50, message = "Maximum {max} characters")
    @Pattern(regexp = "[A-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*", message = "Last name should contain only letters")
    private String lastName;
    @Size(max = 80, message = "Maximum {max} characters")
    @Pattern(regexp = "[A-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*", message = "Only letters")
    private String city;
    @Size(max = 80, message = "Maximum {max} characters")
    private String street;
    private String country;
    @Size(max = 15, message = "Maximum {max} characters")
    private String zipCode;
    private String birthDate;
    @Pattern(regexp = "([+][0-9]{11})|([0-9]{9,11})|", message = "Proper format - 9 digits or '+' with 11 digits")
    private String phoneNumber;
    private Role role;
    @UniqueEmail
    @Size(min = 6, max = 50, message = "In range from {min} to {max} characters")
    private String email;
    @Size(min = 1, max = 100, message = "Minimum {min} characters")
    @PasswordMatches
    private String password;
//    @PasswordsMatchValue(field = "password", fieldMatch = "passwordConfirm", message = "Not match!")
    private String passwordConfirm;

    public static UserDto apply(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setCity(user.getAddress().getCity());
        userDto.setStreet(user.getAddress().getStreet());
        userDto.setCountry(String.valueOf(user.getAddress().getCountry().getPlName()));
        userDto.setZipCode(user.getAddress().getZipCode());
        userDto.setBirthDate(String.valueOf(user.getBirthDate()));
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        return userDto;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
