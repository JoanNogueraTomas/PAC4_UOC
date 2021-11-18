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


    //Constructors
    public Product() throws ProductException {
        setName("Lorem Ipsum");
        setPublicationYear(2011);
        setDescription("lorem ipsum description");
        setPrice(7.5);
    }

    public Product(String _name, int _publicationYear, String _description, double _price) {
        name = _name;
        publicationYear = _publicationYear;
        description = _description;
        price = _price;
    }

    //Methods

    public String getName() {
        return name;
    }

    public void setName(String name) throws ProductException {
        if(name.length() > 50) throw new ProductException(ProductException.MSG_ERR_NAME);
        else this.name = name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) throws ProductException {
        LocalDate current_date = LocalDate.now();
        if(publicationYear > current_date.getYear()) throw new ProductException(ProductException.MSG_PUBLICATION_YEAR);
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws ProductException {
        if(description.length() > 250) throw new ProductException(ProductException.MSG_DESCRIPTION);
        else this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws ProductException {
        if(price < 0) throw new ProductException(ProductException.MSG_ERR_NEGATIVE);
        else this.price = price;
    }

    public String getReference() {
        return reference;
    }

    public void createReference() {
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
