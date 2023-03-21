import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    LineItem LI1;

    @BeforeEach
    void setUp() {
        LI1 = new LineItem("Ramen Noodles", 2.50, 10);
    }

    @Test
    void getProductName() {
        assertEquals("Ramen Noodles", LI1.getProductName());
    }

    @Test
    void getUnitPrice() {
        assertEquals(2.50, LI1.getUnitPrice());
    }

    @Test
    void testToString() {
        assertEquals("Ramen Noodles           10      2.50     25.00\n", LI1.toString());
    }

    @Test
    void getQuantity() {
        assertEquals(10, LI1.getQuantity());
    }

    @Test
    void getCalculatedTotal() {
        assertEquals(25.00, LI1.getCalculatedTotal());
    }
}