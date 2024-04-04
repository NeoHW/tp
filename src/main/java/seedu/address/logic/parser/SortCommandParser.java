package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.patient.comparators.NameComparator.NAME_COMPARATOR;
import static seedu.address.model.patient.comparators.PreferredNameComparator.PREFERRED_NAME_COMPARATOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the user's input argument and creates a new SortCommand
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);
    private static final List<String> VALID_SORT_ATTRIBUTES_LIST = new ArrayList<>(Arrays.asList("n", "p"));

    /**
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Received arguments: " + args + "for SortCommand"
                + "\nAttempting to parse sortCommand...");

        String sortAttribute = parseSortAttribute(args);

        switch (sortAttribute) {
        case "":
            logger.log(Level.INFO, "Sorting Attribute: not specified, patient "
                    + sortAttribute
                    + " will be used as"
                    + " the sorting attribute");
            return new SortCommand(NAME_COMPARATOR);
        case "n":
            logger.log(Level.INFO, "Sorting Attribute: Patient " + sortAttribute);
            return new SortCommand(NAME_COMPARATOR);
        case "p":
            logger.log(Level.INFO, "Sorting Attribute: Patient's " + sortAttribute);
            return new SortCommand(PREFERRED_NAME_COMPARATOR);
        default:
            throw new AssertionError("Code should not reach here\n"
                    + "Invalid sort attribute: "
                    + sortAttribute);
        }
    }

    /**
     * Parses the {@code args} to check for valid sort attribute
     *
     * @param args
     * @return the parsed valid sort attribute
     * @throws ParseException if the user input does not conform to the expected format
     */
    public String parseSortAttribute(String args) throws ParseException {
        String trimmedSortAttribute = args.trim().toLowerCase();
        logger.log(Level.INFO, "trimmed sort attribute: " + trimmedSortAttribute);

        if (trimmedSortAttribute.length() > 1) {
            logger.log(Level.WARNING, "Invalid sort attribute length (> 1) received.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!trimmedSortAttribute.isEmpty() && !VALID_SORT_ATTRIBUTES_LIST.contains(trimmedSortAttribute)) {
            logger.log(Level.WARNING, "Invalid sort attribute received!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return trimmedSortAttribute;
    }
}
