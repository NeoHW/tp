package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Event;

/**
 * Parses the user's input arguments and creates a new EditEventCommand
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditEventCommandParser.class);
    /**
     * Returns a new EditEventCommand instance.
     * @param args User's input.
     * @return A new EditEventCommand.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public EditEventCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Received arguments: " + args + " for EditEventCommand; Attempting to parse..");
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT, PREFIX_NAME, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT, PREFIX_NAME, PREFIX_DATETIME)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }
        logger.log(Level.INFO, "All prefixes required are present.");

        Index patientIndex;
        Index eventIndex;
        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE), e);
        }
        logger.log(Level.INFO, "Patient Index and Event Index are valid.");

        Event event = ParserUtil.parseEvent(argMultimap.getValue(PREFIX_NAME).get(),
                argMultimap.getValue(PREFIX_DATETIME).get());
        logger.log(Level.INFO, "All arguments are valid.");

        return new EditEventCommand(patientIndex, eventIndex, event);
    }
}

