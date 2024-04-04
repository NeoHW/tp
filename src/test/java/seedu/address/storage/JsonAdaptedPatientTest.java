package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.FamilyCondition;
import seedu.address.model.patient.FoodPreference;
import seedu.address.model.patient.Hobby;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.PatientHospitalId;
import seedu.address.model.patient.PreferredName;

public class JsonAdaptedPatientTest {
    private static final String INVALID_ID = "-1";
    private static final String INVALID_NAME = "R@chel Lim";
    private static final String INVALID_PREFERRED_NAME = "R@chel";
    private static final String INVALID_FOOD_PREFERENCE = " ";
    private static final String INVALID_FAMILY_CONDITION = " ";
    private static final String INVALID_HOBBY = " ";
    private static final String INVALID_TAG = "#Diabetes";
    private static final String INVALID_EVENT_DATETIME_STR = "Something";

    private static final String VALID_PATIENT_HOSPITAL_ID = BENSON.getPatientHospitalId().patientHospitalId;
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PREFERRED_NAME = BENSON.getPreferredName().toString();
    private static final List<JsonAdaptedFoodPreference> VALID_FOOD_PREFERENCES = BENSON.getFoodPreferences().stream()
        .map(JsonAdaptedFoodPreference::new)
        .collect(Collectors.toList());
    private static final List<JsonAdaptedFamilyCondition> VALID_FAMILY_CONDITIONS = BENSON.getFamilyConditions()
        .stream()
        .map(JsonAdaptedFamilyCondition::new)
        .collect(Collectors.toList());

    private static final String VALID_HOBBY = "Reading";
    private static final List<JsonAdaptedHobby> VALID_HOBBIES = BENSON.getHobbies()
        .stream()
        .map(JsonAdaptedHobby::new)
        .collect(Collectors.toList());

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_EVENT_NAME = "Birthday";
    private static final List<JsonAdaptedEvent> VALID_EVENTS = BENSON.getEvents().stream()
            .map(JsonAdaptedEvent::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPatient person = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidPatientHospitalId_throwsIllegalValueException() {
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(INVALID_ID, VALID_NAME, VALID_PREFERRED_NAME,
                VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = PatientHospitalId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPatientHospitalId_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(null, VALID_NAME, VALID_PREFERRED_NAME,
            VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PatientHospitalId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient person =
                new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, INVALID_NAME, VALID_PREFERRED_NAME,
                    VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, null, VALID_PREFERRED_NAME,
            VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPreferredName_throwsIllegalValueException() {
        JsonAdaptedPatient person =
                new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, INVALID_PREFERRED_NAME,
                    VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = PreferredName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPreferredName_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, null,
            VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PreferredName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidFoodPreference_throwsIllegalValueException() {
        List<JsonAdaptedFoodPreference> invalidFoodPreferences = new ArrayList<>(VALID_FOOD_PREFERENCES);
        invalidFoodPreferences.add(new JsonAdaptedFoodPreference(INVALID_FOOD_PREFERENCE));
        JsonAdaptedPatient person =
                new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
                    invalidFoodPreferences, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = FoodPreference.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFoodPreference_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
            null, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, FoodPreference.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void constructor_nullFoodPreference_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedFoodPreference((String) null));
    }

    @Test
    public void constructor_emptyFoodPreference_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedFoodPreference(""));
    }

    @Test
    public void toModelType_invalidFamilyCondition_throwsIllegalValueException() {
        List<JsonAdaptedFamilyCondition> invalidFamilyConditions = new ArrayList<>(VALID_FAMILY_CONDITIONS);
        invalidFamilyConditions.add(new JsonAdaptedFamilyCondition(INVALID_FOOD_PREFERENCE));
        JsonAdaptedPatient person =
                new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
                    VALID_FOOD_PREFERENCES, invalidFamilyConditions, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = FamilyCondition.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFamilyCondition_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
            VALID_FOOD_PREFERENCES, null, VALID_HOBBIES, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, FamilyCondition.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void constructor_nullFamilyCondition_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedFamilyCondition((String) null));
    }

    @Test
    public void constructor_emptyFamilyCondition_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedFamilyCondition(""));
    }

    @Test
    public void toModelType_invalidHobby_throwsIllegalValueException() {
        List<JsonAdaptedHobby> invalidHobbies = new ArrayList<>(VALID_HOBBIES);
        invalidHobbies.add(new JsonAdaptedHobby(INVALID_HOBBY));
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
                VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, invalidHobbies, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = Hobby.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullHobby_throwsIllegalValueException() {
        JsonAdaptedPatient person = new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
            VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, null, VALID_TAGS, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Hobby.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidHobbyName_throwsIllegalValueException() {
        JsonAdaptedHobby adaptedHobby = new JsonAdaptedHobby(INVALID_HOBBY);
        assertThrows(IllegalValueException.class, adaptedHobby::toModelType);
    }

    @Test
    public void constructor_nullHobbyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedHobby((String) null));
    }

    @Test
    public void constructor_emptyHobbyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedHobby(""));
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPatient person =
                new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
                    VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, invalidTags, VALID_EVENTS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        List<JsonAdaptedEvent> invalidImportantDates = new ArrayList<>(VALID_EVENTS);
        invalidImportantDates.add(new JsonAdaptedEvent(VALID_EVENT_NAME,
            INVALID_EVENT_DATETIME_STR));
        JsonAdaptedPatient person =
            new JsonAdaptedPatient(VALID_PATIENT_HOSPITAL_ID, VALID_NAME, VALID_PREFERRED_NAME,
                VALID_FOOD_PREFERENCES, VALID_FAMILY_CONDITIONS, VALID_HOBBIES, VALID_TAGS, invalidImportantDates);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
