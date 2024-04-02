package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagsCommand object
 */
public class FindTagsCommandParser implements Parser<FindTagsCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindTagsCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the FindTagsCommand
     * and returns a FindTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagsCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Received arguments: " + args + " for FindTagsCommand; Attempting to parse..");
        requireNonNull(args);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
        }
        logger.log(Level.INFO, "All arguments are valid.");

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagsCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}
