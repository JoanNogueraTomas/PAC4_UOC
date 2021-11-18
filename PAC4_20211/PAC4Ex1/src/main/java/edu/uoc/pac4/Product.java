package edu.uoc.pac4;

//imports
import java.time.LocalDate;

public class Product {
    //Var
    private static int referenceId = 0;
    private String reference;
    private String name;
    private int publicationYear;
    private String description;
    private double price;

    //Methods

    public Product() throws Exception{
        setName("Lorem Ipsum");
        setPublicationYear(2011);
        setDescription("lorem ipsum description");
        setPrice(7.5);
    }

    public Product(String _name, int _publicationYear, String _description, double _price) throws Exception {
        name = _name;
        publicationYear = _publicationYear;
        description = _description;
        price = _price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name.length() > 50) throw new Exception("[ERROR] Product's name cannot be longer than 50");
        else this.name = name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) throws Exception { //TODO throw ProductException Exception
        LocalDate current_date = LocalDate.now();
        if(publicationYear > current_date.getYear()) throw new Exception("[ERROR] Product's publicationYear cannot be later than current year");
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {//TODO throw ProductException Exception
        if(description.length() > 250) throw new Exception("[ERROR] Product's description cannot be longer than 250 characters");
        else this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws Exception {
        if(price < 0) throw new Exception();
        this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public void createReference(){ //TODO
        String prod = "PROD.";
        reference = prod + String.valueOf(referenceId);
        referenceId++;
    }
    @Override
    public String toString() {
        return "Product{" +
                "reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", publicationYear=" + publicationYear +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
