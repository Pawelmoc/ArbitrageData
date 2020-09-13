package pl.pawel.arbitragedata.provider.binance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
class BinancePriceResponse {

    @JsonProperty("price")
    private BigDecimal price;

}
