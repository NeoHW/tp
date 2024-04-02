package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.model.patient.comparators.NameComparator.NAME_COMPARATOR;
import static seedu.address.model.patient.comparators.PreferredNameComparator.PREFERRED_NAME_COMPARATOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;

import java.util.Comparator;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagsCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
    public void parse_validArgs_returnsAddTagsCommand() throws ParseException {

        // Test for arg: n
        String userInput = "n";

        SortCommand expectedCommand = new SortCommand(NAME_COMPARATOR, "name");
        SortCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);

        // Test for arg: ""
        userInput = "";

        expectedCommand = new SortCommand(PREFERRED_NAME_COMPARATOR, "name");
        command = parser.parse(userInput);
        assertEquals(expectedCommand, command);

        // Test for arg: p
        userInput = "p";

        expectedCommand = new SortCommand(PREFERRED_NAME_COMPARATOR, "preferred name");
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
