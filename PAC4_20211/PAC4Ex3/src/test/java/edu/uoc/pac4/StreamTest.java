package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StreamTest {

    //Digital Products
    Product ebook1, ebook2, audiobook1, audiobook2;

    //Physical Products
    PhysicalProduct book1, book2, magazine1, movie1;

    ShoppingCart shoppingCart;

    @BeforeAll
    void init() {
        try {
            Field field = DigitalProduct.class.getDeclaredField("salesTotal");
            field.setAccessible(true);
            field.set(null, 0);

            Field field2 = Product.class.getDeclaredField("referenceId");
            field2.setAccessible(true);
            field2.set(null, 15000);

            //Digital Products
            ebook1 = new Ebook("Harry Potter 1", 1997, "Harry Potter and the Philosopher's Stone", 14.25, 3498);
            ebook2 = new Ebook("The Truth About the Harry Quebert Affair", 2012, "Bestseller of Joel Dicker", 12.12, 222);
            audiobook1 = new Audiobook("The Lord of the Rings", 2003, "The Fellowship of the Ring", 25.00, 12500, 400);
            audiobook2 = new Audiobook("Dune", 2020, "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides", 25.00, 10000, 360);

            //Physical Products
            book1 = new Book("Harry Potter 2", 1998, "Harry Potter and the Chamber of Secrets", 18.00, 4, 251);
            book2 = new Book("Harry Potter 3", 1999, "Harry Potter and the Prisoner of Azkaban", 15.90, 40, 317, "9780545162074");
            magazine1 = new Magazine("Pokemon (issue 59)", 2012, "Pokemon is an exciting magazine", 7.89, 30);
            movie1 = new Movie("E.T", 1982, "E.T. the Extra-Terrestrial", 10.00, 1);

            shoppingCart = new ShoppingCart("Cart 1");
            shoppingCart.add(ebook1);
            shoppingCart.add(ebook2);
            shoppingCart.add(audiobook1);
            shoppingCart.add(audiobook2);
            shoppingCart.add(ebook1);
            shoppingCart.add(book1);
            shoppingCart.add(book2);
            shoppingCart.add(magazine1);
            shoppingCart.add(movie1);
            shoppingCart.add(book1);

        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testGetDigitalProductOrderByName() {
        assertEquals("[Dune, Harry Potter 1, Harry Potter 1, The Lord of the Rings, The Truth About the Harry Quebert Affair]", shoppingCart.getDigitalProductOrderByName().toString());
    }


    @Test
    @Order(2)
    void testGetDistinctReferenceByVATReduced() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        shoppingCart.getDistinctReferenceByVAT(VAT.REDUCED);
        assertEquals("PROD.15004\n" +
                "PROD.15005", outputStreamCaptor.toString().trim());
    }

    @Test
    @Order(3)
    void testGetDistinctReferenceByVATStandard() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        shoppingCart.getDistinctReferenceByVAT(VAT.STANDARD);
        assertEquals("PROD.15002\n" +
                "PROD.15003\n" +
                "PROD.15007", outputStreamCaptor.toString().trim());
    }

    @Test
    @Order(4)
    void testAveragePriceBooks() {
        assertEquals(17.3, shoppingCart.averagePriceBooks());
    }

}
