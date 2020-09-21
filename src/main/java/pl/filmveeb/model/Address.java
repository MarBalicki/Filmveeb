package pl.filmveeb.model;

import pl.filmveeb.dto.UserDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Address {

    private String city;
    private String street;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Column(name = "zip_code")
    private String zipCode;

    public static Address apply(UserDto userDto) {
        Address address = new Address();
        address.city = userDto.getCity();
        address.street = userDto.getStreet();
        address.country = Country.fromSymbol(userDto.getCountry());
        address.zipCode = userDto.getZipCode();
        return address;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
