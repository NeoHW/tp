package seedu.address.model.patient.comparators;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

// Solution below is adapted from https://www.geeksforgeeks.org/comparator-interface-java/
/**
 * Comparator for a Patient's preferred name in PatientSync.
 */
public class PreferredNameComparator implements Comparator<Patient> {
    public static final PreferredNameComparator PREFERRED_NAME_COMPARATOR = new PreferredNameComparator();
    private static final Logger logger = LogsCenter.getLogger(PreferredNameComparator.class);

    @Override
    public int compare(Patient patientOne, Patient patientTwo) {
        logger.log(Level.INFO, "Attempting to compare patients by preferred name");

        requireAllNonNull(patientOne, patientTwo);

        // Comparing patients via preferred name
        return patientOne.getPreferredName().toString()
                .compareToIgnoreCase(patientTwo.getPreferredName().toString());
    }

    @Override
    public String toString() {
        return "Comparator for Patients by Patients' preferred name in alphanumerical order.";
    }

}
