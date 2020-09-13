package pl.pawel.arbitragedata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import pl.pawel.common.ResultLine;
import pl.pawel.common.ResultLine.ExchangeDetails;
import pl.pawel.arbitragedata.provider.binance.BinanceClient;
import pl.pawel.arbitragedata.provider.coinbase.CoinbaseClient;
import pl.pawel.common.Symbol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class PriceService {

    private final BinanceClient binanceClient;
    private final CoinbaseClient coinbaseClient;

    @Autowired
    private KafkaTemplate<String, ResultLine> kafkaTemplate;
    private static final String TOPIC = "quickstart-events";

    @Scheduled(fixedRate = 50000)
    public void getArbitrage() {
        List<ResultLine> resultList = new ArrayList<>();
        for (Symbol value : Symbol.values()) {
            BigDecimal binancePrice = binanceClient.getCurrentPrice(value);
            BigDecimal coinbaseProPrice = coinbaseClient.getCurrentPrice(value);
            ExchangeDetails buyer = findBuyer(binancePrice, coinbaseProPrice);
            ExchangeDetails seller = findSeller(binancePrice, coinbaseProPrice);
            Double profit = (seller.getPrice().doubleValue() - buyer.getPrice().doubleValue()) /
                    buyer.getPrice().doubleValue() * 100;
            ResultLine resultLine = new ResultLine(value, buyer, seller, profit);
            resultList.add(resultLine);
            sendMessage(resultLine);
        }
    }

    public ExchangeDetails findBuyer(BigDecimal binancePrice, BigDecimal coinbaseProPrice) {
        if (binancePrice.compareTo(coinbaseProPrice) <= 0) {
            return new ExchangeDetails("Binance", binancePrice);
        }
        return new ExchangeDetails("Coinbase Pro", coinbaseProPrice);
    }

    public ExchangeDetails findSeller(BigDecimal binancePrice, BigDecimal coinbaseProPrice) {
        if (binancePrice.compareTo(coinbaseProPrice) > 0) {
            return new ExchangeDetails("Binance", binancePrice);
        }
        return new ExchangeDetails("Coinbase Pro", coinbaseProPrice);
    }

    public void sendMessage(ResultLine resultLine) {
        kafkaTemplate.send(TOPIC, resultLine);
    }

}

