package edu.uoc.pac4;

public class Product {
    //Var
    private static int referenceId = 0;
    private String reference;
    private String name;
    private int publicationYear;
    private String description;
    private double price;

    //Methods

    public Product(){
        setName("Lorem Ipsum");
        setPublicationYear(2011);
        setDescription("lorem ipsum description");
        setPrice(7.5);
    }

    public Product(String _name, int _publicationYear, String _description, double _price){
        name = _name;
        publicationYear = _publicationYear;
        description = _description;
        price = _price;

    }
}
