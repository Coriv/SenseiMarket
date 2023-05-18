package com.sensei.history;

import com.sensei.trade.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CashHistoryDto {

    private final Long id;
    private final Long userId;
    private final OperationType type;
    private final BigDecimal quantityUSD;
    private final BigDecimal quantityPLN;
    private String toAccount;
    private final LocalDateTime time;

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
}
