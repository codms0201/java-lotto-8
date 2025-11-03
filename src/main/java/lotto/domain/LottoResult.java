package lotto.domain;

import java.util.EnumMap;
import java.util.Map;

public class LottoResult {
    private static final double PERCENTAGE_MULTIPLIER = 100.0;

    private final Map<Rank, Integer> result;

    public LottoResult() {
        this.result = new EnumMap<>(Rank.class);
        initializeResult();
    }

    private void initializeResult() {
        for (Rank rank : Rank.values()) {
            if (rank.isWinning()) {
                result.put(rank, 0);
            }
        }
    }

    public void addRank(Rank rank) {
        if (rank.isWinning()) {
            result.put(rank, result.get(rank) + 1);
        }
    }

    public int getCount(Rank rank) {
        return result.getOrDefault(rank, 0);
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrizeMoney = calculateTotalPrizeMoney();
        return Math.round((double) totalPrizeMoney / purchaseAmount * PERCENTAGE_MULTIPLIER * 10) / 10.0;
    }

    private long calculateTotalPrizeMoney() {
        return result.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }
}