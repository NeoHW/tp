package seedu.address.model.patient.comparators;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

/**
 * Comparator for Patient's name in PatientSync.
 */
public class NameComparator implements Comparator<Patient> {
    public static final NameComparator NAME_COMPARATOR = new NameComparator();
    private static final Logger logger = LogsCenter.getLogger(NameComparator.class);

    @Override
    public int compare(Patient patientOne, Patient patientTwo) {
        logger.log(Level.INFO, "Attempting to compare patients by name");

        requireAllNonNull(patientOne, patientTwo);

        // Comparing patients via preferred name
        return patientOne.getName().toString()
                .compareToIgnoreCase(patientTwo.getName().toString());
    }

    @Override
    public String toString() {
        return "Comparator for Patients by Patients' name in alphanumerical order.";
    }

}
