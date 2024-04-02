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
            sortAttribute = "name";
            logger.log(Level.INFO, "Sorting Attribute: not specified, patient "
                    + sortAttribute
                    + " will be used as"
                    + " the sorting attribute");
            return new SortCommand(NAME_COMPARATOR, sortAttribute);
        case "n":
            sortAttribute = "name";
            logger.log(Level.INFO, "Sorting Attribute: Patient " + sortAttribute);
            return new SortCommand(NAME_COMPARATOR, sortAttribute);
        case "p":
            sortAttribute = "preferred name";
            logger.log(Level.INFO, "Sorting Attribute: Patient's " + sortAttribute);
            return new SortCommand(PREFERRED_NAME_COMPARATOR, sortAttribute);
        default:
            logger.log(Level.WARNING, "Invalid Sorting Attribute! Sort Attribute Received: " + sortAttribute);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the sorting attribute
     *
     * @param args
     * @return the parsed sorting attribute
     * @throws ParseException if the user input does not conform to the expected format
     */
    public String parseSortAttribute(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        logger.log(Level.INFO, "trimmed argument: " + trimmedArgs);

        if (trimmedArgs.length() > 1) {
            logger.log(Level.WARNING, "Invalid argument length (> 1) received.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!trimmedArgs.isEmpty() && !VALID_SORT_ATTRIBUTES_LIST.contains(trimmedArgs)) {
            logger.log(Level.WARNING, "Invalid sorting attribute received!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return trimmedArgs;
    }
}
