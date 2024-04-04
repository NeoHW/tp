package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_CONDITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAMILY_CONDITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOBBY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOBBY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREFERRED_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREFERRED_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEPRESSION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {
    public static final Patient ALICE = new PatientBuilder().withPatientHospitalId("12234").withName("Alice Pauline")
        .withPreferredName("Alice").withFoodPreferences("Duck rice", "Ban Mian")
        .withFamilyConditions("Have 2 daughters working overseas").withHobbies("Watching Hong Kong drama")
        .withTags("depression").build();
    public static final Patient BENSON = new PatientBuilder().withPatientHospitalId("12235").withName("Benson Meier")
        .withPreferredName("Benson").withFoodPreferences("Kampung Fried Rice")
        .withFamilyConditions("Wife in ICU", "No children").withHobbies("Listen to Coldplay songs")
        .withTags("diabetes", "cholesterol").withEvents(
            new String[] { VALID_EVENT_NAME, VALID_EVENT_NAME },
            new String[] { VALID_EVENT_DATE, VALID_EVENT_DATETIME }).build();;
    public static final Patient CARL = new PatientBuilder().withPatientHospitalId("12236").withName("Carl Kurz")
        .withPreferredName("Ah Carl").withFoodPreferences("Sambal fish").withFamilyConditions("Has no children")
        .withHobbies("Likes to play mahjong").build();
    public static final Patient DANIEL = new PatientBuilder().withPatientHospitalId("12237").withName("Daniel Meier")
        .withPreferredName("Ah Da").withFoodPreferences("Steak").withFamilyConditions("Nieces not around Singapore")
        .withHobbies("Cycling").withTags("eczema").build();
    public static final Patient ELLE = new PatientBuilder().withPatientHospitalId("12238").withName("Elle Meyer")
        .withPreferredName("Elle").withFoodPreferences("Maggie Goreng").withFamilyConditions("Husband working overseas")
        .withHobbies("Dancing", "Singing").build();
    public static final Patient FIONA = new PatientBuilder().withPatientHospitalId("12239").withName("Fiona Kunz")
        .withPreferredName("Ms Fi").withFoodPreferences("Fish soup without milk")
        .withFamilyConditions("Daughter fights with her every week").withHobbies("dancing").build();
    public static final Patient GEORGE = new PatientBuilder().withPatientHospitalId("12240").withName("George Best")
        .withPreferredName("George").withFoodPreferences("Salmon with lemon").withFamilyConditions("no children")
        .withHobbies("reading books").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withPatientHospitalId("12241").withName("Hoon Meier")
        .withPreferredName("Ah Hoon").withFoodPreferences("Char Kuey Teow")
        .withFamilyConditions("Husband unable to walk").withHobbies("Reads novel").build();
    public static final Patient IDA = new PatientBuilder().withPatientHospitalId("12242").withName("Ida Mueller")
        .withPreferredName("Puan Ida").withFoodPreferences("Nasi Kandang")
        .withFamilyConditions("Children abandoned her").withHobbies("Plays congkak").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withPatientHospitalId(VALID_ID_AMY).withName(VALID_NAME_AMY)
        .withPreferredName(VALID_PREFERRED_NAME_AMY).withFoodPreferences(VALID_FOOD_AMY)
        .withFamilyConditions(VALID_FAMILY_CONDITION_AMY).withHobbies(VALID_HOBBY_AMY)
        .withTags(VALID_TAG_DIABETES).build();
    public static final Patient BOB = new PatientBuilder().withPatientHospitalId(VALID_ID_BOB).withName(VALID_NAME_BOB)
        .withPreferredName(VALID_PREFERRED_NAME_BOB).withFoodPreferences(VALID_FOOD_BOB)
        .withFamilyConditions(VALID_FAMILY_CONDITION_BOB).withHobbies(VALID_HOBBY_BOB)
        .withTags(VALID_TAG_DEPRESSION, VALID_TAG_DIABETES).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical patients.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
