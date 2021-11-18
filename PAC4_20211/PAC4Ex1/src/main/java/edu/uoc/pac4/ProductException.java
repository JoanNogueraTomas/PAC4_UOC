package edu.uoc.pac4;

public class ProductException extends Exception {
    //Var
    public static String MSG_ERR_NAME = "[ERROR] Product's name cannot be longer than 50 characters";
    public static String MSG_PUBLICATION_YEAR = "[ERROR] Product's publicationYear cannot be longer later than current year";
    public static String MSG_DESCRIPTION = "[ERROR] Product's description cannot be longer than 250 characters";
    public static String MSG_ERR_NEGATIVE = "[ERROR] Product's price cannot be negative";

    //Constructors

    public ProductException(){
        //TODO
    }
    public ProductException(String msg) {
        //TODO
    }
}
