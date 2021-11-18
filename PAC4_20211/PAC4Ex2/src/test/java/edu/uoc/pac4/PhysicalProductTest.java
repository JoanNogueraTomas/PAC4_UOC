package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhysicalProductTest {

    Book book;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);
            book = new Book("Harry Potter 2", 1998, "Harry Potter and the Chamber of Secrets", 18.00, 50, 251);
            assertEquals("Harry Potter 2", book.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void TestStock(){
        assertEquals(50, book.getStock());
        book.setStock(55);
        assertEquals(55, book.getStock());
        book.incStock();
        book.incStock();
        assertEquals(57, book.getStock());
        book.decStock();
        assertEquals(56, book.getStock());

    }

    @Test
    @Order(3)
    void testActivePromotion() {

        assertNull(book.getPromotion());

        Promotion promotion1 = new Promotion(20, Campaign.SUMMER, 7);
        Promotion promotion2 = new Promotion(15, Campaign.CHRISTMAS, 5);

        try{

            Exception ex = assertThrows(Exception.class, () ->	book.activePromotion(promotion1));
            assertEquals(ProductException.MSG_ERR_PROMOTION_NOT_ALLOWED, ex.getMessage());

            book.activePromotion(promotion2);
            assertEquals(promotion2, book.getPromotion());

            book.setPromotion(null);
            assertNull(book.getPromotion());

        }catch(ProductException e) {
            fail("testActivePromotion failed");
        }
    }

    @Test
    @Order(4)
    void testInfoStockAndSales() {
        assertEquals("Book -> Stock: 56, Sales: 0", book.infoStockAndSales());
        book.incSales();
        book.incSales();
        book.decStock();
        book.decStock();
        assertEquals("Book -> Stock: 54, Sales: 2", book.infoStockAndSales());

        assertEquals("Book -> Stock: 54, Sales: 2", book.infoStockAndSales(-1));
        assertEquals("Book -> Stock: 54, Sales: 2", book.infoStockAndSales(0));
        assertEquals("Book -> Stock: 54, Sales: 2", book.infoStockAndSales(1));
        assertEquals("Book -> Stock: 54, Sales: 2//Book -> Stock: 54, Sales: 2", book.infoStockAndSales(2));
        assertEquals("Book -> Stock: 54, Sales: 2//Book -> Stock: 54, Sales: 2//Book -> Stock: 54, Sales: 2", book.infoStockAndSales(3));
    }

    @Test
    @Order(5)
    void testToString() {
        assertEquals("BOOK: PROD.15000; 23 years; 18.0€; VAT 10.0%, Stock: 54", book.toString());
    }

}
