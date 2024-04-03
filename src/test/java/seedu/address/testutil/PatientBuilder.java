package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.patient.Event;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientHospitalId;
import seedu.address.model.patient.PreferredName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_ID = "12334";
    public static final String DEFAULT_NAME = "Amy Bee Kian Ling";
    public static final String DEFAULT_PREFERRED_NAME = "Amy";
    public static final String DEFAULT_FOOD_PREFERENCE = "Hor Fun";
    public static final String DEFAULT_FAMILY_CONDITION = "Financially unstable";
    public static final String DEFAULT_HOBBY = "Singing karaoke";

    private PatientHospitalId patientHospitalId;
    private Name name;
    private PreferredName preferredName;
    private Set<FoodPreference> foodPreferences;
    private Set<FamilyCondition> familyConditions;
    private Set<Hobby> hobbies;
    private Set<Tag> tags;
    private Set<Event> events;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        patientHospitalId = new PatientHospitalId(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        preferredName = new PreferredName(DEFAULT_PREFERRED_NAME);
        // Include the default food preference, family condition, hobby
        foodPreferences = SampleDataUtil.getFoodPreferenceSet(DEFAULT_FOOD_PREFERENCE);
        familyConditions = SampleDataUtil.getFamilyConditionSet(DEFAULT_FAMILY_CONDITION);
        hobbies = SampleDataUtil.getHobbySet(DEFAULT_HOBBY);
        tags = new HashSet<>();
        events = new HashSet<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        patientHospitalId = patientToCopy.getPatientHospitalId();
        name = patientToCopy.getName();
        preferredName = patientToCopy.getPreferredName();
        foodPreferences = new HashSet<>(patientToCopy.getFoodPreferences());
        familyConditions = new HashSet<>(patientToCopy.getFamilyConditions());
        hobbies = new HashSet<>(patientToCopy.getHobbies());
        tags = new HashSet<>(patientToCopy.getTags());
        events = new HashSet<>(patientToCopy.getEvents());
    }

    /**
     * Sets the {@code PatientHospitalId} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPatientHospitalId(String id) {
        this.patientHospitalId = new PatientHospitalId(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code PreferredName} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPreferredName(String preferredName) {
        this.preferredName = new PreferredName(preferredName);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the FoodPreference of the {@code Patient} that we are building.
     * @param foodPreferences array of string of preferred food
     * @return return PatientBuilder withFoodPreferences
     */
    public PatientBuilder withFoodPreferences(String ... foodPreferences) {
        this.foodPreferences = SampleDataUtil.getFoodPreferenceSet(foodPreferences);
        return this;
    }

    /**
     * Sets the FamilyCondition of the {@code Patient} that we are building.
     * @param familyConditions array of string of preferred food
     * @return return PatientBuilder withFamilyConditions
     */
    public PatientBuilder withFamilyConditions(String ... familyConditions) {
        this.familyConditions = SampleDataUtil.getFamilyConditionSet(familyConditions);
        return this;
    }

    /**
     * Sets the {@code Hobby} of the {@code Patient} that we are building.
     */
    public PatientBuilder withHobbies(String ... hobbies) {
        this.hobbies = SampleDataUtil.getHobbySet(hobbies);
        return this;
    }

    /**
     * Sets the Event of the {@code Patient} that we are building,
     * with the name and date/datetime of the event
     *
     * @param names description of events
     * @param events array of string of dates
     * @return return PatientBuilder withImportantDates
     */
    public PatientBuilder withEvents(String[] names, String[] events) {
        this.events = SampleDataUtil.getEventSet(names, events);
        return this;
    }

    /**
     * Builds {@code Patient} with new Patient.
     */
    public Patient build() {
        return new Patient(patientHospitalId, name, preferredName, this.foodPreferences, familyConditions, hobbies,
            tags, events);
    }

}
