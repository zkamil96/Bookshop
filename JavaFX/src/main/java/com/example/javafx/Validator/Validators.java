package com.example.javafx.Validator;

import javafx.scene.control.TextFormatter;

import java.util.regex.Pattern;

public class Validators {
    public static TextFormatter<String> letterAndDigitTextFormatter() {
        final Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");

        return formatWithGivenPattern(pattern);
    }

    public static TextFormatter<String> digitTextFormatter() {
        final Pattern pattern = Pattern.compile("[0-9]{1,4}");

        return formatWithGivenPattern(pattern);
    }

    public static TextFormatter<String> letterTextFormatter() {
        final Pattern pattern = Pattern.compile("[a-zA-Z]*");

        return formatWithGivenPattern(pattern);
    }

    private static TextFormatter<String> formatWithGivenPattern(Pattern pattern) {
        return new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);
    }
}
