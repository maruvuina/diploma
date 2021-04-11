package by.bsu.recipebook.util;

import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@UtilityClass
public class FormatterPattern {
    public static DateTimeFormatter getFormatterPatternToDisplay() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.UK)
                .withZone(ZoneId.systemDefault());
    }
}
