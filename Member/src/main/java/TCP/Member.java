//Amy Wickham 12178502
//COIT132229 Assignmemt 1: client / server

package TCP;

import java.io.Serializable;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private int memberNumber;
    private String lastName;
    private String phone;
    private String address;

    public Member() {

    }

    public Member(int memberNumber, String firstName, String lastName, String address, String phone) {

        this.memberNumber = memberNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Member"
                + "memberNumber=" + memberNumber
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", phone='" + phone + '\''
                + ", address='" + address + '\'';
    }

}
