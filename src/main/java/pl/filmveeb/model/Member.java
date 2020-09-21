package pl.filmveeb.model;

import pl.filmveeb.dto.MemberDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Member {

    @Column(name = "director_first")
    private String firstName;
    @Column(name = "director_last")
    private String lastName;

    public static Member apply(MemberDto memberDto) {
        Member director = new Member();
        director.firstName = memberDto.getFirstName();
        director.lastName = memberDto.getLastName();
        return director;
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

}
