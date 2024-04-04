package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FoodPreferenceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FoodPreference(null));
    }

    @Test
    public void constructor_invalidFoodPreference_throwsIllegalArgumentException() {
        String invalidFoodPreference = "";
        assertThrows(IllegalArgumentException.class, () -> new FoodPreference(invalidFoodPreference));
    }

    @Test
    public void isValidFoodPreference() {
        // null food preference
        assertThrows(NullPointerException.class, () -> FoodPreference.isValidFoodPreference(null));

        // invalid food preference
        assertFalse(FoodPreference.isValidFoodPreference("")); // empty string
        assertFalse(FoodPreference.isValidFoodPreference(" ")); // spaces onl

        // valid food preference
        assertTrue(FoodPreference.isValidFoodPreference("chicken rice")); // with spaces
        assertTrue(FoodPreference.isValidFoodPreference("pasta")); // without spaces
    }

    @Test
    public void compareTo() {
        // Test when foodPreference are equal
        FoodPreference foodPreference1 = new FoodPreference("Nasi Lemak");
        FoodPreference foodPreference2 = new FoodPreference("Nasi Lemak");
        assertEquals(0, foodPreference1.compareTo(foodPreference2));

        // Test when foodPreference1 is less than foodPreference2
        FoodPreference foodPreference3 = new FoodPreference("Fried Rice");
        assertTrue(foodPreference1.compareTo(foodPreference3) > 0);

        // Test when foodPreference1 is greater than foodPreference2
        FoodPreference foodPreference4 = new FoodPreference("Dim sum");
        assertTrue(foodPreference4.compareTo(foodPreference1) < 0);
    }

    @Test
    public void equals() {
        FoodPreference foodPreference = new FoodPreference("pasta");

        // same values -> returns true
        assertTrue(foodPreference.equals(new FoodPreference("pasta")));

        // same object -> returns true
        assertTrue(foodPreference.equals(foodPreference));

        // null -> returns false
        assertFalse(foodPreference.equals(null));

        // different types -> returns false
        assertFalse(foodPreference.equals(5.0f));

        // different values -> returns false
        assertFalse(foodPreference.equals(new FoodPreference("spaghetti")));
    }
}
