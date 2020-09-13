package pl.pawel.arbitragedata.provider.coinbase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
class CoinbasePriceResponse {

    @JsonProperty("price")
    private BigDecimal price;

}