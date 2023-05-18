package com.sensei.trade;

import com.sensei.config.Prototype;
import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class DealDto extends Prototype<DealDto> {
    private String cryptoSymbol;
    private TransactionType transactionType;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal value;

    public DealDto clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
