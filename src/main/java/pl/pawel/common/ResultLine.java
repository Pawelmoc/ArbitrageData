package pl.pawel.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultLine {

    private Symbol symbol;
    private ExchangeDetails buyer;
    private ExchangeDetails seller;
    private Double profit;

    @Data
    @AllArgsConstructor
    public static class ExchangeDetails {

        private String exchange;
        private BigDecimal price;

    }

}
