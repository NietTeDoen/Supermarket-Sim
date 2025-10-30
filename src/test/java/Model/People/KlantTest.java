package Model.People;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KlantTest {

    //happy flow test
    @Test
    void testTakeProduct_HappyFlow() {
        Klant klant = new Klant(new int[]{0, 0}, List.of(), "sprite.png");
        klant.takeProduct("Appel", 10);

        assertEquals(1, klant.getProductsList().size());
        assertEquals("Appel", klant.getProductsList().get(0).getNaam());
    }

    //edge case test
    @Test
    void testTakeProduct_NullName() {
        Klant klant = new Klant(new int[]{0,0}, List.of(), "sprite.png");
        assertThrows(NullPointerException.class, () -> klant.takeProduct(null, 10));
    }
}
