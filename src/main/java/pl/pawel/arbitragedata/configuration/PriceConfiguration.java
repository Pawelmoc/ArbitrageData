package pl.pawel.arbitragedata.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import pl.pawel.arbitragedata.PriceService;
import pl.pawel.arbitragedata.provider.binance.BinanceClient;
import pl.pawel.arbitragedata.provider.coinbase.CoinbaseClient;

@Configuration
@EnableScheduling
class PriceConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    BinanceClient binanceClient(@Value("${binance.fetch-prices}") String binanceUrl,
                                RestTemplate restTemplate) {
        return new BinanceClient(restTemplate, binanceUrl);
    }

    @Bean
    CoinbaseClient coinbaseProClient(@Value("${coinbasePro.fetch-prices}") String coinbaseProUrl,
                                     RestTemplate restTemplate) {
        return new CoinbaseClient(restTemplate, coinbaseProUrl);
    }

    @Bean
    PriceService priceService(BinanceClient binanceClient, CoinbaseClient coinbaseClient) {
        return new PriceService(binanceClient, coinbaseClient);
    }


}

