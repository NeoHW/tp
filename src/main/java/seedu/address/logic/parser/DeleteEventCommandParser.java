package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the user's input arguments and creates a new DeleteEventCommand
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    private static final Logger logger = LogsCenter.getLogger(DeleteEventCommandParser.class);

    /**
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        logger.info("Received arguments: " + args + " for DeleteEventCommand; Attempting to parse..");

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT) || argMultimap.getPreamble().isEmpty()) {
            logger.log(Level.WARNING, "Invalid command format!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteEventCommand.MESSAGE_USAGE));
        }

        Index patientIndex;
        Index eventIndex;
        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
            logger.log(Level.INFO, "Patient index and Event Index successfully parsed!");
            return new DeleteEventCommand(patientIndex, eventIndex);
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Parsing of patient index or event index failed!");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
    }
}
