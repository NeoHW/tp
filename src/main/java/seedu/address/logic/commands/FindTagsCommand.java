package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.patient.TagContainsKeywordsPredicate;

/**
 * Finds and lists all patients in patient list whose tag contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTagsCommand extends Command {

    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all patient whose tag contains any of the specified keywords (case-insensitive)"
            + " and displays them as a list with index numbers.\n"
            + "Parameter: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD
            + " diabetes wheelchair";

    private static final Logger logger = LogsCenter.getLogger(FindTagsCommand.class);

    private final TagContainsKeywordsPredicate predicate;

    public FindTagsCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.log(Level.INFO, "Attempting to execute EditEventCommand.");
        requireNonNull(model);

        model.updateFilteredPatientList(predicate);
        logger.log(Level.INFO, "Successfully updated the patient list.");

        return new CommandResult(
                String.format(Messages.MESSAGE_PATIENT_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTagsCommand)) {
            return false;
        }

        FindTagsCommand otherFindTagsCommand = (FindTagsCommand) other;

        return predicate.equals(otherFindTagsCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
