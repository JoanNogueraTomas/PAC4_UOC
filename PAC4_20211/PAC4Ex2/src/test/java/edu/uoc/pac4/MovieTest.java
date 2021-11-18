package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest {

    Movie movie;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);

            movie = new Movie("E.T", 1982, "E.T. the Extra-Terrestrial", 10.00, 5);

            assertEquals("E.T", movie.getName());
            assertEquals(1982, movie.getPublicationYear());
            assertEquals("E.T. the Extra-Terrestrial", movie.getDescription());
            assertEquals(10.00, movie.getPrice());
            assertEquals(5, movie.getStock());

        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testVAT() {
        assertEquals(VAT.STANDARD, movie.getVAT());
    }

    @Test
    @Order(2)
    void testPlay1() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(movie.isPlaying());
        movie.play();
        assertEquals("Movie playing", outputStreamCaptor.toString().trim());
        assertTrue(movie.isPlaying());
    }

    @Test
    @Order(3)
    void testPlay2() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertTrue(movie.isPlaying());
        movie.play();
        assertEquals("Movie stopped", outputStreamCaptor.toString().trim());
        assertFalse(movie.isPlaying());
    }

    @Test
    @Order(4)
    void testPlay3() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(movie.isPlaying());
        movie.play();
        assertEquals("Movie playing", outputStreamCaptor.toString().trim());
        assertTrue(movie.isPlaying());
    }

    @Test
    @Order(4)
    void testStop() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertTrue(movie.isPlaying());
        movie.stop();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertTrue(movie.isPlaying());
    }

    @Test
    @Order(5)
    void testPause() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertTrue(movie.isPlaying());
        movie.pause();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertTrue(movie.isPlaying());
    }

    @Test
    @Order(6)
    void testToString() {
        assertEquals("MOVIE: PROD.15000; 39 years; 10.0€; VAT 21.0%, Stock: 5", movie.toString());
    }
}
