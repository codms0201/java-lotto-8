package lotto.util;

public class InputValidator {
    private static final int LOTTO_PRICE = 1000;
    private static final String NUMBER_DELIMITER = ",";

    public int validatePurchaseAmount(String input) {
        int amount = parseNumber(input, "구입 금액");
        validatePositive(amount);
        validateDivisible(amount);
        return amount;
    }

    private int parseNumber(String input, String fieldName) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] " + fieldName + "은 숫자여야 합니다.");
        }
    }

    private void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 0보다 커야 합니다.");
        }
    }

    private void validateDivisible(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    public int[] validateWinningNumbers(String input) {
        String[] tokens = input.split(NUMBER_DELIMITER);
        validateTokenCount(tokens);
        return parseWinningNumbers(tokens);
    }

    private void validateTokenCount(String[] tokens) {
        if (tokens.length != 6) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
        }
    }

    private int[] parseWinningNumbers(String[] tokens) {
        int[] numbers = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            numbers[i] = parseNumber(tokens[i], "당첨 번호");
        }
        return numbers;
    }

    public int validateBonusNumber(String input) {
        return parseNumber(input, "보너스 번호");
    }
}