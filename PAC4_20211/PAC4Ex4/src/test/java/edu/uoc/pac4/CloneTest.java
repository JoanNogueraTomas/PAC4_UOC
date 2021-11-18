package edu.uoc.pac4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)

class CloneTest {

    @Test
    void testClone() {
        Library c1 = new Library("Library UOC S.L.","12345678-B", 41.98123, 2.1344, "libraryuoc@uoc.edu", "9312345678");
        try {
            Library c2 = (Library) c1.clone();
            assertNotEquals(c1, c2);
            assertEquals(c1.getName(),c2.getName());
            assertEquals(c1.getNif(),c2.getNif());
            assertNotEquals(c1.getAddress(),c2.getAddress());
            assertEquals(c1.getAddress().getLatitude(),c2.getAddress().getLatitude());
            assertEquals(c1.getAddress().getLongitude(),c2.getAddress().getLongitude());
            assertNotEquals(c1.getContactInformation(),c2.getContactInformation());
            assertEquals(c1.getContactInformation().getMail(),c2.getContactInformation().getMail());
            assertEquals(c1.getContactInformation().getTelephone(),c2.getContactInformation().getTelephone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            fail("test failed");
        }
    }
}