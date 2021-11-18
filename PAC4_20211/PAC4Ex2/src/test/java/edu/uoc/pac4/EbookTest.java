package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EbookTest {

    Ebook ebook;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);
            ebook = new Ebook("Harry Potter 1", 1997, "Harry Potter and the Philosopher's Stone", 14.25, 3498);
            assertEquals("Harry Potter 1", ebook.getName());
            assertEquals(VAT.SUPER_REDUCED, ebook.getVAT());
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testVAT() {
        assertEquals(VAT.SUPER_REDUCED, ebook.getVAT());
    }

    @Test
    @Order(2)
    void testToString() {
        assertEquals("EBOOK: PROD.15000; 24 years; 14.25€; VAT 4.0%, Rating: 0, Votes: 0", ebook.toString());
    }

}
