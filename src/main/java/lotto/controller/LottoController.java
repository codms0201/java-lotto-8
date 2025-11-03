package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.LottoTickets;
import lotto.domain.WinningLotto;
import lotto.util.InputValidator;
import lotto.util.LottoGenerator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.Arrays;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputValidator validator;
    private final LottoGenerator generator;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.validator = new InputValidator();
        this.generator = new LottoGenerator();
    }

    public void run() {
        int purchaseAmount = getPurchaseAmount();
        LottoTickets tickets = purchaseTickets(purchaseAmount);
        WinningLotto winningLotto = getWinningLotto();
        calculateAndPrintResult(tickets, winningLotto, purchaseAmount);
    }

    private int getPurchaseAmount() {
        return repeatUntilSuccess(() -> {
            String input = inputView.readPurchaseAmount();
            return validator.validatePurchaseAmount(input);
        });
    }

    private LottoTickets purchaseTickets(int purchaseAmount) {
        LottoTickets tickets = new LottoTickets(purchaseAmount, generator);
        outputView.printPurchaseCount(tickets.getCount());
        outputView.printLottoNumbers(tickets.getTickets());
        return tickets;
    }

    private WinningLotto getWinningLotto() {
        Lotto winningNumbers = getWinningNumbers();
        int bonusNumber = getBonusNumber();
        return repeatUntilSuccess(() -> new WinningLotto(winningNumbers, bonusNumber));
    }

    private Lotto getWinningNumbers() {
        return repeatUntilSuccess(() -> {
            String input = inputView.readWinningNumbers();
            int[] numbers = validator.validateWinningNumbers(input);
            return new Lotto(Arrays.stream(numbers).boxed().toList());
        });
    }

    private int getBonusNumber() {
        return repeatUntilSuccess(() -> {
            String input = inputView.readBonusNumber();
            return validator.validateBonusNumber(input);
        });
    }

    private void calculateAndPrintResult(LottoTickets tickets, WinningLotto winningLotto, int purchaseAmount) {
        LottoResult result = tickets.match(winningLotto);
        double profitRate = result.calculateProfitRate(purchaseAmount);
        outputView.printStatistics(result, profitRate);
    }

    private <T> T repeatUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}