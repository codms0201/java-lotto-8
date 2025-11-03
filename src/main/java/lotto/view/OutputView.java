package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Rank;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_FORMAT = "\n%d개를 구매했습니다.\n";
    private static final String LOTTO_NUMBER_FORMAT = "%s\n";
    private static final String STATISTICS_HEADER = "\n당첨 통계\n---";
    private static final String RANK_FORMAT = "%s - %d개\n";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.\n";

    public void printPurchaseCount(int count) {
        System.out.printf(PURCHASE_COUNT_FORMAT, count);
    }

    public void printLottoNumbers(List<Lotto> tickets) {
        for (Lotto ticket : tickets) {
            System.out.printf(LOTTO_NUMBER_FORMAT, ticket.getNumbers());
        }
    }

    public void printStatistics(LottoResult result, double profitRate) {
        System.out.println(STATISTICS_HEADER);
        printRankStatistics(result);
        System.out.printf(PROFIT_RATE_FORMAT, profitRate);
    }

    private void printRankStatistics(LottoResult result) {
        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                printRank(rank, result.getCount(rank));
            }
        }
    }

    private void printRank(Rank rank, int count) {
        System.out.printf(RANK_FORMAT, rank.getDescription(), count);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}