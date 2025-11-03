// src/main/java/lotto/domain/LottoTickets.java
package lotto.domain;

import lotto.util.LottoGenerator;

import java.util.ArrayList;
import java.util.List;

public class LottoTickets {
    private static final int LOTTO_PRICE = 1000;

    private final List<Lotto> tickets;

    public LottoTickets(int purchaseAmount, LottoGenerator generator) {
        this.tickets = generateTickets(purchaseAmount, generator);
    }

    private List<Lotto> generateTickets(int purchaseAmount, LottoGenerator generator) {
        int count = purchaseAmount / LOTTO_PRICE;
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottos.add(generator.generate());
        }
        return lottos;
    }

    public List<Lotto> getTickets() {
        return tickets;
    }

    public int getCount() {
        return tickets.size();
    }

    public LottoResult match(WinningLotto winningLotto) {
        LottoResult result = new LottoResult();
        for (Lotto ticket : tickets) {
            Rank rank = winningLotto.match(ticket);
            result.addRank(rank);
        }
        return result;
    }
}