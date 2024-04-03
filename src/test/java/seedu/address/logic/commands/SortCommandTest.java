package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.patient.comparators.NameComparator.NAME_COMPARATOR;
import static seedu.address.model.patient.comparators.PreferredNameComparator.PREFERRED_NAME_COMPARATOR;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortCommandWithSortAttributeName_success() {

        String sortAttribute = "name";
        SortCommand sortCommand = new SortCommand(NAME_COMPARATOR, sortAttribute);

        List<Patient> patientList = expectedModel.getFullPatientList();
        List<Patient> patientArrayList = new ArrayList<>(patientList);
        Collections.sort(patientArrayList, NAME_COMPARATOR);
        expectedModel.updatePatientList(patientArrayList);

        expectedModel.updatePatientList(patientArrayList);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "name");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortCommandWithSortAttributePreferredName_success() {

        String sortAttribute = "preferred name";
        SortCommand sortCommand = new SortCommand(PREFERRED_NAME_COMPARATOR, sortAttribute);

        List<Patient> patientList = expectedModel.getFullPatientList();
        List<Patient> patientArrayList = new ArrayList<>(patientList);
        Collections.sort(patientArrayList, PREFERRED_NAME_COMPARATOR);
        expectedModel.updatePatientList(patientArrayList);

        expectedModel.updatePatientList(patientArrayList);

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "preferred name");

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void initialise_sortCommandWithMismatchSortAttributeAndComparator_failure() throws AssertionError {
        try {
            SortCommand sortCommand = new SortCommand(NAME_COMPARATOR, "preferred name");
        } catch (AssertionError e) {
            assertEquals("Sort Attribute and Comparator mismatch!", e.getMessage());
        }

        try {
            SortCommand sortCommand = new SortCommand(PREFERRED_NAME_COMPARATOR, "name");
        } catch (AssertionError e) {
            assertEquals("Sort Attribute and Comparator mismatch!", e.getMessage());
        }
    }

    @Test
    public void equals() {
        String sortAttribute = "name";

        final SortCommand standardCommand = new SortCommand(NAME_COMPARATOR, sortAttribute);

        // same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(NAME_COMPARATOR, "name");
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ListCommand());

        // different comparator -> returns false
        assertNotEquals(standardCommand, new SortCommand(PREFERRED_NAME_COMPARATOR, sortAttribute));

        // different sort attribute -> returns false
        assertNotEquals(standardCommand, new SortCommand(NAME_COMPARATOR, "hello"));
    }

    @Test
    public void toStringTest() {
        // test case 1
        String sortAttribute = "name";
        SortCommand sortCommand = new SortCommand(NAME_COMPARATOR, "name");

        String expected = SortCommand.class.getCanonicalName() + "{comparator=" + NAME_COMPARATOR + ", sort attribute="
                + sortAttribute
                + "}";
        assertEquals(expected, sortCommand.toString());

        // test case 2
        sortAttribute = "preferred name";
        sortCommand = new SortCommand(PREFERRED_NAME_COMPARATOR, "preferred name");
        expected = SortCommand.class.getCanonicalName() + "{comparator=" + PREFERRED_NAME_COMPARATOR
                + ", sort attribute="
                + sortAttribute
                + "}";
        assertEquals(expected, sortCommand.toString());

    }

}
