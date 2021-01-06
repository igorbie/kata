package string_calculator;

public class StringCalculatorUtils {
    private StringCalculatorUtils() {}

    public static final String NEW_LINE = "\n";
    public static final String COMMA = ",";
    public static final String PIPE = "|";
    public static final String CUSTOM_DELIMITER_PREFIX = "//";
    public static final String CUSTOM_DELIMITER_GROUPING_REGEX = "\\[(.*?)\\]";
    public static final String DEFAULT_DELIMITED_REGEX = COMMA.concat(PIPE).concat(NEW_LINE);
    public static final int MAXIMUM_NUMBER_SIZE = 1000;
}
