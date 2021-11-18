package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartTest {

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

        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testSetTotal2Decimals() {
        assertEquals(0, shoppingCart.getTotal());

        shoppingCart.setTotal(2);
        assertEquals(2, shoppingCart.getTotal());

        shoppingCart.setTotal(2.1);
        assertEquals(2.1, shoppingCart.getTotal());

        shoppingCart.setTotal(2.13);
        assertEquals(2.13, shoppingCart.getTotal());

        shoppingCart.setTotal(2.134);
        assertEquals(2.13, shoppingCart.getTotal());

        shoppingCart.setTotal(2.138);
        assertEquals(2.14, shoppingCart.getTotal());

        shoppingCart.setTotal(2.135);
        assertEquals(2.13, shoppingCart.getTotal());

        shoppingCart.setTotal(2.1351);
        assertEquals(2.14, shoppingCart.getTotal());

        shoppingCart.setTotal(2.13689878);
        assertEquals(2.14, shoppingCart.getTotal());

        shoppingCart.setTotal(0);
        assertEquals(0, shoppingCart.getTotal());
    }

    @Test
    @Order(2)
    void testConstructor() {
        assertEquals("Cart 1", shoppingCart.getClientName());
        assertEquals(0, shoppingCart.getTotal());
        assertNotNull(shoppingCart.getCart());
    }

    @Test
    @Order(3)
    void testAddProduct() {
        assertEquals(0, shoppingCart.getCart().size());
        assertEquals(0, ebook1.getSales());
        assertEquals(0, DigitalProduct.getSalesTotal());
        shoppingCart.add(ebook1);
        assertEquals(1, shoppingCart.getCart().size());
        assertEquals(1, ebook1.getSales());
        assertEquals(1, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(4)
    void testAddProduct2() {
        shoppingCart.add(ebook1);
        shoppingCart.add(ebook2);
        assertEquals(3, shoppingCart.getCart().size());
        assertEquals(2, ebook1.getSales());
        assertEquals(1, ebook2.getSales());
        assertEquals(3, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(5)
    void testAddProduct3() {
        shoppingCart.add(ebook1);
        shoppingCart.add(audiobook1);
        shoppingCart.add(audiobook1);
        assertEquals(4, book1.getStock());
        shoppingCart.add(book1);
        assertEquals(7, shoppingCart.getCart().size());
        assertEquals(3, ebook1.getSales());
        assertEquals(1, ebook2.getSales());
        assertEquals(2, audiobook1.getSales());
        assertEquals(1, book1.getSales());
        assertEquals(3, book1.getStock());
        assertEquals(6, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(6)
    void testAddProduct4() {
        shoppingCart.add(audiobook1);
        shoppingCart.add(audiobook2);
        shoppingCart.add(book1);
        shoppingCart.add(book1);
        shoppingCart.add(book1);
        shoppingCart.add(book2);
        shoppingCart.add(book2);
        assertEquals(14, shoppingCart.getCart().size());
        assertEquals(3, audiobook1.getSales());
        assertEquals(1, audiobook2.getSales());
        assertEquals(4, book1.getSales());
        assertEquals(0, book1.getStock());
        assertEquals(2, book2.getSales());
        assertEquals(38, book2.getStock());
        assertEquals(8, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(7)
    void testAddProduct5() {
        shoppingCart.add(magazine1);
        shoppingCart.add(magazine1);
        shoppingCart.add(magazine1);
        shoppingCart.add(movie1);
        assertEquals(18, shoppingCart.getCart().size());
        assertEquals(3, magazine1.getSales());
        assertEquals(27, magazine1.getStock());
        assertEquals(1, movie1.getSales());
        assertEquals(0, movie1.getStock());
        assertEquals(8, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(8)
    void testAddProduct6() {
        assertFalse(shoppingCart.add(book1));
        assertEquals(18, shoppingCart.getCart().size());
        assertEquals(4, book1.getSales());
        assertEquals(0, book1.getStock());
        assertEquals(8, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(9)
    void testAddProduct7() {
        Exception ex = assertThrows(Exception.class, () ->	shoppingCart.add(null));
        assertEquals("[ERROR] Product object cannot be null", ex.getMessage());
    }

    @Test
    @Order(10)
    void testRemoveProduct1() {
        shoppingCart.remove(movie1);
        assertEquals(17, shoppingCart.getCart().size());
        assertEquals(0, movie1.getSales());
        assertEquals(1, movie1.getStock());
        assertEquals(8, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(11)
    void testRemoveProduct2() {
        shoppingCart.remove(book1);
        assertEquals(16, shoppingCart.getCart().size());
        assertEquals(3, book1.getSales());
        assertEquals(1, book1.getStock());
        assertEquals(8, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(12)
    void testRemoveProduct3() {
        shoppingCart.remove(audiobook1);
        shoppingCart.remove(book2);
        assertEquals(14, shoppingCart.getCart().size());
        assertEquals(2, audiobook1.getSales());
        assertEquals(1, book2.getSales());
        assertEquals(39, book2.getStock());
        assertEquals(7, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(13)
    void testExists() {
        assertTrue(shoppingCart.exists(book2));
        assertTrue(shoppingCart.exists(book1));
        assertFalse(shoppingCart.exists(movie1));
    }

    @Test
    @Order(14)
    void testIsEmpty1() {
        assertFalse(shoppingCart.isEmpty());
    }

    @Test
    @Order(15)
    void testGroupAndSort() {
        TreeMap t = shoppingCart.groupAndSort(false);
        assertEquals("{EBOOK: PROD.15000; 24 years; 14.25€; VAT 4.0%, Rating: 0, Votes: 0=3, BOOK: PROD.15004; 23 years; 18.0€; VAT 10.0%, Stock: 1=3, BOOK: PROD.15005; 22 years; 15.9€; VAT 10.0%, Stock: 39=1, AUDIOBOOK: PROD.15002; 18 years; 25.0€; VAT 21.0%, Rating: 0, Votes: 0=2, EBOOK: PROD.15001; 9 years; 12.12€; VAT 4.0%, Rating: 0, Votes: 0=1, MAGAZINE: PROD.15006; 9 years; 7.89€; VAT 4.0%, Stock: 27=3, AUDIOBOOK: PROD.15003; 1 years; 25.0€; VAT 21.0%, Rating: 0, Votes: 0=1}", t.toString());

        t = shoppingCart.groupAndSort(true);
        assertEquals("{AUDIOBOOK: PROD.15003; 1 years; 25.0€; VAT 21.0%, Rating: 0, Votes: 0=1, AUDIOBOOK: PROD.15002; 18 years; 25.0€; VAT 21.0%, Rating: 0, Votes: 0=2, BOOK: PROD.15004; 23 years; 18.0€; VAT 10.0%, Stock: 1=3, BOOK: PROD.15005; 22 years; 15.9€; VAT 10.0%, Stock: 39=1, EBOOK: PROD.15000; 24 years; 14.25€; VAT 4.0%, Rating: 0, Votes: 0=3, EBOOK: PROD.15001; 9 years; 12.12€; VAT 4.0%, Rating: 0, Votes: 0=1, MAGAZINE: PROD.15006; 9 years; 7.89€; VAT 4.0%, Stock: 27=3}", t.toString());
    }

    @Test
    @Order(16)
    void testPrintInvoiceByPublicationYear() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        shoppingCart.printInvoice(false);
        assertEquals("PROD.15000 - Units: 3 - (14.25€) 1997\n" +
                "PROD.15004 - Units: 3 - (18.0€) 1998\n" +
                "PROD.15005 - Units: 1 - (15.9€) 1999\n" +
                "PROD.15002 - Units: 2 - (25.0€) 2003\n" +
                "PROD.15001 - Units: 1 - (12.12€) 2012\n" +
                "PROD.15006 - Units: 3 - (7.89€) 2012\n" +
                "PROD.15003 - Units: 1 - (25.0€) 2020", outputStreamCaptor.toString().trim());
    }

    @Test
    @Order(17)
    void testPrintInvoiceByPriceDesc() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        shoppingCart.printInvoice(true);
        assertEquals("PROD.15003 - Units: 1 - (25.0€) 2020\n" +
                "PROD.15002 - Units: 2 - (25.0€) 2003\n" +
                "PROD.15004 - Units: 3 - (18.0€) 1998\n" +
                "PROD.15005 - Units: 1 - (15.9€) 1999\n" +
                "PROD.15000 - Units: 3 - (14.25€) 1997\n" +
                "PROD.15001 - Units: 1 - (12.12€) 2012\n" +
                "PROD.15006 - Units: 3 - (7.89€) 2012", outputStreamCaptor.toString().trim());
    }

    @Test
    @Order(18)
    void testToString() {
        assertEquals("Client: Cart 1 - Products: 14", shoppingCart.toString());
    }

    @Test
    @Order(19)
    void testRemove() {
        shoppingCart.remove();
        assertEquals(0, shoppingCart.getCart().size());
        assertEquals(0, shoppingCart.getTotal());
    }

    @Test
    @Order(20)
    void testTotal() {
        shoppingCart.add(book1);
        shoppingCart.add(book1);
        shoppingCart.add(book1);
        shoppingCart.add(book1);

        assertEquals(18, book1.getPrice());
        assertEquals(72, shoppingCart.getTotal());

        shoppingCart.remove(book1);
        shoppingCart.remove(book1);
        assertEquals(36, shoppingCart.getTotal());

        shoppingCart.add(ebook2);
        shoppingCart.remove(magazine1);
        assertEquals(48.12, shoppingCart.getTotal());

        shoppingCart.remove();
        assertEquals(0, shoppingCart.getTotal());
    }
}
