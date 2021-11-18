package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DigitalProductTest {

    Ebook ebook;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);
            ebook = new Ebook("Harry Potter 1", 1997, "Harry Potter and the Philosopher's Stone", 14.25, 3498);
            assertEquals("Harry Potter 1", ebook.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void TestFilesize(){
        assertEquals(3498, ebook.getFilesize());
        ebook.setFilesize(5000);
        assertEquals(5000, ebook.getFilesize());
    }

    @Test
    @Order(2)
    void TestSalesTotal(){
        assertEquals(0, DigitalProduct.getSalesTotal());
        DigitalProduct.incSalesTotal();
        ebook.incSalesTotal();
        assertEquals(2, DigitalProduct.getSalesTotal());
        DigitalProduct.decSalesTotal();
        assertEquals(1, DigitalProduct.getSalesTotal());
    }

    @Test
    @Order(3)
    void testActivePromotion() {

        assertNull(ebook.getPromotion());

        Promotion promotion1 = new Promotion(20, Campaign.SUMMER, 7);
        Promotion promotion2 = new Promotion(15, Campaign.CHRISTMAS, 5);

        try{
            ebook.activePromotion(promotion1);
            assertEquals(promotion1, ebook.getPromotion());

            ebook.activePromotion(promotion2);
            assertEquals(promotion2, ebook.getPromotion());

            ebook.setPromotion(null);
            assertNull(ebook.getPromotion());

        }catch(ProductException e) {
            fail("testActivePromotion failed");
        }
    }

    @Test
    @Order(4)
    void testInfoStockAndSales() {
        assertEquals("Ebook -> Sales: 0", ebook.infoStockAndSales());
        ebook.incSales();
        ebook.incSales();
        assertEquals("Ebook -> Sales: 2", ebook.infoStockAndSales());
    }

    @Test
    @Order(5)
    void testRate() {
        assertEquals(0, ebook.getRating());
        assertEquals(0, ebook.getVotes());

        ebook.rate(5);
        assertEquals(1, ebook.getVotes());
        assertEquals(5, ebook.starRatingCalculation());

        ebook.rate(5);
        assertEquals(2, ebook.getVotes());
        assertEquals(5, ebook.starRatingCalculation());

        ebook.rate(5);
        ebook.rate(5);
        ebook.rate(4);
        assertEquals(5, ebook.getVotes());
        assertEquals(4.8, ebook.starRatingCalculation(), 0.01);

        ebook.rate(3);
        ebook.rate(3);
        ebook.rate(2);
        ebook.rate(2);
        assertEquals(9, ebook.getVotes());
        assertEquals(3.77, ebook.starRatingCalculation(), 0.01);

        ebook.rate(2);
        ebook.rate(2);
        ebook.rate(1);
        ebook.rate(1);
        assertEquals(13, ebook.getVotes());
        assertEquals(3.07, ebook.starRatingCalculation(), 0.01);
    }

    @Test
    @Order(6)
    void testToString() {
        assertEquals("EBOOK: PROD.15000; 24 years; 14.25€; VAT 4.0%, Rating: 40, Votes: 13", ebook.toString());
    }

}
