package pl.filmveeb.dto;

import pl.filmveeb.model.Member;

public class MemberDto {

    private String firstName;
    private String lastName;

    public static MemberDto apply(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());
        return memberDto;
    }

    public MemberDto() {
    }

    public MemberDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
