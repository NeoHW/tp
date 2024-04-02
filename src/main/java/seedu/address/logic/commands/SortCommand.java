package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREFERRED_NAME;

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

    public static final String MESSAGE_SORT_SUCCESS = "Successfully sorted all patients by ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all persons by the specified attribute "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [ATTRIBUTE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PREFERRED_NAME;

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final Comparator<Patient> comparator;
    private final String sortAttribute;

    /**
     * Creates a new SortCommand to sort the patients in patient list by the specified {@code comparator}
     *
     * @param comparator the comparator used to compare the patient list
     */
    public SortCommand(Comparator<Patient> comparator, String sortAttribute) {
        requireAllNonNull(comparator, sortAttribute);

        this.comparator = comparator;
        this.sortAttribute = sortAttribute;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.log(Level.INFO, "Attempting to execute SortCommand");

        requireNonNull(model);

        List<Patient> patientList = model.getFullPatientList();
        List<Patient> patientArrayList = new ArrayList<>(patientList);

        Collections.sort(patientArrayList, this.comparator);

        model.updatePatientList(patientArrayList);
        model.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);

        logger.log(Level.INFO, "Successfully sorted the patient list by " + this.sortAttribute);

        return new CommandResult(MESSAGE_SORT_SUCCESS + sortAttribute);
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
                .add("comparator", comparator)
                .toString();
    }

}
