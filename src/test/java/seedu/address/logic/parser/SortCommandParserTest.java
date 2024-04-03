package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.model.patient.comparators.NameComparator.NAME_COMPARATOR;
import static seedu.address.model.patient.comparators.PreferredNameComparator.PREFERRED_NAME_COMPARATOR;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidSortAttributeOfLengthOne_throwsParseException() {
        // Invalid sort attribute: "c" instead of "", "n", "p"
        String userInput = "c";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidAttributesOfLengthMoreThanOne_throwsParseException() {
        // Invalid sort attribute: "cappy" instead of "", "n", "p"
        String userInput = "cappy";
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Invalid sort attribute: "c a" instead of "", "n", "p"
        String userInputWithSpace = "c a";
        assertThrows(ParseException.class, () -> parser.parse(userInputWithSpace));
    }

    @Test
    public void parse_oneInvalidAndOneValidSortAttribute_throwsParseException() {
        // Invalid sort attribute mixed with valid sort Attribute: "ns" instead of "", "n", "p"
        String userInput = "ns";
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Invalid sort attribute mixed with valid sort Attribute: "n s" instead of "", "n", "p"
        String userInputWithSpace = "n s";
        assertThrows(ParseException.class, () -> parser.parse(userInputWithSpace));
    }

    @Test
    public void parse_multipleInvalidAndOneValidSortAttribute_throwsParseException() {
        // Invalid sort attribute mixed with valid sort Attribute: "nsa" instead of "", "n", "p"
        String userInput = "nsa";
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Invalid sort attribute mixed with valid sort Attribute: "n sa" instead of "", "n", "p"
        String userInputWithSpace = "n sa";
        assertThrows(ParseException.class, () -> parser.parse(userInputWithSpace));
    }

    @Test
    public void parse_multipleInvalidAndMultipleValidSortAttribute_throwsParseException() {
        // Invalid sort attribute mixed with valid sort Attribute: "nsp" instead of "", "n", "p"
        String userInput = "nsp";
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Invalid sort attribute mixed with valid sort Attribute: "nsp" instead of "", "n", "p"
        String userInputWithSpace = "n s p";
        assertThrows(ParseException.class, () -> parser.parse(userInputWithSpace));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {

        // Test for arg: n
        String userInput = "n";

        SortCommand expectedCommand = new SortCommand(NAME_COMPARATOR);
        SortCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);

        // Test for arg: ""
        userInput = "";

        expectedCommand = new SortCommand(NAME_COMPARATOR);
        command = parser.parse(userInput);
        assertEquals(expectedCommand, command);

        // Test for arg: p
        userInput = "p";

        expectedCommand = new SortCommand(PREFERRED_NAME_COMPARATOR);
        command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid sort Attribute of length 1
        assertThrows(ParseException.class, () -> parser.parse("1"));

        // Invalid sort Attribute of length 2
        assertThrows(ParseException.class, () -> parser.parse("as"));

        // Invalid sort Attribute of length 1 with space
        assertThrows(ParseException.class, () -> parser.parse("1 "));
        assertThrows(ParseException.class, () -> parser.parse(" 1"));
        assertThrows(ParseException.class, () -> parser.parse("1   "));
        assertThrows(ParseException.class, () -> parser.parse("  1 "));

        // Invalid sort Attribute of length more than 1 with spaces
        assertThrows(ParseException.class, () -> parser.parse("asd  "));
        assertThrows(ParseException.class, () -> parser.parse("a s d  "));
        assertThrows(ParseException.class, () -> parser.parse("  a sd  "));

        // Mix of Valid and Invalid sort attributes
        assertThrows(ParseException.class, () -> parser.parse("pn"));
        assertThrows(ParseException.class, () -> parser.parse("np"));
        assertThrows(ParseException.class, () -> parser.parse("apn"));
        assertThrows(ParseException.class, () -> parser.parse("npa"));
        assertThrows(ParseException.class, () -> parser.parse("ap n "));
        assertThrows(ParseException.class, () -> parser.parse("n ap"));
        assertThrows(ParseException.class, () -> parser.parse("n a p"));
    }
}
