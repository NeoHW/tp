package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HobbyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Hobby(null));
    }

    @Test
    public void constructor_invalidHobby_throwsIllegalArgumentException() {
        String invalidHobby = "";
        assertThrows(IllegalArgumentException.class, () -> new Hobby(invalidHobby));
    }

    @Test
    public void isValidHobby() {
        // null hobby
        assertThrows(NullPointerException.class, () -> Hobby.isValidHobby(null));

        // invalid hobby
        assertFalse(Hobby.isValidHobby("")); // empty string
        assertFalse(Hobby.isValidHobby(" ")); // spaces only

        // valid hobby
        assertTrue(Hobby.isValidHobby("rock climbing")); // with spaces
        assertTrue(Hobby.isValidHobby("hiking")); // without spaces
    }

    @Test
    public void isValidHobby_caseInsensitive() {
        // Test with uppercase hobby
        assertTrue(Hobby.isValidHobby("Reading"));

        // Test with lowercase hobby
        assertTrue(Hobby.isValidHobby("reading"));
    }

    @Test
    public void compareTo() {
        // Test when hobbies are equal
        Hobby hobby1 = new Hobby("Reading");
        Hobby hobby2 = new Hobby("Reading");
        assertEquals(0, hobby1.compareTo(hobby2));

        Hobby hobby3 = new Hobby("Cooking");
        assertTrue(hobby1.compareTo(hobby3) > 0);

        Hobby hobby4 = new Hobby("Traveling");
        assertTrue(hobby4.compareTo(hobby1) > 0);
    }

    @Test
    public void equals() {
        Hobby hobby = new Hobby("hiking");

        // same values -> returns true
        assertTrue(hobby.equals(new Hobby("hiking")));

        // same object -> returns true
        assertTrue(hobby.equals(hobby));

        // null -> returns false
        assertFalse(hobby.equals(null));

        // different types -> returns false
        assertFalse(hobby.equals(5.0f));

        // different values -> returns false
        assertFalse(hobby.equals(new Hobby("swimming")));
    }
}
