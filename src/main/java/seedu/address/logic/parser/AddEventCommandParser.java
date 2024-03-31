package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Event;

/**
 * Parses the user's input arguments and creates a new AddEvent Command
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddEventCommandParser.class);

    /**
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        logger.info("Received arguments: " + args + " for AddEventCommand; Attempting to parse..");

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATETIME) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }
        logger.info("All prefixes required are present.");

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE), e);
        }
        logger.info("Patient Index is valid");

        Event event = ParserUtil.parseEvent(argMultimap.getValue(PREFIX_NAME).get(),
                argMultimap.getValue(PREFIX_DATETIME).get());
        logger.info("All arguments received are valid");

        return new AddEventCommand(index, event);
    }
}
