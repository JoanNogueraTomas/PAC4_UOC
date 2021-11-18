package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MagazineTest {

    Magazine magazine1;
    Magazine magazine2;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);

            magazine1 = new Magazine();
            magazine2 = new Magazine("Pokemon (issue 59)", 2012, "Pokemon is an exciting magazine", 7.89, 30);

            assertEquals("Lorem Ipsum", magazine1.getName());
            assertEquals(2011, magazine1.getPublicationYear());
            assertEquals("lorem ipsum description", magazine1.getDescription());
            assertEquals(7.50, magazine1.getPrice());
            assertEquals(0, magazine1.getStock());

            assertEquals("Pokemon (issue 59)", magazine2.getName());
            assertEquals(2012, magazine2.getPublicationYear());
            assertEquals("Pokemon is an exciting magazine", magazine2.getDescription());
            assertEquals(7.89, magazine2.getPrice());
            assertEquals(30, magazine2.getStock());

        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testVAT() {
        assertEquals(VAT.SUPER_REDUCED, magazine1.getVAT());
        assertEquals(VAT.SUPER_REDUCED, magazine1.getVAT());
    }

    @Test
    @Order(2)
    void testActivePromotion() {

        assertNull(magazine1.getPromotion());
        assertNull(magazine2.getPromotion());

        Promotion promotion1 = new Promotion(15, Campaign.CHRISTMAS, 5);
        Promotion promotion2 = new Promotion(10, Campaign.BLACK_FRIDAY, 2);
        Promotion promotion3 = new Promotion(49, Campaign.BOOK_DAY, 7);


        try{

            magazine1.activePromotion(promotion1);
            assertEquals(promotion1, magazine1.getPromotion());
            assertEquals(18, magazine1.getPromotion().getDiscount());

            Exception ex = assertThrows(Exception.class, () -> magazine1.activePromotion(promotion2));
            assertEquals(ProductException.MSG_ERR_PROMOTION_NOT_ALLOWED, ex.getMessage());

            ex = assertThrows(Exception.class, () -> magazine1.activePromotion(promotion3));
            assertEquals("[ERROR] Promotion's parameter is incorrect!!", ex.getMessage());

            magazine1.setPromotion(null);
            assertNull(magazine1.getPromotion());

        }catch(ProductException e) {
            fail("testActivePromotion failed");
        }
    }


    @Test
    @Order(3)
    void testToString() {
        assertEquals("MAGAZINE: PROD.15000; 10 years; 7.5€; VAT 4.0%, Stock: 0", magazine1.toString());
        assertEquals("MAGAZINE: PROD.15001; 9 years; 7.89€; VAT 4.0%, Stock: 30", magazine2.toString());
    }
}
