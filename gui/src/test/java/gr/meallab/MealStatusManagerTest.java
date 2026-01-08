package gr.meallab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MealStatusManagerTest {

    @BeforeEach
    void setUp() {
        // Καθαρίζουμε τις λίστες πριν από κάθε τεστ
        MealStatusManager.getFavorites().clear();
        MealStatusManager.getCooked().clear();
    }

    @Test
    void testAddFavorite() {
        String id = "55555";
        MealStatusManager.addFavorite(id);
        assertTrue(MealStatusManager.isFavorite(id), "To ID πρέπει να είναι στα favorites");
    }

    @Test
    void testRemoveFavorite() {
        String id = "55555";
        MealStatusManager.addFavorite(id);
        MealStatusManager.removeFavorite(id);
        assertFalse(MealStatusManager.isFavorite(id), "To ID δεν πρέπει να είναι στα favorites");
    }
}