package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // Identity fields
    private final PatientHospitalId patientHospitalId;
    private final Name name;
    private final PreferredName preferredName;

    // Data fields
    private final Set<FoodPreference> foodPreferences = new HashSet<>();
    private final Set<FamilyCondition> familyConditions = new HashSet<>();
    private final Set<Hobby> hobbies = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Event> events = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(PatientHospitalId patientHospitalId, Name name, PreferredName preferredName,
                   Set<FoodPreference> foodPreferences, Set<FamilyCondition> familyConditions, Set<Hobby> hobbies,
                   Set<Tag> tags) {
        requireAllNonNull(patientHospitalId, name, preferredName, foodPreferences, familyConditions, hobbies, tags);
        this.patientHospitalId = patientHospitalId;
        this.name = name;
        this.preferredName = preferredName;
        this.foodPreferences.addAll(foodPreferences);
        this.familyConditions.addAll(familyConditions);
        this.hobbies.addAll(hobbies);
        this.tags.addAll(tags);
    }

    /**
     * Constructs a Patient with {@param patientHospitalId},{@param name}, {@param preferredName},
     * {@param foodPreferences}, {@param familyConditions}, {@param hobbies},{@param tags}, {@param events}
     *
     * @param patientHospitalId patient's hospital ID
     * @param name patient's full name
     * @param preferredName patient's preferred name
     * @param foodPreferences patient's preferred food
     * @param familyConditions patient's family condition
     * @param hobbies patient's hobby
     * @param tags tag for patient
     * @param events
     */
    public Patient(PatientHospitalId patientHospitalId, Name name, PreferredName preferredName,
                   Set<FoodPreference> foodPreferences, Set<FamilyCondition> familyConditions, Set<Hobby> hobbies,
                   Set<Tag> tags, Set<Event> events) {
        requireAllNonNull(patientHospitalId, name, preferredName, foodPreferences, familyConditions, hobbies, tags);
        this.patientHospitalId = patientHospitalId;
        this.name = name;
        this.preferredName = preferredName;
        this.foodPreferences.addAll(foodPreferences);
        this.familyConditions.addAll(familyConditions);
        this.hobbies.addAll(hobbies);
        this.tags.addAll(tags);
        this.events.addAll(events);
    }

    public PatientHospitalId getPatientHospitalId() {
        return patientHospitalId;
    }

    public Name getName() {
        return name;
    }

    public PreferredName getPreferredName() {
        return preferredName;
    }

    /**
     * Returns an immutable family condition set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<FamilyCondition> getFamilyConditions() {
        return Collections.unmodifiableSet(familyConditions);
    }

    /**
     * Returns an immutable food preference set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<FoodPreference> getFoodPreferences() {
        return Collections.unmodifiableSet(foodPreferences);
    }

    /**
     * Returns an immutable hobby set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Hobby> getHobbies() {
        return Collections.unmodifiableSet(hobbies);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable date set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Event> getEvents() {
        return Collections.unmodifiableSet(this.events);
    }

    /**
     * Returns true if both patients have the same patientHospitalId.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getPatientHospitalId().equals(getPatientHospitalId());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return patientHospitalId.equals(otherPatient.patientHospitalId)
                && name.equals(otherPatient.name)
                && preferredName.equals(otherPatient.preferredName)
                && foodPreferences.equals(otherPatient.foodPreferences)
                && familyConditions.equals(otherPatient.familyConditions)
                && hobbies.equals(otherPatient.hobbies)
                && tags.equals(otherPatient.tags)
                && events.equals(otherPatient.events);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patientHospitalId, name, preferredName, foodPreferences, familyConditions, hobbies, tags,
            events);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("patientHospitalId", patientHospitalId)
            .add("name", name)
            .add("preferredName", preferredName)
            .add("foodPreferences", foodPreferences)
            .add("familyConditions", familyConditions)
            .add("hobbies", hobbies)
            .add("tags", tags)
            .add("events", this.events)
            .toString();
    }

}
