package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FamilyConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FamilyCondition(null));
    }

    @Test
    public void constructor_invalidFamilyCondition_throwsIllegalArgumentException() {
        String invalidFamilyCondition = "";
        assertThrows(IllegalArgumentException.class, () -> new FamilyCondition(invalidFamilyCondition));
    }

    @Test
    public void isValidFamilyCondition() {
        // null family condition
        assertThrows(NullPointerException.class, () -> FamilyCondition.isValidFamilyCondition(null));

        // invalid family condition
        assertFalse(FamilyCondition.isValidFamilyCondition("")); // empty string
        assertFalse(FamilyCondition.isValidFamilyCondition(" ")); // spaces only

        // valid family condition
        assertTrue(FamilyCondition.isValidFamilyCondition("Wife not around")); // with spaces
        assertTrue(FamilyCondition.isValidFamilyCondition("Overseas")); // without spaces
        assertTrue(FamilyCondition.isValidFamilyCondition("Has 2 sons")); // allow numeric and alphabets
    }

    @Test
    public void compareTo() {
        // Test when familyCondition are equal
        FamilyCondition familyCondition1 = new FamilyCondition("No children");
        FamilyCondition familyCondition2 = new FamilyCondition("No children");
        assertEquals(0, familyCondition1.compareTo(familyCondition2));

        FamilyCondition familyCondition3 = new FamilyCondition("Wife in ward");
        assertTrue(familyCondition1.compareTo(familyCondition3) < 0);

        FamilyCondition familyCondition4 = new FamilyCondition("Son not in Singapore");
        assertTrue(familyCondition4.compareTo(familyCondition1) > 0);
    }

    @Test
    public void equals() {
        FamilyCondition familyCondition = new FamilyCondition("good relationship");

        // same values -> returns true
        assertTrue(familyCondition.equals(new FamilyCondition("good relationship")));

        // same object -> returns true
        assertTrue(familyCondition.equals(familyCondition));

        // null -> returns false
        assertFalse(familyCondition.equals(null));

        // different types -> returns false
        assertFalse(familyCondition.equals(5.0f));

        // different values -> returns false
        assertFalse(familyCondition.equals(new FoodPreference("son just met accident")));
    }
}
