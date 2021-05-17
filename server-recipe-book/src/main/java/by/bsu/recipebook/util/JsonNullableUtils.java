package by.bsu.recipebook.util;

import lombok.experimental.UtilityClass;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

@UtilityClass
public class JsonNullableUtils {

    public static <T> void changeIfPresent(JsonNullable<T> nullable, Consumer<T> consumer) {
        if (nullable.isPresent()) {
            System.out.println("nullable.get()---> " + nullable.get());
            consumer.accept(nullable.get());
        }
    }
}
