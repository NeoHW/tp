package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, null));
        assertThrows(NullPointerException.class, () -> new Event(null, "01-01-2100"));
        assertThrows(NullPointerException.class, () -> new Event("Family Visit", null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("", "01-01-2100"));
        assertThrows(IllegalArgumentException.class, () -> new Event("              ", "01-01-2100"));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit", "something"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit", "1-1-2100"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit", "01-01-22"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit", "01-01-1999"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit", "01-01-2024"));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String validDate = "01-01-2100";

        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + ", HH:mm - HH:mm"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 99:88 - 99:99"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 0000 - 2359"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 06:00 - 00:00"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 01:00 - 00:59"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 01:00, 00:59"));
        assertThrows(IllegalArgumentException.class, () -> new Event("Family Visit",
                validDate + "21-02-2100, 01:00 - 00:59 - 01:00"));
    }

    @Test
    public void isValidEvent() {
        assertThrows(NullPointerException.class, () -> Event.isValidEvent(null));

        assertFalse(Event.isValidEvent(""));
        assertFalse(Event.isValidEvent("   "));
        assertFalse(Event.isValidEvent("01-01-2100, HH:mm - HH:mm"));
        assertFalse(Event.isValidEvent("01-01-2100, 99:99 - 99:99"));
        assertFalse(Event.isValidEvent("01-01-2100, 12:12 - 99:99"));
        assertFalse(Event.isValidEvent("01-01-2100, 12:12 to 12:12"));

        assertTrue(Event.isValidEvent("01-01-2100       "));
        assertTrue(Event.isValidEvent("       01-01-2100"));
        assertTrue(Event.isValidEvent("01-01-2100"));
        assertTrue(Event.isValidEvent("21-02-2100, 01:00 - 19:00"));
        assertTrue(Event.isValidEvent("21-02-2100, 00:00 - 23:59"));
        assertTrue(Event.isValidEvent("01-01-2100, 12:12 - 12:12        "));
        assertTrue(Event.isValidEvent("          01-01-2100, 12:12 - 12:12"));
        assertTrue(Event.isValidEvent("21-02-2100, 00:00 - 00:00"));
    }


    @Test
    public void compareTo() {
        Event earlierEvent = new Event("Family Visit", "01-01-9998, 12:12 - 12:12");
        Event nullTimeEvent = new Event("Family Visit", "01-01-9998");
        Event laterDateEvent = new Event("Family Visit", "02-01-9999, 12:12 - 12:12");
        Event laterMonthEvent = new Event("Family Visit", "01-02-9999, 12:12 - 12:12");
        Event laterYearEvent = new Event("Family Visit", "01-01-9999, 12:12 - 12:12");
        Event laterStartTimeEvent = new Event("Family Visit", "01-01-9999, 13:12 - 13:12");
        Event laterEndTimeEvent = new Event("Family Visit", "01-01-9999, 12:12 - 12:13");
        Event laterNameEvent = new Event("Z", "01-01-9999, 12:12 - 12:12");

        assertTrue(earlierEvent.compareTo(nullTimeEvent) > 0);
        assertTrue(nullTimeEvent.compareTo(earlierEvent) < 0);

        assertTrue(earlierEvent.compareTo(laterDateEvent) < 0);
        assertTrue(laterDateEvent.compareTo(earlierEvent) > 0);

        assertTrue(earlierEvent.compareTo(laterMonthEvent) < 0);
        assertTrue(laterMonthEvent.compareTo(earlierEvent) > 0);

        assertTrue(earlierEvent.compareTo(laterYearEvent) < 0);
        assertTrue(laterYearEvent.compareTo(earlierEvent) > 0);

        assertTrue(earlierEvent.compareTo(laterStartTimeEvent) < 0);
        assertTrue(laterStartTimeEvent.compareTo(earlierEvent) > 0);

        assertTrue(earlierEvent.compareTo(laterEndTimeEvent) < 0);
        assertTrue(laterEndTimeEvent.compareTo(earlierEvent) > 0);

        assertTrue(earlierEvent.compareTo(laterNameEvent) < 0);
        assertTrue(laterNameEvent.compareTo(earlierEvent) > 0);
    }

    @Test
    public void equals() {
        Event date = new Event("Family Visit", "01-01-2100, 12:12 - 12:12");

        assertFalse(date.equals(null));
        assertFalse(date.equals("Something"));
        assertFalse(date.equals(10));
        assertFalse(date.equals(new Event("Family Visit", "02-01-2100, 12:12 - 12:12")));
        assertFalse(date.equals(new Event("Family Visit", "01-02-2100, 12:12 - 12:12")));
        assertFalse(date.equals(new Event("Family Visit", "01-02-9999, 12:12 - 12:12")));
        assertFalse(date.equals(new Event("Family Visit", "01-01-2100, 12:11 - 12:12")));
        assertFalse(date.equals(new Event("Family Visit", "01-01-2100, 11:12 - 12:12")));

        assertTrue(date.equals(date));
        assertTrue(date.equals(new Event("Family Visit", "01-01-2100, 12:12 - 12:12")));
    }

    @Test
    public void dateToString() {
        Event date = new Event("Family Visit", "01-01-2100");

        assertTrue(date.toString().equals("Family Visit (01-01-2100)"));
    }

    @Test
    public void dateTimeToString() {
        Event date = new Event("Family Visit", "01-01-2100, 12:12 - 12:12");

        assertTrue(date.toString().equals("Family Visit (01-01-2100, from 12:12 to 12:12)"));
    }
}
