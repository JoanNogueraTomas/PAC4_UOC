package edu.uoc.pac4;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductTest {

    Product product1;
    Product product2;

    @BeforeAll
    void init() {
        try {
            Field field = Product.class.getDeclaredField("referenceId");
            field.setAccessible(true);
            field.set(null, 15000);
            product1 = new Book();
            product2 = new Book("Book", 2018, "book product test", 12.40, 5, 365);
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
            Exception ex = assertThrows(Exception.class, () -> new Book("Lorem ipsum dolor sit amet, consectetur vestibulum.", 2000, "book product test", 12.40, 5, 365));
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
        assertEquals(VAT.REDUCED, product1.getVAT());
        assertEquals(VAT.REDUCED, product2.getVAT());
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
        product1.setVAT(VAT.REDUCED);
    }

    @Test
    @Order(13)
    void testToString() {
        assertEquals("BOOK: PROD.15000; 26 years; 10.0€; VAT 10.0%, Stock: 0", product1.toString());
        assertEquals("BOOK: PROD.15001; 3 years; 12.4€; VAT 10.0%, Stock: 5", product2.toString());
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
            product1 = new Book();
            product3 = new Book();
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

    @Test
    void testGetSales() {
        assertEquals(0, product1.getSales());
        assertEquals(0, product2.getSales());
    }

    @Test
    void tesIncDecSales() {
        product1.incSales();
        product1.incSales();
        product1.incSales();
        assertEquals(3, product1.getSales());
        product1.decSales();
        product1.decSales();
        assertEquals(1, product1.getSales());
        product1.incSales();
        assertEquals(2, product1.getSales());
    }


    @Test
    void testCompareTo() {

        try {
            Product [] products = new Product[8];

            //Digital Products
            Ebook ebook1 = new Ebook("Harry Potter 1", 1997, "Harry Potter and the Philosopher's Stone", 14.25, 3498);
            Ebook ebook2 = new Ebook("The Truth About the Harry Quebert Affair", 2012, "Bestseller of Joel Dicker", 12.12, 222);
            Audiobook audiobook1 = new Audiobook("The Lord of the Rings", 2003, "The Fellowship of the Ring", 25.00, 12500, 400);
            Audiobook audiobook2 = new Audiobook("Dune", 2020, "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides", 25.00, 10000, 360);
            //Physical Products
            Book book1 = new Book("Harry Potter 2", 1998, "Harry Potter and the Chamber of Secrets", 18.00, 50, 251);
            Book book2 = new Book("Harry Potter 3", 1999, "Harry Potter and the Prisoner of Azkaban", 15.90, 40, 317, "9780545162074");
            Magazine magazine1 = new Magazine("Pokemon (issue 59)", 2012, "Pokemon is an exciting magazine", 7.89, 30);
            Movie movie1 = new Movie("E.T", 1982, "E.T. the Extra-Terrestrial", 10.00, 5);

            products[0] = ebook1;
            products[1] = ebook2;
            products[2] = audiobook1;
            products[3] = audiobook2;
            products[4] = movie1;
            products[5] = book1;
            products[6] = book2;
            products[7] = magazine1;

            /*
              NOTAS compareTo: int compareTo(Product product)
              devuelve < 0, entonces el product que llama al metodo va primero.
              devuelve == 0 entonces son iguales.
              devuelve > 0, entonces el parametro pasado al metodo compareTo va primero.
             */

            Exception ex = assertThrows(NullPointerException.class, () -> ebook1.compareTo(null));
            assertEquals(NullPointerException.class, ex.getClass());
            assertTrue(book1.compareTo(movie1) > 0); //positivo porque 1998 (book1) va despues que 1982 (movie1)
            assertTrue(ebook1.compareTo(audiobook2) < 0); //negativo porque 1997 (ebook1) va antes que 2020 (audiobook2)
            assertTrue(ebook2.compareTo(magazine1) < 0); // fechas iguales 2012=2012, pero negativo porque reference de ebook2 va antes que reference de magazine1
            assertEquals(0, book1.compareTo(book1));

            Arrays.sort(products);
            assertArrayEquals(new Product[]{movie1, ebook1, book1, book2, audiobook1, ebook2, magazine1, audiobook2}, products);

            Arrays.sort(products);

        } catch (ProductException e) {
            e.printStackTrace();
            fail("testCompareTo failed");
        }
    }

    @Test
    void testArrayComparator() {

        try {
            Product [] products = new Product[8];

            //Digital Products
            Ebook ebook1 = new Ebook("Harry Potter 1", 1997, "Harry Potter and the Philosopher's Stone", 14.25, 3498);
            Ebook ebook2 = new Ebook("The Truth About the Harry Quebert Affair", 2012, "Bestseller of Joel Dicker", 12.12, 222);
            Audiobook audiobook1 = new Audiobook("The Lord of the Rings", 2003, "The Fellowship of the Ring", 25.00, 12500, 400);
            Audiobook audiobook2 = new Audiobook("Dune", 2020, "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides", 25.00, 10000, 360);
            //Physical Products
            Book book1 = new Book("Harry Potter 2", 1998, "Harry Potter and the Chamber of Secrets", 18.00, 50, 251);
            Book book2 = new Book("Harry Potter 3", 1999, "Harry Potter and the Prisoner of Azkaban", 15.90, 40, 317, "9780545162074");
            Magazine magazine1 = new Magazine("Pokemon (issue 59)", 2012, "Pokemon is an exciting magazine", 7.89, 30);
            Movie movie1 = new Movie("E.T", 1982, "E.T. the Extra-Terrestrial", 10.00, 5);

            products[0] = ebook1;
            products[1] = ebook2;
            products[2] = audiobook1;
            products[3] = audiobook2;
            products[4] = book1;
            products[5] = book2;
            products[6] = magazine1;
            products[7] = movie1;

            Exception ex = assertThrows(NullPointerException.class, () -> new ProductOrderByPrice().compare(ebook1, null));
            assertEquals(NullPointerException.class, ex.getClass());

            Arrays.sort(products, new ProductOrderByPrice());
            assertArrayEquals(new Product[]{magazine1, movie1, ebook2, ebook1, book2, book1, audiobook1, audiobook2}, products);

        } catch (ProductException e) {
            e.printStackTrace();
            fail("testCompareTo failed");
        }
    }
}