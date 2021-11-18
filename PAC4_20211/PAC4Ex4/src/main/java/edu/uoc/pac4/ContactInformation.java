package edu.uoc.pac4;

public class ContactInformation{

    private String mail;
    private String telephone;

    public ContactInformation(String mail, String telephone) {
        setMail(mail);
        setTelephone(telephone);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
