package string_calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static string_calculator.StringCalculatorUtils.*;

public class StringCalculator {

    public Integer add(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        List<String> numbers = getNumbers(input);
        validateNegatives(numbers);
        return getSum(numbers);
    }

    private List<String> getNumbers(String input) {
        List<String> numbers;
        if (hasCustomDelimiters(input)) {
            numbers = getCustomDelimitedNumbers(input);
        } else {
            numbers = getDefaultDelimitedNumbers(input);
        }
        return numbers;
    }

    private List<String> getDefaultDelimitedNumbers(String input) {
        return Arrays.asList(input.split(DEFAULT_DELIMITED_REGEX));
    }

    private List<String> getCustomDelimitedNumbers(String input) {
        String numbers = input.split(NEW_LINE)[1];
        String customDelimitersRegex = getCustomDelimitersRegex(input);
        return Arrays.asList(numbers.split(customDelimitersRegex));
    }

    private boolean hasCustomDelimiters(String input) {
        return input.startsWith(CUSTOM_DELIMITER_PREFIX);
    }

    private Integer getSum(Collection<String> numbers) {
        return numbers.stream()
                .map(Integer::valueOf)
                .filter(number -> number <= MAXIMUM_NUMBER_SIZE)
                .reduce(0, Integer::sum);
    }

    private void validateNegatives(Collection<String> numbers) {
        Set<Integer> negatives = numbers.stream()
                .map(Integer::valueOf)
                .filter(number -> number < 0)
                .collect(Collectors.toSet());

        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(String.format("Negatives not allowed %s", negatives));
        }
    }

    private String getCustomDelimitersRegex(String input) {
        String inputDelimiters = input.split(NEW_LINE)[0].substring(2);
        List<String> extractedDelimiters = new ArrayList<>();

        if (inputDelimiters.length() == 1) {
            extractedDelimiters.add(inputDelimiters);
        } else {
            Matcher matcher = Pattern.compile(CUSTOM_DELIMITER_GROUPING_REGEX).matcher(inputDelimiters);
            while (matcher.find()) {
                extractedDelimiters.add(matcher.group(1));
            }
        }
        return getQuotedDelimitersRegex(extractedDelimiters);
    }

    private String getQuotedDelimitersRegex(List<String> delimiters) {
        return delimiters.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining(PIPE));
    }
}
