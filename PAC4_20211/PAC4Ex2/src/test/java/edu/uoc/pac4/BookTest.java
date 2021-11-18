package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTest {

    Book book1;
    Book book2;
    Book book3;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);

            book1 = new Book();
            book2 = new Book("Harry Potter 2", 1998, "Harry Potter and the Chamber of Secrets", 18.00, 50, 251);
            book3 = new Book("Harry Potter 3", 1999, "Harry Potter and the Prisoner of Azkaban", 15.90, 40, 317, "9780545162074");

            assertEquals("Lorem Ipsum", book1.getName());
            assertEquals(2011, book1.getPublicationYear());
            assertEquals("lorem ipsum description", book1.getDescription());
            assertEquals(7.50, book1.getPrice());
            assertEquals(0, book1.getStock());
            assertEquals(1, book1.getPages());
            assertEquals("9780000000000", book1.getIsbn());

            assertEquals("Harry Potter 2", book2.getName());
            assertEquals(1998, book2.getPublicationYear());
            assertEquals("Harry Potter and the Chamber of Secrets", book2.getDescription());
            assertEquals(18.00, book2.getPrice());
            assertEquals(50, book2.getStock());
            assertEquals(251, book2.getPages());
            assertEquals("9780000000000", book2.getIsbn());

            assertEquals("Harry Potter 3", book3.getName());
            assertEquals(1999, book3.getPublicationYear());
            assertEquals("Harry Potter and the Prisoner of Azkaban", book3.getDescription());
            assertEquals(15.90, book3.getPrice());
            assertEquals(40, book3.getStock());
            assertEquals(317, book3.getPages());
            assertEquals("9780545162074", book3.getIsbn());
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testVAT() {
        assertEquals(VAT.REDUCED, book1.getVAT());
        assertEquals(VAT.REDUCED, book2.getVAT());
        assertEquals(VAT.REDUCED, book3.getVAT());
        book3.setVAT(VAT.SUPER_REDUCED);
        assertEquals(VAT.SUPER_REDUCED, book3.getVAT());
    }

    @Test
    @Order(2)
    void testPages() {
        assertEquals(1, book1.getPages());
        book2.setPages(222);
        assertEquals(222, book2.getPages());
        assertEquals(317, book3.getPages());
    }

    @Test
    @Order(3)
    void testIsbn() {
        assertEquals("9780000000000", book1.getIsbn());
        assertEquals("9780000000000", book2.getIsbn());
        assertEquals("9780545162074", book3.getIsbn());
        try {
            book2.setIsbn("9781111111111");
            assertEquals("9781111111111", book2.getIsbn());
            Exception ex = assertThrows(Exception.class, () ->	book1.setIsbn("9874565456545"));
            assertEquals(ProductException.MSG_ERR_PARAMETER_INCORRECT, ex.getMessage());
            ex = assertThrows(Exception.class, () -> book2.setIsbn("97845654565459"));
            assertEquals(ProductException.MSG_ERR_PARAMETER_INCORRECT, ex.getMessage());
            ex = assertThrows(Exception.class, () -> book2.setIsbn("978456545654"));
            assertEquals(ProductException.MSG_ERR_PARAMETER_INCORRECT, ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail("isbn failed");
        }

    }

    @Test
    @Order(4)
    void testToString() {
        assertEquals("BOOK: PROD.15000; 10 years; 7.5€; VAT 10.0%, Stock: 0", book1.toString());
        assertEquals("BOOK: PROD.15001; 23 years; 18.0€; VAT 10.0%, Stock: 50", book2.toString());
        assertEquals("BOOK: PROD.15002; 22 years; 15.9€; VAT 4.0%, Stock: 40", book3.toString());
    }
}
