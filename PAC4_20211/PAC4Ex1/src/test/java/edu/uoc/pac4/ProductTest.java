package edu.uoc.pac4;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ProductTest {

    Product product1;
    Product product2;

    @BeforeAll
    void init() {
        try {
            product1 = new Product();
            product2 = new Product("Book", 2018, "book product test", 12.40);
        } catch (Exception e) {
            e.printStackTrace();
            fail("init");
        }
    }

    @Test
    @Order(1)
    void testReference() {
        assertEquals("PROD.15000", product1.getReference());
        try{
            Exception ex = assertThrows(Exception.class, () -> new Product("Lorem ipsum dolor sit amet, consectetur vestibulum.", 2000, "book product test", 12.40));
            assertEquals(ProductException.MSG_ERR_NAME, ex.getMessage());

            assertEquals("PROD.15001", product2.getReference());
        }catch (Exception e) {
            e.printStackTrace();
            fail("testSetId failed");
        }
    }

    @Test
    @Order(2)
    void testGetName() {
        assertEquals("Lorem Ipsum", product1.getName());
        assertEquals("Book", product2.getName());
    }

    @Test
    @Order(3)
    void testSetName() {
        try{
            product1.setName("Foo Bar");
            assertEquals("Foo Bar", product1.getName());
        }catch(Exception e) {
            fail("setName failed");
        }

        Exception ex = assertThrows(Exception.class, () ->	product1.setName("a".repeat(51)));
        assertEquals(ProductException.MSG_ERR_NAME, ex.getMessage());

        assertEquals("Foo Bar", product1.getName());
    }


    @Test
    @Order(4)
    void testGetPublicationYear() {
        assertEquals(2011, product1.getPublicationYear());
        assertEquals(2018, product2.getPublicationYear());
    }

    @Test
    @Order(5)
    void testSetPublicationYear() {
        try{
            product1.setPublicationYear(2021);
            assertEquals(2021, product1.getPublicationYear());

            product1.setPublicationYear(1995);
            assertEquals(1995, product1.getPublicationYear());
        }catch(Exception e) {
            fail("setSalary failed");
        }

        Exception ex = assertThrows(Exception.class, () ->	product1.setPublicationYear(2022));
        assertEquals(ProductException.MSG_ERR_PUBLICATION_YEAR, ex.getMessage());

        assertEquals(1995, product1.getPublicationYear());
    }

    @Test
    @Order(6)
    void testGetDescription() {
        assertEquals("lorem ipsum description", product1.getDescription());
        assertEquals("book product test", product2.getDescription());
    }

    @Test
    @Order(7)
    void testSetDescription() {
        try{
            product1.setDescription("description change");
            assertEquals("description change", product1.getDescription());
        }catch(Exception e) {
            fail("setEmail failed");
        }

        Exception ex = assertThrows(Exception.class, () ->	product1.setDescription("a".repeat(251)));
        assertEquals(ProductException.MSG_ERR_DESCRIPTION, ex.getMessage());

        assertEquals("description change", product1.getDescription());
    }

    @Test
    @Order(8)
    void testGetPrice() {
        assertEquals(7.50, product1.getPrice());
        assertEquals(12.40, product2.getPrice());
    }

    @Test
    @Order(9)
    void testSetPrice() {
        try{
            product1.setPrice(10.00);
            assertEquals(10.00, product1.getPrice());
        }catch(ProductException e) {
            fail("set failed");
        }

        Exception ex = assertThrows(Exception.class, () ->	product1.setPrice(-3));
        assertEquals(ProductException.MSG_ERR_NEGATIVE, ex.getMessage());
        assertEquals(10.00, product1.getPrice());
    }


    @Test
    @Order(10)
    void testGetVAT() {
        assertEquals(VAT.STANDARD, product1.getVAT());
        assertEquals(VAT.STANDARD, product2.getVAT());
    }

    @Test
    @Order(11)
    void testSetVAT() {
        product1.setVAT(VAT.SUPER_REDUCED);
        assertEquals(VAT.SUPER_REDUCED, product1.getVAT());
        assertEquals(4, product1.getVAT().getPercentage());
        product1.setVAT(VAT.STANDARD);
        assertEquals(VAT.STANDARD, product1.getVAT());
        assertEquals(21, product1.getVAT().getPercentage());
        product1.setVAT(VAT.REDUCED);
        assertEquals(VAT.REDUCED, product1.getVAT());
        assertEquals(10, product1.getVAT().getPercentage());
    }

    @Test
    @Order(12)
    void testGetPriceVAT() {
        assertEquals(11, product1.getPriceVAT());

        try {
            product1.setPrice(10);
        } catch (ProductException e) {
            fail("get failed");
        }
        assertEquals(11, product1.getPriceVAT());
        product1.setVAT(VAT.SUPER_REDUCED);
        assertEquals(10.4, product1.getPriceVAT());
        product1.setVAT(VAT.STANDARD);
        assertEquals(12.1, product1.getPriceVAT());
    }

    @Test
    @Order(13)
    void testToString() {
        product1.setVAT(VAT.SUPER_REDUCED);
        assertEquals("PROD.15000; 26 years; 10.0€; VAT 4.0%", product1.toString());
        assertEquals("PROD.15001; 3 years; 12.4€; VAT 21.0%", product2.toString());
    }

    @Test
    @Order(14)
    void testEquals() {
        product2 = product1;
        Product  product3;
        assertEquals(product1, product1);
        assertEquals(product2, product2);
        assertEquals(product1, product2);
        assertEquals(product2, product1);

        try {
            product1 = new Product();
            product3 = new Product();
            assertEquals(product3, product3);
            assertEquals(product1, product3);
            product3.setVAT(VAT.SUPER_REDUCED);
            assertNotEquals(product1, product3);
            assertNotEquals(product2, product3);
        } catch (ProductException e) {
            e.printStackTrace();
            fail("testEquals failed");
        }
    }
}