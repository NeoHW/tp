package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July}  where prefixes are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {
    public static final String INVALID_PREFIXES_MESSAGE = "Invalid command prefixes detected. "
            + "Only %1$s prefixes allowed";
    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        return extractArguments(argsString, positions);
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString The arguments string to search for prefixes
     * @return a list of PrefixPosition objects representing the positions of prefixes
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString) {
        return PREFIX_LIST.stream()
                .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string from specified given prefixes.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    /**
     * {@see findAllPrefixPositions}
     */
    private static List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), 0);
        while (prefixPosition != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, prefixPosition);
            positions.add(extendedPrefix);
            prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), prefixPosition);
        }

        return positions;
    }

    /**
     * Returns the index of the first occurrence of {@code prefix} in
     * {@code argsString} starting from index {@code fromIndex}. An occurrence
     * is valid if there is a whitespace before {@code prefix}. Returns -1 if no
     * such occurrence can be found.
     *
     * E.g if {@code argsString} = "e/hip/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns -1 as there are no valid
     * occurrences of "p/" with whitespace before it. However, if
     * {@code argsString} = "e/hi p/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns 5.
     */
    private static int findPrefixPosition(String argsString, String prefix, int fromIndex) {
        int prefixIndex = argsString.indexOf(" " + prefix, fromIndex);
        return prefixIndex == -1 ? -1
                : prefixIndex + 1; // +1 as offset for whitespace
    }

    /**
     * Extracts prefixes and their argument values, and returns an {@code ArgumentMultimap} object that maps the
     * extracted prefixes to their respective arguments. Prefixes are extracted based on their zero-based positions in
     * {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentMultimap object that maps prefixes to their arguments
     */
    private static ArgumentMultimap extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map prefixes to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            // Extract and store prefixes and their arguments
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    /**
     * Returns the trimmed value of the argument in the arguments string specified by {@code currentPrefixPosition}.
     * The end position of the value is determined by {@code nextPrefixPosition}.
     */
    private static String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
                                        PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    /**
     * Checks for invalid prefixes other than {@code t/} in the provided arguments string.
     * Throws a ParseException if any prefix other than {@code t/} is found.
     *
     * @param argsString The arguments string to check for invalid prefixes
     * @throws ParseException if an invalid prefix is found
     */
    public static void checkInvalidPrefixesForTags(String argsString) throws ParseException {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);

        for (PrefixPosition position : positions) {
            Prefix prefix = position.getPrefix();
            if (!PREFIX_TAG.equals(prefix)) {
                throw new ParseException(String.format(INVALID_PREFIXES_MESSAGE, PREFIX_TAG));
            }
        }
    }

    /**
     * Checks for invalid prefixes, i.e., prefixes other than {@code n/}  or {@code d/} in the provided arguments
     * string.
     * Throws a ParseException if any prefix other than {@code n/} or {@code d/} is found.
     *
     * @param argsString The arguments string to check for invalid prefixes
     * @throws ParseException If an invalid prefix is found
     */
    public static void checkInvalidPrefixesForAddEvent(String argsString) throws ParseException {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        List<Prefix> validPrefixes = List.of(PREFIX_NAME, PREFIX_DATETIME);

        for (PrefixPosition position : positions) {
            Prefix prefix = position.getPrefix();
            if (!validPrefixes.contains(prefix)) {
                throw new ParseException(String.format(INVALID_PREFIXES_MESSAGE, validPrefixes));
            }
        }
    }

    /**
     * Checks for invalid prefixes, i.e., prefixes other than {@code e/} or {@code n/}  or {@code d/}
     * in the provided arguments string.
     * Throws a ParseException if any prefix other than {@code e/} or {@code n/} or {@code d/} is found.
     *
     * @param argsString The arguments string to check for invalid prefixes.
     * @throws ParseException If an invalid prefix is found.
     */
    public static void checkInvalidPrefixesForEditEvent(String argsString) throws ParseException {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        List<Prefix> validPrefixes = List.of(PREFIX_EVENT, PREFIX_NAME, PREFIX_DATETIME);

        for (PrefixPosition position : positions) {
            Prefix prefix = position.getPrefix();
            if (!validPrefixes.contains(prefix)) {
                throw new ParseException(String.format(INVALID_PREFIXES_MESSAGE, validPrefixes));
            }
        }
    }

    /**
     * Represents a prefix's position in an arguments string.
     */
    private static class PrefixPosition {
        private int startPosition;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return startPosition;
        }

        Prefix getPrefix() {
            return prefix;
        }
    }

}
