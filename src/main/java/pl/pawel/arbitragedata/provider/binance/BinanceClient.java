package pl.pawel.arbitragedata.provider.binance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import pl.pawel.common.Symbol;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class BinanceClient {

    private final RestTemplate restTemplate;
    private final String binanceUrl;

    public BigDecimal getCurrentPrice(Symbol symbol) {
        BinancePriceResponse forObject = getPriceFromBinance(symbol);
        log.info("Binance - symbol: " + symbol + ", price: " + forObject.getPrice());
        return forObject.getPrice();
    }

    private BinancePriceResponse getPriceFromBinance(Symbol symbol) {
        return restTemplate.getForObject(binanceUrl + symbol + "USDT", BinancePriceResponse.class);
    }


}

