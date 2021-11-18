package edu.uoc.pac4;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PromotionTest {

    Promotion promotion;

    @BeforeAll
    void testPromotion() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Promotion(51, Campaign.SUMMER, 12));
        assertEquals("[ERROR] Promotion's parameter is incorrect!!",ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> new Promotion(-1, Campaign.SUMMER, 12));
        assertEquals("[ERROR] Promotion's parameter is incorrect!!",ex.getMessage());

        promotion = new Promotion(5,  Campaign.BOOK_DAY, 2);

    }

    @Test
    @Order(1)
    void testGetCode() {
        String regex = "PROMO-[A-Z0-9]{10}";
        assertTrue(promotion.getCode().matches(regex));
    }

    @Test
    @Order(2)
    void testGetDiscount() {
        assertEquals(5, promotion.getDiscount());
    }

    @Test
    @Order(3)
    void testSetDiscount() {
        promotion.setDiscount(20);
        assertEquals(20, promotion.getDiscount());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Promotion(51, Campaign.SUMMER, 12));
        assertEquals("[ERROR] Promotion's parameter is incorrect!!",ex.getMessage());
        assertEquals(20, promotion.getDiscount());
    }

    @Test
    @Order(4)
    void testGetDate() {
        assertEquals(LocalDate.now().plusDays(2), promotion.getDate());
    }

    @Test
    @Order(5)
    void testGetCampaign() {
        assertEquals(Campaign.BOOK_DAY, promotion.getCampaign());
        promotion = new Promotion(10,  Campaign.BLACK_FRIDAY, 15);
        assertEquals(Campaign.BLACK_FRIDAY, promotion.getCampaign());

    }

    @Test
    @Order(6)
    void testToString() {
        String regex = "PROMO-[A-Z0-9]{10}";
        assertTrue(promotion.toString().substring(0, 16).matches(regex));
        assertEquals(" - 10.0 - BLACK_FRIDAY", promotion.toString().substring(16));
    }

}
