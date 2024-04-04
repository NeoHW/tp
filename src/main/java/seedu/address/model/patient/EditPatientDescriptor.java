package seedu.address.model.patient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the patient with. Each non-empty field value will replace the
 * corresponding field value of the patient.
 */
public class EditPatientDescriptor {
    private PatientHospitalId patientHospitalId;
    private Name name;
    private PreferredName preferredName;
    private Set<FoodPreference> foodPreferences;
    private Set<FamilyCondition> familyConditions;
    private Set<Hobby> hobbies;
    private Set<Tag> tags;
    private Set<Event> events;

    public EditPatientDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPatientDescriptor(EditPatientDescriptor toCopy) {
        setPatientHospitalId(toCopy.patientHospitalId);
        setName(toCopy.name);
        setPreferredName(toCopy.preferredName);
        setFoodPreferences(toCopy.foodPreferences);
        setFamilyConditions(toCopy.familyConditions);
        setHobbies(toCopy.hobbies);
        setTags(toCopy.tags);
        setEvents(toCopy.events);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(patientHospitalId, name, preferredName, foodPreferences, familyConditions,
            hobbies, tags, events);
    }

    public void setPatientHospitalId(PatientHospitalId id) {
        this.patientHospitalId = id;
    }

    public Optional<PatientHospitalId> getPatientHospitalId() {
        return Optional.ofNullable(patientHospitalId);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPreferredName(PreferredName preferredName) {
        this.preferredName = preferredName;
    }

    public Optional<PreferredName> getPreferredName() {
        return Optional.ofNullable(preferredName);
    }

    /**
     * Sets {@code foodPreferences} to this object's {@code foodPreferences}.
     * A defensive copy of {@code foodPreferences} is used internally.
     */
    public void setFoodPreferences(Set<FoodPreference> foodPreferences) {
        this.foodPreferences = (foodPreferences != null) ? new HashSet<>(foodPreferences) : null;
    }

    /**
     * Returns an unmodifiable food preference set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code foodPreferences} is null.
     */
    public Optional<Set<FoodPreference>> getFoodPreferences() {
        return (foodPreferences != null) ? Optional.of(Collections.unmodifiableSet(foodPreferences)) : Optional.empty();
    }

    /**
     * Sets {@code familyConditions} to this object's {@code familyConditions}.
     * A defensive copy of {@code familyConditions} is used internally.
     */
    public void setFamilyConditions(Set<FamilyCondition> familyConditions) {
        this.familyConditions = (familyConditions != null) ? new HashSet<>(familyConditions) : null;
    }

    /**
     * Returns an unmodifiable food preference set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code familyConditions} is null.
     */
    public Optional<Set<FamilyCondition>> getFamilyConditions() {
        return (familyConditions != null)
            ? Optional.of(Collections.unmodifiableSet(familyConditions))
            : Optional.empty();
    }

    /**
     * Sets {@code hobbies} to this object's {@code hobbies}.
     * A defensive copy of {@code hobbies} is used internally.
     */
    public void setHobbies(Set<Hobby> hobbies) {
        this.hobbies = (hobbies != null) ? new HashSet<>(hobbies) : null;
    }

    /**
     * Returns an unmodifiable food preference set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code hobbies} is null.
     */
    public Optional<Set<Hobby>> getHobbies() {
        return (hobbies != null)
            ? Optional.of(Collections.unmodifiableSet(hobbies))
            : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    public Optional<Set<Event>> getEvents() {
        return events != null ? Optional.of(Collections.unmodifiableSet(events)) : Optional.empty();
    }

    public void setEvents(Set<Event> events) {
        this.events = events != null ? new HashSet<>(events) : null;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPatientDescriptor)) {
            return false;
        }

        EditPatientDescriptor otherEditPatientDescriptor = (EditPatientDescriptor) other;
        return Objects.equals(patientHospitalId, otherEditPatientDescriptor.patientHospitalId)
            && Objects.equals(name, otherEditPatientDescriptor.name)
            && Objects.equals(preferredName, otherEditPatientDescriptor.preferredName)
            && Objects.equals(foodPreferences, otherEditPatientDescriptor.foodPreferences)
            && Objects.equals(familyConditions, otherEditPatientDescriptor.familyConditions)
            && Objects.equals(hobbies, otherEditPatientDescriptor.hobbies)
            && Objects.equals(tags, otherEditPatientDescriptor.tags)
            && Objects.equals(events, otherEditPatientDescriptor.events);
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
            .add("events", events)
            .toString();
    }
}
