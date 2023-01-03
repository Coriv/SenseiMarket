package com.sensei.dto;

import com.sensei.config.Prototype;
import com.sensei.entity.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
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
