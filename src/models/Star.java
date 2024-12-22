package models;

import java.util.Date;

public class Star {
    private String firstname;
    private String lastname;


    public Star(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String toString() {
        return this.firstname + " " + this.lastname;
    }
}
