package edu.uoc.pac4;

public class Library{
    private String name;
    private String nif;

    private Address address;
    private ContactInformation contactInformation;

    public Library(String name, String nif, double latitude, double longitude, String mail, String telephone) {
        setName(name);
        setNif(nif);

        address = new Address(latitude, longitude);
        contactInformation = new ContactInformation(mail, telephone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Address getAddress() {
        return address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

}
