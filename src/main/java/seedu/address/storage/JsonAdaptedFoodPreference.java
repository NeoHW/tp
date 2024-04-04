package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.FoodPreference;

/**
 * Jackson-friendly version of {@link FoodPreference}.
 */
class JsonAdaptedFoodPreference {

    private final String foodPreferenceName;

    /**
     * Constructs a {@code JsonAdaptedFoodPreference} with the given {@code foodPreferenceName}.
     */
    @JsonCreator
    public JsonAdaptedFoodPreference(String foodPreferenceName) {
        if (foodPreferenceName == null || foodPreferenceName.isEmpty()) {
            throw new IllegalArgumentException(FoodPreference.MESSAGE_CONSTRAINTS);
        }
        this.foodPreferenceName = foodPreferenceName;
    }

    /**
     * Converts a given {@code FoodPreference} into this class for Jackson use.
     */
    public JsonAdaptedFoodPreference(FoodPreference source) {
        foodPreferenceName = source.foodPreference;
    }

    @JsonValue
    public String getFoodPreferenceName() {
        return foodPreferenceName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code FoodPreference} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public FoodPreference toModelType() throws IllegalValueException {
        if (!FoodPreference.isValidFoodPreference(foodPreferenceName)) {
            throw new IllegalValueException(FoodPreference.MESSAGE_CONSTRAINTS);
        }
        return new FoodPreference(foodPreferenceName);
    }

}
