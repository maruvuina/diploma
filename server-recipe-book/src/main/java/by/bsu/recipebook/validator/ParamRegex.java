package by.bsu.recipebook.validator;

public class ParamRegex {
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+\\@[a-z0-9.-]+\\.[a-z]{2,4}$";

    public static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
}
