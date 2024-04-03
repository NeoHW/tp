package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.patient.comparators.NameComparator.NAME_COMPARATOR;
import static seedu.address.model.patient.comparators.PreferredNameComparator.PREFERRED_NAME_COMPARATOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Sorts (based on specified comparator) and list all patients in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SORT_SUCCESS = "Successfully sorted all patients by %1s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all persons by the specified attribute "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [ATTRIBUTE]\n"
            + "Example: " + COMMAND_WORD + " n";

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final Comparator<Patient> comparator;

    /**
     * Creates a new SortCommand to sort the patients in patient list by the specified {@code comparator}
     *
     * @param comparator the comparator used to compare the patient list
     */
    public SortCommand(Comparator<Patient> comparator) {
        requireNonNull(comparator);

        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.log(Level.INFO, "Attempting to execute SortCommand");

        requireNonNull(model);

        String sortAttribute = "";

        if (this.comparator.equals(NAME_COMPARATOR)) {
            sortAttribute = "name";
        }

        if (this.comparator.equals(PREFERRED_NAME_COMPARATOR)) {
            sortAttribute = "preferred name";
        }

        assert (!sortAttribute.isEmpty()) : "Sort attribute should not be empty";

        List<Patient> patientList = model.getFullPatientList();
        List<Patient> patientArrayList = new ArrayList<>(patientList);

        Collections.sort(patientArrayList, this.comparator);

        model.updatePatientList(patientArrayList);
        model.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);

        logger.log(Level.INFO, "Successfully sorted the patient list by " + sortAttribute);

        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortAttribute));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return this.comparator.equals(otherSortCommand.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparator", this.comparator)
                .toString();
    }

}
