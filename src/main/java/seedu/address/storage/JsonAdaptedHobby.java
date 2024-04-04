package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Hobby;

/**
 * Jackson-friendly version of {@link Hobby}.
 */
class JsonAdaptedHobby {

    private final String hobbyName;

    /**
     * Constructs a {@code JsonAdaptedHobby} with the given {@code hobbyName}.
     */
    @JsonCreator
    public JsonAdaptedHobby(String hobbyName) {
        if (hobbyName == null || hobbyName.isEmpty()) {
            throw new IllegalArgumentException(Hobby.MESSAGE_CONSTRAINTS);
        }
        this.hobbyName = hobbyName;
    }

    /**
     * Converts a given {@code Hobby} into this class for Jackson use.
     */
    public JsonAdaptedHobby(Hobby source) {
        hobbyName = source.hobby;
    }

    @JsonValue
    public String getHobbyName() {
        return hobbyName;
    }

    /**
     * Converts this Jackson-friendly adapted hobby object into the model's {@code Hobby} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted hobby.
     */
    public Hobby toModelType() throws IllegalValueException {
        if (!Hobby.isValidHobby(hobbyName)) {
            throw new IllegalValueException(Hobby.MESSAGE_CONSTRAINTS);
        }
        return new Hobby(hobbyName);
    }

}
