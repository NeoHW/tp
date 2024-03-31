package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditCommand.createEditedPatient;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.EditPatientDescriptor;
import seedu.address.model.patient.Event;
import seedu.address.model.patient.Patient;

/**
 * Edits an event for a patient in the address book.
 */
public class EditEventCommand extends Command {
    public static final String COMMAND_WORD = "edite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits an event for a specific patient using the patient index"
            + " in the patient list and event index.\n"
            + "Parameters: INDEX (must be a positive integer matching that of the Patient in the `list` command)"
            + " e/ [Index of the updated Event]"
            + " n/ [Name of the updated Event]"
            + " d/ [Updated Date / Datetime, in the format DD-MM-YYYY"
            + " / DD-MM-YYYY, HH:mm - HH:mm respectively]\n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_EVENT + " 1"
            + PREFIX_NAME + " Updated Event "
            + PREFIX_DATETIME + " 12-10-2024";

    public static final String MESSAGE_SUCCESS = "Event %1$s with ID %2$s on %3$s successfully updated "
            + "for Patient %4$s with ID %5$s";
    public static final String MESSAGE_DUPLICATE = "Event %1$s on %2$s already exists for Patient %3$s with ID %4$s";

    private final Index patientIndex;
    private final Index eventIndex;
    private final Event eventToUpdate;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * Constructs an EditEventCommand to edit the specified {@code eventToUpdate}
     * for {@code eventIndex} to the Patient with id {@code patientIndex}
     * @param patientIndex The id of the patient.
     * @param eventIndex The id of the event.
     * @param eventToUpdate The updated event.
     */
    public EditEventCommand(Index patientIndex, Index eventIndex, Event eventToUpdate) {
        requireAllNonNull(patientIndex, eventIndex, eventToUpdate);
        this.patientIndex = patientIndex;
        this.eventIndex = eventIndex;
        this.eventToUpdate = eventToUpdate;
        this.editPatientDescriptor = new EditPatientDescriptor();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();
        checkPatientIndex(lastShownList);

        Patient patientToEditEvent = lastShownList.get(patientIndex.getZeroBased());
        Set<Event> events = patientToEditEvent.getEvents();
        checkEventIndex(events);
        
        checkDuplicateEvent(events, patientToEditEvent);

        List<Event> eventList = new ArrayList<>(events);
        editEvent(eventList);

        Patient updatedPatient = createEditedPatient(patientToEditEvent, editPatientDescriptor);
        updatePatientList(model, patientToEditEvent, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventToUpdate.name, eventIndex.getOneBased(),
                eventToUpdate.date, updatedPatient.getName(), patientIndex.getOneBased()));
    }

    /**
     * Checks whether the patient index is valid.
     * @param lastShownList The last shown displayed list.
     * @throws CommandException Throws exception when patient index is invalid.
     */
    public void checkPatientIndex(List<Patient> lastShownList) throws CommandException {
        if (patientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks whether the event index is valid.
     * @param events Set of events of the specified patient.
     * @throws CommandException Throws exception when event index is invalid.
     */
    public void checkEventIndex(Set<Event> events) throws CommandException {
        if (eventIndex.getZeroBased() >= events.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the event to edit is the same as any of the existing events.
     * @param events Set of events.
     * @param patientToEditEvent The specified patient to edit event.
     * @throws CommandException Throws exception when there is a duplicate event.
     */
    public void checkDuplicateEvent(Set<Event> events, Patient patientToEditEvent) throws CommandException {
        if (events.contains(eventToUpdate)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE, eventToUpdate.name, eventToUpdate.date,
                    patientToEditEvent.getName(), patientIndex.getOneBased()));
        }
    }

    /**
     * Edits the specified event.
     * @param eventList List of events of the specified patient.
     */
    public void editEvent(List<Event> eventList) {
        Collections.sort(eventList);
        eventList.set(eventIndex.getZeroBased(), eventToUpdate);
        Set<Event> updatedEvents = new HashSet<>(eventList);
        editPatientDescriptor.setEvents(updatedEvents);
    }

    /**
     * Updates the displayed patient list.
     * @param model It stores current patient objects.
     * @param patientToEditEvent The specified patient to edit event.
     * @param updatedPatient The specified patient with updated event.
     */
    public void updatePatientList(Model model, Patient patientToEditEvent, Patient updatedPatient) {
        model.setPatient(patientToEditEvent, updatedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        EditEventCommand otherEditEventCommand = (EditEventCommand) other;
        return patientIndex.equals(otherEditEventCommand.patientIndex)
                && eventIndex.equals(otherEditEventCommand.eventIndex)
                && eventToUpdate.equals(otherEditEventCommand.eventToUpdate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("patientIndex", patientIndex)
                .add("eventIndex", eventIndex)
                .add("eventToUpdate", eventToUpdate)
                .toString();
    }
}
