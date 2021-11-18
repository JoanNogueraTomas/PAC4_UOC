package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AudiobookTest {

    Audiobook audiobook;


    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);
            audiobook = new Audiobook("The Lord of the Rings", 2003, "The Fellowship of the Ring", 25.00, 12500, 400);
            assertEquals("The Lord of the Rings", audiobook.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testVAT() {
        assertEquals(VAT.STANDARD, audiobook.getVAT());
    }

    @Test
    @Order(2)
    void testDuration() {
        assertEquals(400, audiobook.getDuration());
        audiobook.setDuration(444);
        assertEquals(444, audiobook.getDuration());
    }

    @Test
    @Order(3)
    void testPlay1() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(audiobook.isPlaying());
        audiobook.play();
        assertEquals("AudioBook playing", outputStreamCaptor.toString().trim());
        assertTrue(audiobook.isPlaying());
    }

    @Test
    @Order(4)
    void testPlay2() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertTrue(audiobook.isPlaying());
        audiobook.play();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertTrue(audiobook.isPlaying());
    }

    @Test
    @Order(5)
    void testStop1() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertTrue(audiobook.isPlaying());
        audiobook.stop();
        assertEquals("AudioBook stopped", outputStreamCaptor.toString().trim());
        assertFalse(audiobook.isPlaying());
    }

    @Test
    @Order(6)
    void testStop2() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(audiobook.isPlaying());
        audiobook.stop();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertFalse(audiobook.isPlaying());
    }

    @Test
    @Order(7)
    void testPause1() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(audiobook.isPlaying());
        audiobook.pause();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertFalse(audiobook.isPlaying());
    }

    @Test
    @Order(8)
    void testPause2() {
        assertFalse(audiobook.isPlaying());
        audiobook.play();
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        audiobook.pause();
        assertEquals("AudioBook paused: ", outputStreamCaptor.toString().trim().substring(0, 18));
        assertTrue(outputStreamCaptor.toString().trim().substring(18).matches("[0-9]*"));
        assertTrue(Integer.parseInt(outputStreamCaptor.toString().trim().substring(18)) <= audiobook.getDuration() ? true : false);
        assertFalse(audiobook.isPlaying());
    }

    @Test
    @Order(9)
    void testPause3() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        assertFalse(audiobook.isPlaying());
        audiobook.pause();
        assertEquals("", outputStreamCaptor.toString().trim());
        assertFalse(audiobook.isPlaying());
    }

    @Test
    @Order(10)
    void testToString() {
        assertEquals("AUDIOBOOK: PROD.15000; 18 years; 25.0€; VAT 21.0%, Rating: 0, Votes: 0", audiobook.toString());
    }
}
