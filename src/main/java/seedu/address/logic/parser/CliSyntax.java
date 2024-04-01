package seedu.address.logic.parser;

import java.util.Set;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PID = new Prefix("id/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PREFERRED_NAME = new Prefix("p/");
    public static final Prefix PREFIX_FOOD_PREFERENCE = new Prefix("f/");
    public static final Prefix PREFIX_FAMILY_CONDITION = new Prefix("c/");
    public static final Prefix PREFIX_HOBBY = new Prefix("h/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATETIME = new Prefix("d/");
    public static final Prefix PREFIX_EVENT = new Prefix("e/");

    public static final Set<Prefix> PREFIX_LIST = Set.of(PREFIX_PID, PREFIX_NAME, PREFIX_PREFERRED_NAME,
            PREFIX_FOOD_PREFERENCE, PREFIX_FAMILY_CONDITION, PREFIX_HOBBY, PREFIX_TAG, PREFIX_DATETIME, PREFIX_EVENT);
}
